/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Permission;

import DAO.PermissionDAO;

import Model.Permissions;
import Model.RolePermission;
import Model.Roles;
import Utils.FormatUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author PC
 */
public class PermissionsServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
 PermissionDAO permissionDao = new PermissionDAO();
    String roleIdStr = request.getParameter("roleId");

    if (roleIdStr == null || roleIdStr.trim().isEmpty()) {
        request.setAttribute("message", "Vui lòng chọn một vai trò!");
        request.getRequestDispatcher("Permissions.jsp").forward(request, response);
        return;
    }

    try {
        int roleId = Integer.parseInt(roleIdStr);

        ArrayList<Permissions> permissionList = permissionDao.getAllPermission();
        List<Integer> rolePermissions = permissionDao.getPermissiIDonByRoleID(roleId); // Sửa tên hàm

        request.setAttribute("permissionList", permissionList);
        request.setAttribute("rolePermissions", new HashSet<>(rolePermissions));
        request.setAttribute("roleId", roleId);

        request.getRequestDispatcher("Permissions.jsp").forward(request, response);

    } catch (NumberFormatException e) {
        e.printStackTrace();
        request.setAttribute("message", "Role ID không hợp lệ!");
        request.getRequestDispatcher("Permissions.jsp").forward(request, response);
    }
    }
    

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PermissionDAO permissionDao = new PermissionDAO();
        String role = request.getParameter("role_id");

       

        if (role == null || role.trim().isEmpty()) {
            request.setAttribute("message", "Invalid role ID!");
            request.getRequestDispatcher("Permissions.jsp").forward(request, response);
            return;
        }

        try {

            int roleId = Integer.parseInt(role);

            String[] selectedPermissions = request.getParameterValues("permissions");
            List<Integer> currentPermissions = permissionDao.getPermissiIDonByRoleID(roleId);
            List<Integer> newPermissions = new ArrayList<>();

            if (selectedPermissions != null) {
                for (String permId : selectedPermissions) {
                    newPermissions.add(Integer.parseInt(permId));
                }
            }

            // Xóa quyền bị bỏ tích
            for (Integer perm : currentPermissions) {
                if (!newPermissions.contains(perm)) {
                    permissionDao.deletePermission(roleId, perm);
                }
            }

            // Thêm quyền mới được tích
            for (Integer perm : newPermissions) {
                if (!currentPermissions.contains(perm)) {
                    permissionDao.addRoleAndPermission(roleId, perm);
                }
            }

            response.sendRedirect("permissions?roleId=" + roleId);


        } catch (NumberFormatException e) {
            request.setAttribute("message", "Role ID không hợp lệ!");

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
