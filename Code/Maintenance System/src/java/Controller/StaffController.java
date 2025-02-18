/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.StaffDAO;
import DAO.StaffLogDAO;
import Model.Staff;
import Model.StaffLog;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;
/**
 *
 * @author ADMIN
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class StaffController extends HttpServlet {
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        StaffDAO dao = new StaffDAO();
        List<Staff> list ;
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAll";
        }
        
        switch(action){
            case "viewAll":
                String search = request.getParameter("search");
                String searchname = request.getParameter("searchname");
                String column = request.getParameter("column");
                String sortOrder = request.getParameter("sortOrder");
                String page_size = request.getParameter("page_size");                               
                String name = "";
                if(searchname != null && !searchname.isEmpty()){
                    name = searchname.trim().replaceAll("\\s+", " ");
                }
                
                int pagesize = 5; 

                if (page_size != null && !page_size.isEmpty()) {
                    try {
                        pagesize = Integer.parseInt(page_size);
                    } catch (NumberFormatException e) {
                        pagesize = 5; 
                    }
                }
                    
                int pageIndex = 1;
                String pageIndexStr = request.getParameter("index");

                if (pageIndexStr != null) {
                    try {
                        pageIndex = Integer.parseInt(pageIndexStr);
                    } catch (NumberFormatException e) {
                        pageIndex = 1;
                    }
                }
                int totalStaff;
                list = dao.getAllStaff(name, search, pageIndex, pagesize, column, sortOrder);
                totalStaff = dao.getAllStaff(name, search, column, sortOrder).size();
                int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                request.setAttribute("totalPageCount", totalPageCount);
                request.setAttribute("search", search);
                request.setAttribute("searchname", searchname);
                request.setAttribute("column", column);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("pageIndex", pageIndex);
                request.setAttribute("page_size", page_size);
                request.setAttribute("list", list);
                request.getRequestDispatcher("Staff.jsp").forward(request, response);
                break;
            case "Delete":
                String staffID = request.getParameter("staffID");
                boolean delete = dao.deleteStaff(staffID);
                response.sendRedirect("StaffController");
                break;
            case "Update":
                String UpdateStaffID = request.getParameter("staffID");
                Staff staff = dao.getInformationByID(UpdateStaffID);
                request.setAttribute("staff", staff);
//                PrintWriter out = response.getWriter();
//                out.print(staff.getImgage());
                request.getRequestDispatcher("staff-information.jsp").forward(request, response);
                break;
            default:
                break;
        } 
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        StaffDAO dao = new StaffDAO();
        List<Staff> list ;
        String action = request.getParameter("action");
        if (action == null) {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                if ("action".equals(part.getName())) {
                    action = request.getParameter(part.getName());
                    break;
                }               
            }
        }
               
        PrintWriter out = response.getWriter();
        
        String search = request.getParameter("search");
                String searchname = request.getParameter("searchname");
                String column = request.getParameter("column");
                String sortOrder = request.getParameter("sortOrder");
                String page_size = request.getParameter("page_size");               
                int pagesize = 5; 

                if (page_size != null && !page_size.isEmpty()) {
                    try {
                        pagesize = Integer.parseInt(page_size);
                    } catch (NumberFormatException e) {
                        pagesize = 5; 
                    }
                }
                int pageIndex = 1;
                String pageIndexStr = request.getParameter("index");

                if (pageIndexStr != null) {
                    try {
                        pageIndex = Integer.parseInt(pageIndexStr);
                    } catch (NumberFormatException e) {
                        pageIndex = 1;
                    }
                }
        
        int totalStaff;
        totalStaff = dao.getAllStaff(searchname, search, column, sortOrder).size();
        int totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);  
        
        switch(action){
            
            case "AddStaff":
                String usename = request.getParameter("usename").trim();
                String password = request.getParameter("password").trim();
                String role = request.getParameter("role");
                String name = request.getParameter("name").trim();
                String email = request.getParameter("email").trim();
                String phone = request.getParameter("phone").trim();
                String address = request.getParameter("address").trim();
                Part imagePart = request.getPart("newImage");
                String imagePath = saveImage(imagePart, request);
                

                list = dao.getAllStaff(searchname, search, pageIndex, pagesize, column, sortOrder);
                
                
                if (usename.isEmpty() || password.isEmpty() || role == null ||
                    name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                    request.setAttribute("errorMessage", "Không được bỏ trống bất kỳ trường nào.");
                    request.getRequestDispatcher("add-staff.jsp").forward(request, response);
                    return;
                }
                if (!phone.matches("\\d+")) {
                    request.setAttribute("errorMessage", "Số điện thoại chỉ được chứa số.");
                    request.getRequestDispatcher("add-staff.jsp").forward(request, response);
                    return;
                }

                if (!phone.matches("0\\d{9}")) {
                    request.setAttribute("errorMessage", "Số điện thoại phải nhập đủ.");
                    request.getRequestDispatcher("add-staff.jsp").forward(request, response);
                    return;
                }
                if (dao.isPhoneExists(phone) ) {
                    request.setAttribute("errorMessage", "Số điện thoại đã được đăng ký.");
                    request.getRequestDispatcher("add-staff.jsp").forward(request, response);
                    return;
                }
                
                if(imagePath.equals("Chỉ lấy file png và jpg")){
                    request.setAttribute("ErrorImage", "File upload khong hop ");
                    request.getRequestDispatcher("add-staff.jsp").forward(request, response);
                    return;
                }
                if(imagePart.getSize()> 1024*1024){
                   request.setAttribute("ErrorImage", "Anh phai nho hon 1MB ");
                   request.getRequestDispatcher("add-staff.jsp").forward(request, response);
                   return;
                }
                
                boolean add = dao.addStaff(usename, password, role, name, email, phone, address, imagePath);
                if (add) {
                    request.setAttribute("message", "Thêm nhân viên thành công!");
                } else {
                    request.setAttribute("message", "Thêm nhân viên thất bại!");
                }
                
                request.setAttribute("totalPageCount", totalPageCount);
                request.setAttribute("search", search);
                request.setAttribute("searchname", searchname);
                request.setAttribute("column", column);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("pageIndex", pageIndex);
                request.setAttribute("page_size", page_size);
                request.setAttribute("list", list);               

                request.getRequestDispatcher("Staff.jsp").forward(request, response);
                break;
            case "Update":
                String UpdatestaffID = request.getParameter("staffID").trim();
                String Updateusename = request.getParameter("usename").trim();
                String Updatepassword = request.getParameter("password").trim();
                String Updaterole = request.getParameter("role").trim();
                String Updatename = request.getParameter("name").trim();
                String Updateemail = request.getParameter("email").trim();
                String Updatephone = request.getParameter("phone").trim();
                String Updateaddress = request.getParameter("address").trim();
                Part UpdateimagePart = request.getPart("newImage");
                String UpdateimagePath = saveImage(UpdateimagePart, request);
                
                if (Updateusename.isEmpty() || Updatepassword.isEmpty() || Updaterole == null ||
                    Updatename.isEmpty() || Updateemail.isEmpty() || Updatephone.isEmpty() || Updateaddress.isEmpty() || UpdatestaffID.isEmpty()) {
                    request.setAttribute("errorMessage", "Không được bỏ trống bất kỳ trường nào.");
                    Staff staff = dao.getInformationByID(UpdatestaffID);
                    request.setAttribute("staff", staff);
                    request.getRequestDispatcher("staff-information.jsp").forward(request, response);
                    return;
                }
                
//                if (!Updatepassword.matches("^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{8,}$")) {
//                    request.setAttribute("errorMessage", "Password phai có ít nhất 1 chữ cái, 1 số và 1 kí tự đặc biệt.");
//                    Staff staff = dao.getInformationByID(UpdatestaffID);
//                    request.setAttribute("staff", staff);
//                    request.getRequestDispatcher("staff-information.jsp").forward(request, response);
//                    return;
//                }
                
                if (!Updatephone.matches("\\d+")) {
                    request.setAttribute("errorMessage", "Số điện thoại chỉ được chứa số.");
                    Staff staff = dao.getInformationByID(UpdatestaffID);
                    request.setAttribute("staff", staff);
                    request.getRequestDispatcher("staff-information.jsp").forward(request, response);
                    return;
                }
                
                

                if (!Updatephone.matches("0\\d{9}")) {
                    request.setAttribute("errorMessage", "Số điện thoại phải nhập đủ 10 số.");
                    Staff staff = dao.getInformationByID(UpdatestaffID);
                    request.setAttribute("staff", staff);
                    request.getRequestDispatcher("staff-information.jsp").forward(request, response);
                    return;
                }
                if (dao.isUpdatePhoneExists(Updatephone, UpdatestaffID)) {
                    request.setAttribute("errorMessage", "Số điện thoại đã được đăng ký.");
                    Staff staff = dao.getInformationByID(UpdatestaffID);
                    request.setAttribute("staff", staff);
                    request.getRequestDispatcher("staff-information.jsp").forward(request, response);
                    return;
                }
                if(UpdateimagePath.equals("Chỉ lấy file png và jpg")){
                    request.setAttribute("ErrorImage", "File upload khong hop ");
                    Staff staff = dao.getInformationByID(UpdatestaffID);
                    request.setAttribute("staff", staff);
                    request.getRequestDispatcher("staff-information.jsp").forward(request, response);
                    return;
                }
                if(UpdateimagePart.getSize()> 1024*1024){
                   request.setAttribute("ErrorImage", "Anh phai nho hon 1MB ");
                   Staff staff = dao.getInformationByID(UpdatestaffID);
                   request.setAttribute("staff", staff);
                   request.getRequestDispatcher("staff-information.jsp").forward(request, response);
                   return;
                }
                request.setAttribute("change", "confirm('Bạn có chắc chắn muốn thay đổi không!')");
                
                
                StaffDAO staff = new StaffDAO();
                StaffLogDAO  stafflog = new StaffLogDAO();
                boolean update = stafflog.addStaff(UpdatestaffID, Updateusename, Updatepassword, Updaterole, Updatename, Updateemail, Updatephone, Updateaddress, UpdateimagePath);
                boolean uppdate = staff.updateStaff(UpdatestaffID, Updateusename, Updatepassword, Updaterole, Updatename, Updateemail, Updatephone, Updateaddress, UpdateimagePath);
                
                if (update) {
                    request.setAttribute("message", "Thay đổi thành công!");
                } else {
                    request.setAttribute("message", "Thay đổi thất bại!");
                }
                list = dao.getAllStaff(searchname, search, pageIndex, pagesize, column, sortOrder);
                request.setAttribute("list", list); 
                request.setAttribute("totalPageCount", totalPageCount);
                request.getRequestDispatcher("Staff.jsp").forward(request, response);
                break;
            default:
                break;
        } 

    }

    private String saveImage(Part imagePart, HttpServletRequest request) throws IOException {
    if (imagePart == null || imagePart.getSize() == 0 || imagePart.getSubmittedFileName() == null|| imagePart.getSubmittedFileName().isEmpty()) return "img/Staff/default-image.png";

    // Đường dẫn đến thư mục Web Pages/img/Staff
    String devUploadPath = request.getServletContext().getRealPath("/").replace("build\\web\\", "web/") + "img/Staff";

    // Đường dẫn đến thư mục build/web/img/Staff
    String buildUploadPath = request.getServletContext().getRealPath("/") + "img/Staff";

    System.out.println("Dev Upload Path: " + devUploadPath);
    System.out.println("Build Upload Path: " + buildUploadPath);

    File devUploadDir = new File(devUploadPath);
    File buildUploadDir = new File(buildUploadPath);

    if (!devUploadDir.exists()) devUploadDir.mkdirs(); // Tạo thư mục nếu chưa có
    if (!buildUploadDir.exists()) buildUploadDir.mkdirs();

    String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
    
    if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
        String error = "Chỉ lấy file png và jpg";
        return error;
    }
    
    File devFile = new File(devUploadPath, fileName);
    File buildFile = new File(buildUploadPath, fileName);

    // Ghi file vào cả hai thư mục
    imagePart.write(devFile.getAbsolutePath());
    Files.copy(devFile.toPath(), buildFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    return "img/Staff/"+ fileName; // Trả về đường dẫn lưu trong DB
}
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
