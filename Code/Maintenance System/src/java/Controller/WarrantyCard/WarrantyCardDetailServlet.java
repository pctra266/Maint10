package Controller.WarrantyCard;

import DAO.ComponentDAO;
import DAO.ComponentRequestDAO;
import DAO.CustomerDAO;
import DAO.NotificationDAO;
import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardDetailDAO;
import DAO.WarrantyCardProcessDAO; // New DAO for WarrantyCardProcess
import Model.Component;
import Model.ComponentRequest;
import Model.ComponentRequestDetail;
import Model.Notification;
import Model.Staff;
import Model.WarrantyCard;
import Model.WarrantyCardDetail;
import Model.WarrantyCardProcess;
import Utils.FormatUtils;
import Utils.OtherUtils;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "WarrantyCardDetail", urlPatterns = {"/WarrantyCard/Detail"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 50, // 50MB
        maxRequestSize = 1024 * 1024 * 100 // 100MB
)
public class WarrantyCardDetailServlet extends HttpServlet {

    private final WarrantyCardDetailDAO wcdDao = new WarrantyCardDetailDAO();
    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final ComponentDAO componentDAO = new ComponentDAO();
    private final ComponentRequestDAO componentRequestDAO = new ComponentRequestDAO();
    private final WarrantyCardProcessDAO wcpDao = new WarrantyCardProcessDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final NotificationDAO notificationDAO = new NotificationDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPara = request.getParameter("ID");
        Integer id = FormatUtils.tryParseInt(idPara);
        if (id == null || warrantyCardDAO.getWarrantyCardById(id) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        //back
        session.setAttribute("createComponentRequestFrom", request.getContextPath() + request.getServletPath() + "?ID=" + id);

        Integer handlerID = null;
        if (staff != null) {
            handlerID = staff.getStaffID();
        }

        List<WarrantyCardDetail> cardDetails = wcdDao.getWarrantyCardDetailOfCard(id);
        WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(id);
        List<Component> availableComponents = componentDAO.getAllComponents();
        WarrantyCardProcess latestProcess = wcpDao.getLatestProcessByWarrantyCardId(id); // Fetch latest process
        Map<ComponentRequest, List<ComponentRequestDetail>> componentRequests = new HashMap<>();
        List<ComponentRequest> listComponentRequest = componentRequestDAO.getAllComponentRequestsOfCard(id);
        for (ComponentRequest cr : listComponentRequest) {
            componentRequests.put(cr, componentRequestDAO.getListComponentRequestDetailById(cr.getComponentRequestID() + ""));
        }
        if ("1".equals(request.getParameter("addSuccess"))) {
            request.setAttribute("addAlert1", "Component added successfully!");
        }
        if ("true".equals(request.getParameter("payment"))) {
            request.setAttribute("addAlert1", "Payment successfully!");
        }
        if ("false".equals(request.getParameter("canChange"))) {
            request.setAttribute("addAlert0", "You don't have permission to impact this card!");
        }
        if ("false".equals(request.getParameter("payment"))) {
            request.setAttribute("addAlert0", "Payment fail!");
        }
        if ("false".equals(request.getParameter("invoice"))) {
            request.setAttribute("addAlert0", "Error in payment!");
        }
        if ("false".equals(request.getParameter("invoiceCreate"))) {
            request.setAttribute("addAlert0", "Product isn't fixed!");
        }
//        if ("false".equals(request.getParameter("canChange"))) {
//            request.setAttribute("addAlert0", "You don't have permission to impact this card!");
//        }
        request.setAttribute("price", warrantyCardDAO.getPriceOfWarrantyCard(id));
        request.setAttribute("componentRequests", componentRequests);
        request.setAttribute("pd", warrantyCardDAO.getProductDetailByCode(wc.getProductDetailCode()));
        request.setAttribute("customer", customerDAO.getCustomerByID(wc.getCustomerID()));
        request.setAttribute("handler", staffDAO.getStaffById(wc.getHandlerID()));
        request.setAttribute("cardDetails", cardDetails);
        request.setAttribute("card", wc);
        request.setAttribute("images", wc.getImages());
        request.setAttribute("videos", wc.getVideos());
        request.setAttribute("availableComponents", availableComponents);
        request.setAttribute("latestProcess", latestProcess); // Pass to JSP
        request.getRequestDispatcher("/views/WarrantyCard/WarrantyCardDetail.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String warrantyCardIdParam = request.getParameter("ID");
        Integer warrantyCardId = FormatUtils.tryParseInt(warrantyCardIdParam);

        if (warrantyCardId == null || warrantyCardDAO.getWarrantyCardById(warrantyCardId) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            request.setAttribute("updateAlert0", "You must be logged in to perform this action.");
            processRequest(request, response);
            return;
        }

        if (!checkRightHanderlerId(request, response, warrantyCardId)) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?canChange=false&ID=" + warrantyCardId);
            return;
        }

        WarrantyCardProcess latestProcess = wcpDao.getLatestProcessByWarrantyCardId(warrantyCardId);
        if (null != action) {
            switch (action) {
                case "deleteMedia" -> {
                    WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
                    List<String> videoPaths = new ArrayList<>(wc.getVideos()); // Sao chép danh sách để chỉnh sửa
                    List<String> imagePaths = new ArrayList<>(wc.getImages());
                    String deleteMedia = request.getParameter("deleteMedia"); // Thêm tham số để xóa media
                    if (deleteMedia != null && !deleteMedia.isEmpty()) {
                        String filePath = request.getServletContext().getRealPath("") + deleteMedia; // Đường dẫn file trên server
                        if (imagePaths.remove(deleteMedia)) {
                            wc.setImages(imagePaths);
                            if (warrantyCardDAO.updateWarrantyCard(wc)) {
                                try {
                                    Files.deleteIfExists(Paths.get(filePath));
                                } catch (IOException e) {
                                    e.printStackTrace(); // Log lỗi nếu không xóa được file
                                }
                                response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?ID=" + wc.getWarrantyCardID() + "&deleteMedia=true");
                                return;
                            }
                        } else if (videoPaths.remove(deleteMedia)) {
                            wc.setVideos(videoPaths);
                            if (warrantyCardDAO.updateWarrantyCard(wc)) {
                                try {
                                    Files.deleteIfExists(Paths.get(filePath));
                                } catch (IOException e) {
                                    e.printStackTrace(); // Log lỗi nếu không xóa được file
                                }
                            }
                            response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?ID=" + wc.getWarrantyCardID() + "&deleteMedia=true");
                            return;
                        }
                    }

                }
                case "uploadImages" -> {
                    WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
                    List<String> videoPaths = new ArrayList<>(wc.getVideos()); // Sao chép danh sách để chỉnh sửa
                    List<String> imagePaths = new ArrayList<>(wc.getImages());
                    boolean canUpdate = true;
                    for (Part part : request.getParts()) {
                        if ("mediaFiles".equals(part.getName()) && part.getSize() > 0) {
                            String mimeType = part.getContentType();
                            String mediaPath;
                            if (mimeType != null && mimeType.startsWith("video/")) {
                                mediaPath = OtherUtils.saveVideo(part, request, "media/component");
                                if (mediaPath != null && !mediaPath.startsWith("Invalid") && !mediaPath.startsWith("File is too large")) {
                                    videoPaths.add(mediaPath);
                                } else {
                                    canUpdate = false;
                                    request.setAttribute("pictureAlert", mediaPath != null ? mediaPath : "Error uploading media");
                                }
                            } else {
                                mediaPath = OtherUtils.saveImage(part, request, "media/component");
                                if (mediaPath != null && !mediaPath.startsWith("Invalid") && !mediaPath.startsWith("File is too large")) {
                                    imagePaths.add(mediaPath);
                                } else {
                                    canUpdate = false;
                                    request.setAttribute("pictureAlert", mediaPath != null ? mediaPath : "Error uploading media");
                                }
                            }
                        }
                    }
                    wc.setImages(imagePaths);
                    wc.setVideos(videoPaths);
                    warrantyCardDAO.updateWarrantyCard(wc);
                    response.sendRedirect(request.getContextPath() + request.getServletPath() + "?ID=" + wc.getWarrantyCardID());
                    return;
                }
                case "update" -> {
                    String noteParam = request.getParameter("note");
                    String warrantyCardDetailIdParam = request.getParameter("warrantyCardDetailID");
                    String status = request.getParameter("status");
                    String quantityParam = request.getParameter("quantity");
                    String priceParam = request.getParameter("price");
                    Integer warrantyCardDetailId = FormatUtils.tryParseInt(warrantyCardDetailIdParam);
                    Integer quantity = FormatUtils.tryParseInt(quantityParam);
                    Double price = FormatUtils.tryParseDouble(priceParam);
                    if (warrantyCardDetailId != null) {
                        WarrantyCardDetail detail = wcdDao.getWarrantyCardDetailById(warrantyCardDetailId);
                        if (detail != null) {
                            boolean updated = false;
                            if (status != null && isValidStatus(status)) {
                                detail.setStatus(status);
                                if ("warranty_repaired".equals(status) || "warranty_replaced".equals(status)) {
                                    detail.setPrice(0.0);
                                }
                                updated = true;
                            }
                            if (quantity != null && quantity >= 0) {
                                detail.setQuantity(quantity);
                                updated = true;
                            }
                            if (price != null && price >= 0 && !("warranty_repaired".equals(detail.getStatus()) || "warranty_replaced".equals(detail.getStatus()))) {
                                detail.setPrice(price);
                                updated = true;
                            }
                            if (updated) {
                                detail.setNote(noteParam);
                                boolean success;
                                if (quantity > 0) {
                                     success = wcdDao.updateWarrantyCardDetail(detail);
                                }
                                else {
                                    success = wcdDao.deleteWarrantyCardDetail(warrantyCardDetailId);
                                }

                                if (success) {
                                    request.setAttribute("updateAlert1", "Update successful!");
                                } else {
                                    request.setAttribute("updateAlert0", "Failed to update.");
                                }
                            }
                        }
                    }
                }
                case "delete" -> {
                    String warrantyCardDetailIdParam = request.getParameter("warrantyCardDetailID");
                    Integer warrantyCardDetailId = FormatUtils.tryParseInt(warrantyCardDetailIdParam);
                    if (warrantyCardDetailId != null) {
                        boolean deleted = wcdDao.deleteWarrantyCardDetail(warrantyCardDetailId);
                        if (deleted) {
                            request.setAttribute("updateAlert1", "Component deleted successfully!");
                        } else {
                            request.setAttribute("updateAlert0", "Failed to delete component.");
                        }
                    }
                }
                case "process" -> {
                    String errorProcess = "";
                    String processAction = request.getParameter("processAction");
                    if (processAction != null && isValidProcessAction(processAction)) {
                        boolean canProcess = false;
                        switch (processAction) {
                            case "receive" ->
                                canProcess = latestProcess != null && ("create".equals(latestProcess.getAction()) || "cancel".equals(latestProcess.getAction()) || "refuse".equals(latestProcess.getAction()));
                            case "fixing" ->
                                canProcess = latestProcess != null && ("receive".equals(latestProcess.getAction()) || "receive_from_outsource".equals(latestProcess.getAction()) || "refuse_outsource".equals(latestProcess.getAction()) || "cancel_outsource".equals(latestProcess.getAction()));
                            case "refix" ->
                                canProcess = latestProcess != null && ("fixed".equals(latestProcess.getAction()) || "completed".equals(latestProcess.getAction()) || "cancel".equals(latestProcess.getAction()));
                            case "outsource" -> {
                                // Kiểm tra điều kiện để cho phép outsourcing
                                canProcess = latestProcess != null && ("fixing".equals(latestProcess.getAction()) || "refix".equals(latestProcess.getAction()));
                                if (canProcess) {
                                    // Chuyển hướng đến trang chọn Repair Contractor
                                    response.sendRedirect(request.getContextPath() + "/WarrantyCard/OutsourceRequest?ID=" + warrantyCardId);
                                    return; // Thoát khỏi phương thức để không tiếp tục xử lý
                                } else {
                                    request.setAttribute("updateAlert0", "Cannot perform outsource at this stage.");
                                }
                            }
                            case "fixed" -> {
                                canProcess = latestProcess != null && !"completed".equals(latestProcess.getAction()) && ("fixing".equals(latestProcess.getAction()) || "refix".equals(latestProcess.getAction()) || "outsource".equals(latestProcess.getAction()));
                                if (!canChangeToFixed(warrantyCardId)) {
                                    canProcess = false;
                                    errorProcess += "One or more component is being fixed, check again!";
                                }
                            }
                            case "cancel" -> {
                                canProcess = latestProcess != null && !"cancel".equals(latestProcess.getAction()) && !"completed".equals(latestProcess.getAction()) && !"fixed".equals(latestProcess.getAction());
                            }
                            case "refuse" ->
                                canProcess = latestProcess != null && !"refuse".equals(latestProcess.getAction()) && !"completed".equals(latestProcess.getAction()) && !"fixed".equals(latestProcess.getAction());
                        }
                        //
                        if (latestProcess != null && latestProcess.getAction().endsWith("outsource") && !latestProcess.getAction().equals("refuse_outsource") && !"receive_from_outsource".equals(latestProcess.getAction()) && !"refuse_outsource".equals(latestProcess.getAction()) && !"cancel_outsource".equals(latestProcess.getAction())) {
                            canProcess = false;
                        }

                        if (canProcess && !"outsource".equals(processAction)) {
                            WarrantyCardProcess newProcess = new WarrantyCardProcess();
                            newProcess.setWarrantyCardID(warrantyCardId);
                            newProcess.setHandlerID(staff.getStaffID());
                            newProcess.setAction(processAction);
                            boolean success = wcpDao.addWarrantyCardProcess(newProcess);
                            if (success) {
                                WarrantyCard wc = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
                                if ("cancel".equals(processAction) || "refix".equals(processAction)) {
                                    wc.setWarrantyStatus(processAction);

                                    if ("cancel".equals(processAction)) {
                                        wc.setCanceldDate(Date.from(Instant.now()));
                                    }
                                }
                                if ("fixed".equals(processAction)) {
                                    //Thong bao
                                    String message = "Your product " + wc.getProductName() + " has been fixed!";
                                    Notification notification = new Notification();
                                    notification.setRecipientType("Customer");
                                    notification.setRecipientID(wc.getCustomerID());
                                    notification.setMessage(message);
                                    notification.setCreatedDate(new Date());
                                    notification.setIsRead(false);
                                    notification.setTarget(request.getContextPath() + "/WarrantyCard/Detail?noti=true&ID=" + wc.getWarrantyCardID()); // URL chi tiết
                                    notificationDAO.addNotification(notification);

                                    wc.setWarrantyStatus("done");
                                    wc.setDonedDate(Date.from(Instant.now()));
                                }
                                if ("fixing".equals(processAction)) {
                                    wc.setWarrantyStatus("fixing");
                                }
                                if ("refuse".equals(processAction)) {
                                    wc.setWarrantyStatus("waiting");
                                    wc.setHandlerID(null);
                                }
                                if ("receive".equals(processAction)) {
                                    wc.setHandlerID(staff.getStaffID());
                                }
                                warrantyCardDAO.updateWarrantyCard(wc); // Update WarrantyCard status

                                request.setAttribute("updateAlert1", processAction.substring(0, 1).toUpperCase() + processAction.substring(1) + " action successful!");
                            } else {
                                request.setAttribute("updateAlert0", "Failed to process " + processAction + ".");
                            }
                        } else {
                            request.setAttribute("updateAlert0", "Cannot perform " + processAction + " at this stage.\n" + errorProcess);
                        }
                    }
                }
                default -> {
                }
            }
        }

        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private boolean isValidStatus(String status) {
        return "warranty_repaired".equals(status)
                || "repaired".equals(status)
                || "replace".equals(status)
                || "warranty_replaced".equals(status)
                || "fixing".equals(status);
    }

    private boolean isValidProcessAction(String action) {
        return action != null && Set.of("outsource",
                "create", "receive", "refuse", "fixing", "refix", "wait_components", "received_components",
                "request_outsource", "cancel_outsource", "accept_outsource", "refuse_outsource", "send_outsource", "lost",
                "receive_outsource", "fixed_outsource", "unfixable_outsource", "back_outsource",
                "receive_from_outsource", "fixed", "completed", "cancel"
        ).contains(action);
    }

    private boolean canChangeToFixed(Integer warrantyCardId) {
        List<WarrantyCardDetail> cardDetails = wcdDao.getWarrantyCardDetailOfCard(warrantyCardId);
        for (WarrantyCardDetail cardDetail : cardDetails) {
            if (cardDetail.getStatus().equals("fixing")) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRightHanderlerId(HttpServletRequest request, HttpServletResponse response, int warrantyCardId) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("componentWarehouseFrom", request.getContextPath() + request.getServletPath() + "?ID=" + warrantyCardId);
        Staff staff = (Staff) session.getAttribute("staff");
        WarrantyCard card = warrantyCardDAO.getWarrantyCardById(warrantyCardId);
        if (card.getHandlerID() == null || card.getHandlerID() == 0) {
            return true;
        }
        return !(staff == null || card.getHandlerID() != staff.getStaffID());
    }
}
