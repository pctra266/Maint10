/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller;

import DAO.BlogDAO;
import DAO.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.ArrayList;
import Model.Blog;
import Utils.Pagination;
import Model.Staff;
import jakarta.servlet.http.HttpSession;


/**
 *
 * @author ADMIN
 */
@WebServlet(name="BlogController", urlPatterns={"/BlogController/*"})
public class BlogController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Blog</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Blog at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

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
        BlogDAO dao = new BlogDAO();
        StaffDAO staffDAO = new StaffDAO();
        List<Blog> list ;
        ArrayList<Blog> listrole;
        String action = request.getParameter("action");
        if (action == null) {
            action = "viewAll";
        }
        HttpSession session = request.getSession();
        Staff staffOnSession = (Staff) session.getAttribute("staff");
        if (staffOnSession == null) {
            staffOnSession = new Staff();
            staffOnSession.setStaffID(0); 
        }else{
            Staff staffProfile = staffDAO.getStaffById(staffOnSession.getStaffID());
            listrole = dao.getAllRole();
            for (Blog role : listrole) {
                if (role.getStaff().equals(String.valueOf(staffProfile.getRole()))) {
                    request.setAttribute("Create", "Duoc create");
                }
            }
        }
        String action1 = request.getPathInfo();

    if (action1 != null && !action1.equals("/")) {
        // Nếu có sự thay đổi (action1 khác "/" hoặc null)
        request.setAttribute("errorMessage", "Đường dẫn không hợp lệ!");
        request.getRequestDispatcher("BlogController").forward(request, response);
        return;
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
                String pageIndexStr = request.getParameter("page");

                if (pageIndexStr != null) {
                    try {
                        pageIndex = Integer.parseInt(pageIndexStr);
                    } catch (NumberFormatException e) {
                        pageIndex = 1;
                    }
                }
                int totalStaff;
                int totalPageCount;
                list = dao.getAllBlogs(name, search, pageIndex, pagesize, column, sortOrder);
                if(list.size()==0){
                    request.setAttribute("nolist", "Khong co danh sach ma ban can tim!");
                    name = "";
                    list = dao.getAllBlogs(name, search, pageIndex, pagesize, column, sortOrder);
                    totalStaff = dao.getAllBlogs(name, search, column, sortOrder).size();
                    totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                }else{
                
                    totalStaff = dao.getAllBlogs(name, search, column, sortOrder).size();
                    totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                }
                Pagination pagination = new Pagination();
                
                pagination.setUrlPattern( "/BlogController");
                pagination.setCurrentPage(pageIndex);
                pagination.setPageSize(pagesize);
                pagination.setTotalPages(totalPageCount);
                pagination.setTotalPagesToShow(5); // Hiển thị tối đa 5 trang liền kề
                pagination.setSort(column == null ? "" : column);
                pagination.setOrder(sortOrder == null ? "" : sortOrder);
                
                List<String> searchFields = new ArrayList();
                List<String> searchValues = new ArrayList<>();
                if(searchname != null && !searchname.isEmpty()){
                    searchFields.add("searchname");
                    searchValues.add(searchname);
                }
                if(search != null && !search.isEmpty()){
                    searchFields.add("search");
                    searchValues.add(search);
                }
                if(page_size != null && !page_size.isEmpty()){
                    searchFields.add("page_size");
                    searchValues.add(page_size);
                }
                pagination.setSearchFields(searchFields.toArray(new String[searchFields.size()]));
                pagination.setSearchValues(searchValues.toArray(new String[searchValues.size()]));

                request.setAttribute("pagination", pagination);
                
                request.setAttribute("totalPageCount", totalPageCount);
                request.setAttribute("search", search);
                request.setAttribute("searchname", searchname);
                request.setAttribute("column", column);
                request.setAttribute("sortOrder", sortOrder);
                request.setAttribute("pageIndex", pageIndex);
                request.setAttribute("page_size", page_size);
                request.setAttribute("list", list);
                request.getRequestDispatcher("Blog.jsp").forward(request, response);
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
        BlogDAO dao = new BlogDAO();
        StaffDAO staffDAO = new StaffDAO();
        List<Blog> list ;
        List<Blog> info ;
        List<Blog> changeBlog ;
        int id = 0;
        HttpSession session = request.getSession();
        Staff staffOnSession = (Staff) session.getAttribute("staff");
        String action = request.getParameter("action");
        
        if (staffOnSession == null) {
            
            staffOnSession = new Staff();
            staffOnSession.setStaffID(0); // ID mặc định, có thể là 0 hoặc -1
        }else{
            Staff staffProfile = staffDAO.getStaffById(staffOnSession.getStaffID());   
            request.setAttribute("staffProfile", staffProfile);
            String roleName = staffDAO.getRoleNameByStaffID(staffOnSession.getStaffID());
            id = staffProfile.getStaffID();
        }
        String blogID = request.getParameter("blogID");
        
        
        
        
        if (action == null) {
            action = "viewAll";
        }
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
        String pageIndexStr = request.getParameter("page");

        if (pageIndexStr != null) {
            try {
                pageIndex = Integer.parseInt(pageIndexStr);
            } catch (NumberFormatException e) {
                pageIndex = 1;
            }
        }
        int totalStaff;
        list = dao.getAllBlogs(name, search, pageIndex, pagesize, column, sortOrder);
        int totalPageCount ;
        if(list.size()==0){
                    request.setAttribute("nolist", "Khong co danh sach ma ban can tim!");
                    name = "";
                    list = dao.getAllBlogs(name, search, pageIndex, pagesize, column, sortOrder);
                    totalStaff = dao.getAllBlogs(searchname, search, column, sortOrder).size();
                    totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                }else{
                
                    totalStaff = dao.getAllBlogs(searchname, search, column, sortOrder).size();
                    totalPageCount = (int) Math.ceil((double) totalStaff / pagesize);
                }
        Pagination pagination = new Pagination();

        pagination.setUrlPattern( "/BlogController");
        pagination.setCurrentPage(pageIndex);
        pagination.setPageSize(pagesize);
        pagination.setTotalPages(totalPageCount);
        pagination.setTotalPagesToShow(5); // Hiển thị tối đa 5 trang liền kề
        pagination.setSort(column == null ? "" : column);
        pagination.setOrder(sortOrder == null ? "" : sortOrder);

        List<String> searchFields = new ArrayList();
        List<String> searchValues = new ArrayList<>();
        if(searchname != null && !searchname.isEmpty()){
            searchFields.add("searchname");
            searchValues.add(searchname);
        }
        if(search != null && !search.isEmpty()){
            searchFields.add("search");
            searchValues.add(search);
        }
        if(page_size != null && !page_size.isEmpty()){
            searchFields.add("page_size");
            searchValues.add(page_size);
        }
        pagination.setSearchFields(searchFields.toArray(new String[searchFields.size()]));
        pagination.setSearchValues(searchValues.toArray(new String[searchValues.size()]));

       
        
        request.setAttribute("pagination", pagination);

        request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("search", search);
        request.setAttribute("searchname", searchname);
        request.setAttribute("column", column);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("page_size", page_size);
        
        switch(action){
            case "More":
                info = dao.getBlogByID(blogID);
                request.setAttribute("info", info);
                String eqID = info.get(0).getStaffID();
                int change = Integer.parseInt(eqID);
                request.setAttribute("change", change);
                request.getRequestDispatcher("Blog.jsp").forward(request, response);
                break;
            case "Create":
                request.setAttribute("mes", "mes");
                request.getRequestDispatcher("Blog.jsp").forward(request, response);
                break;
            case "SubmitCreate":
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                int staffID = id;
                String id1 = Integer.toString(staffID);
                boolean insert = dao.InsertBlog(id1, title, content);
                request.setAttribute("list", list);
                request.getRequestDispatcher("Blog.jsp").forward(request, response);
                break;
            case "Change":
                changeBlog = dao.getBlogByID(blogID);
                request.setAttribute("changeBlog", changeBlog);
                request.getRequestDispatcher("Blog.jsp").forward(request, response);
                break;
            case "Save":
                String contentChange = request.getParameter("content");
                Boolean save = dao.ChangeBlog(blogID, contentChange);
                info = dao.getBlogByID(blogID);
                request.setAttribute("info", info);
                request.setAttribute("update", "Thay doi thanh cong");
                request.getRequestDispatcher("Blog.jsp").forward(request, response);
                break;
            default:
                break;
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
