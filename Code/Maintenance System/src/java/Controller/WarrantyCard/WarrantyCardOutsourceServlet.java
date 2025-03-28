package Controller.WarrantyCard;

import DAO.ContractorCardDAO;
import DAO.NotificationDAO;
import DAO.StaffDAO;
import DAO.WarrantyCardDAO;
import DAO.WarrantyCardProcessDAO;
import Model.Staff;
import Model.ContractorCard;
import Model.Notification;
import Model.WarrantyCard;
import Model.WarrantyCardProcess;
import Utils.FormatUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Set;

@WebServlet(name = "WarrantyCardOutsourceServlet", urlPatterns = {"/WarrantyCard/OutsourceRequest"})
public class WarrantyCardOutsourceServlet extends HttpServlet {

    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    private final StaffDAO staffDAO = new StaffDAO();
    private final ContractorCardDAO contractorCardDAO = new ContractorCardDAO();
    private final WarrantyCardProcessDAO wcpDao = new WarrantyCardProcessDAO();
        private final NotificationDAO notificationDAO = new NotificationDAO();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idPara = request.getParameter("ID");
        Integer id = FormatUtils.tryParseInt(idPara);
        if (id == null || warrantyCardDAO.getWarrantyCardById(id) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }
         if (!checkRightHanderlerId(request, response, id)) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?canChange=false&ID=" + id);
            return;
        }
        // Lấy danh sách Repair Contractors (RoleID = 4)
        List<Staff> contractors = staffDAO.getStaffByRoleName("Repair Contractor");
        request.setAttribute("contractors", contractors);
        request.setAttribute("warrantyCardID", id);

        // Chuyển hướng đến trang OutsourceRequest.jsp
        request.getRequestDispatcher("/views/Outsource/OutsourceRequest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String warrantyCardIdParam = request.getParameter("ID");
        Integer warrantyCardId = FormatUtils.tryParseInt(warrantyCardIdParam);
        String noteParam = request.getParameter("note");
        String processAction = request.getParameter("processAction");
        if (warrantyCardId == null || warrantyCardDAO.getWarrantyCardById(warrantyCardId) == null) {
            response.sendRedirect(request.getContextPath() + "/WarrantyCard");
            return;
        }

        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff == null) {
            request.setAttribute("updateAlert0", "You must be logged in to perform this action.");
            doGet(request, response);
            return;
        }

        WarrantyCardProcess latestProcess = wcpDao.getLatestProcessByWarrantyCardId(warrantyCardId);

        //gui sang cho contractor
        String action = request.getParameter("action");
        if ("requestOutsource".equals(action)) {
            String contractorIdParam = request.getParameter("contractorID");
            Integer contractorId = FormatUtils.tryParseInt(contractorIdParam);
            if (!checkRightHanderlerId(request, response, warrantyCardId)) {
                response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?canChange=false&ID=" + warrantyCardId);
                return;
            }
            // Kiểm tra contractorID hợp lệ và có RoleID = 4
            Staff contractor = staffDAO.getStaffById(contractorId);
            if (contractorId != null && contractor != null && contractor.getRole() == 4) {
                // Tạo bản ghi WarrantyCardProcess với Action = request_outsource
                WarrantyCardProcess newProcess = new WarrantyCardProcess();
                newProcess.setWarrantyCardID(warrantyCardId);
                newProcess.setHandlerID(staff.getStaffID());
                newProcess.setAction("request_outsource");
                newProcess.setNote("Outsourced to Repair Contractor ID: " + contractorId + " (" + contractor.getName() + ")");
                boolean success = wcpDao.addWarrantyCardProcess(newProcess);
                System.out.println(noteParam);
                if (success) {
                    //them vao contractor card
                    ContractorCard contractorCard = new ContractorCard();
                    contractorCard.setWarrantyCardID(warrantyCardId);
                    contractorCard.setContractorID(contractorId);
                    contractorCard.setNote(noteParam);
                    contractorCard.setStaffID(staff.getStaffID());
                    contractorCard.setStatus("waiting");
                    contractorCardDAO.addContractorCard(contractorCard);
                    String message = "A warranty request send to you " + (noteParam.isBlank()?"":(": "+noteParam));
                            Notification notification = new Notification();
                            notification.setRecipientType("Staff");
                            notification.setRecipientID(contractorId);
                            notification.setMessage(message);
                            notification.setCreatedDate(new Date());
                            notification.setIsRead(false);
                            notification.setTarget(request.getContextPath() + "/warrantyCardDetailContractor?cardId=" + warrantyCardId); // URL chi tiết
                            notificationDAO.addNotification(notification);
                    request.setAttribute("updateAlert1", "Outsource request sent successfully!");
                    response.sendRedirect(request.getContextPath() + "/WarrantyCard/Detail?ID=" + warrantyCardId);
                    return;
                } else {
                    request.setAttribute("updateAlert0", "Failed to send outsource request.");
                }
            } else {
                request.setAttribute("updateAlert0", "Invalid Repair Contractor selected.");
            }
        } 
        //process outsource cua technician
        else if ("processOutsource".equals(action)) {

            if (processAction != null && isValidProcessAction(processAction)) {
                boolean canProcess = false;
                switch (processAction) {
                    case "cancel_outsource" -> {
                        canProcess = latestProcess != null && ("request_outsource".equals(latestProcess.getAction()) || "accept_outsource".equals(latestProcess.getAction()));
                    }
                    case "send_outsource" -> {
                        canProcess = latestProcess != null && "accept_outsource".equals(latestProcess.getAction());
                    }
                    case "receive_from_outsource" -> {
                        canProcess = latestProcess != null && "back_outsource".equals(latestProcess.getAction());
                    }
                }
                if (canProcess) {
                    System.out.println("can " + processAction);
                    WarrantyCardProcess newProcess = new WarrantyCardProcess();
                    newProcess.setWarrantyCardID(warrantyCardId);
                    newProcess.setHandlerID(staff.getStaffID());
                    newProcess.setAction(processAction);
                    boolean success = wcpDao.addWarrantyCardProcess(newProcess);
                    if (success) {
                        if("cancel_outsource".equals(processAction)) {
                            ContractorCard cc = contractorCardDAO.getLastContractorCardOfWarrantyCard(warrantyCardId);
                            contractorCardDAO.updateContractorStatus(cc.getContractorCardID(), "cancel");
                        }
                        request.setAttribute("updateAlert1", processAction.substring(0, 1).toUpperCase() + processAction.substring(1) + " action successful!");
                    } else {
                        request.setAttribute("updateAlert0", "Failed to process " + processAction + ".");
                    }
                } else {
                    request.setAttribute("updateAlert0", "Cannot perform " + processAction + " at this stage.\n");
                }

            }
            //tra ve trang detailCard
            WarrantyCardDetailServlet servlet = new WarrantyCardDetailServlet();
            servlet.processRequest(request, response);
            return;
        }

        // Nếu có lỗi, quay lại trang OutsourceRequest
        doGet(request, response);
    }

    private boolean isValidProcessAction(String action) {
        return action != null && Set.of("outsource",
                "create", "receive", "refuse", "fixing", "refix", "wait_components", "received_components",
                "request_outsource", "unfixable_outsource", "accept_outsource", "refuse_outsource", "send_outsource", "lost",
                "receive_outsource", "fixed_outsource", "cancel_outsource", "back_outsource",
                "receive_from_outsource", "fixed", "completed", "cancel"
        ).contains(action);
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

    @Override
    public String getServletInfo() {
        return "Servlet for handling Warranty Card outsourcing requests";
    }
}
