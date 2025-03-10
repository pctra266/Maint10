/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Account;

import DAO.CustomerDAO;
import DAO.StaffDAO;
import Model.Customer;
import Model.GoogleInformation;
import Model.Staff;
import Utils.Google;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author PC
 */
public class LoginGoogle extends HttpServlet {

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
        StaffDAO staffDao = new StaffDAO();
        CustomerDAO customerDao = new CustomerDAO();
        HttpSession session = request.getSession();
        String code = request.getParameter("code");

        String accessToken = getToken(code);

        GoogleInformation user = getUserInfo(accessToken);

        Staff staff = staffDao.getStaffByEmail(user.getEmail());
        Customer customer = customerDao.getCustomerByEmail(user.getEmail());

        if (staff != null) {
            session.setAttribute("staff", staff);
            response.sendRedirect("HomePage.jsp");
        } else if (customer != null) {
            session.setAttribute("customer", customer);
            response.sendRedirect("HomePage.jsp");
        } else {
            response.sendRedirect("AcessDenied.jsp");
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    
       // Ham get token
    public String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(Google.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Google.GOOGLE_CLIENT_ID)
                        .add("client_secret", Google.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Google.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Google.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class
        );
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    // Ham lay thong tin ca nhna nguoi dung
    public static GoogleInformation getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Google.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        
        GoogleInformation googlePojo = new Gson().fromJson(response, GoogleInformation.class
        );

        return googlePojo;
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
