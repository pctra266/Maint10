package Controller.Account;

import DAO.CustomerDAO;
import DAO.PermissionDAO;
import DAO.StaffDAO;
import Model.Customer;

import Model.Staff;
import Utils.Google;
import Utils.GoogleInformation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

public class LoginGoogle extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PermissionDAO permissionDao = new PermissionDAO();
        StaffDAO staffDao = new StaffDAO();
        CustomerDAO customerDao = new CustomerDAO();
        HttpSession session = request.getSession();
        String code = request.getParameter("code");

        String accessToken = getToken(code);
        GoogleInformation user = getUserInfo(accessToken);

        Staff staff = staffDao.getStaffByEmail(user.getEmail());
        Customer customer = customerDao.getCustomerByEmail(user.getEmail());
        if (staff != null && staff.getEmail() != null && !staff.getEmail().isEmpty()) {
            session.setAttribute("staff", staff);
            session.setAttribute("roleId", staff.getRole());
            response.sendRedirect("Home");
        } else if (customer != null && customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            session.setAttribute("customer", customer);
            response.sendRedirect("Home");
        } else {
            System.out.println("Access Denied: User not found in database.");
            session.invalidate();
            request.setAttribute("notification", "Your account not exist!");
            request.getRequestDispatcher("LoginForm.jsp").forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public String getToken(String code) throws ClientProtocolException, IOException {
        String response = Request.Post(Google.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", Google.GOOGLE_CLIENT_ID)
                        .add("client_secret", Google.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Google.GOOGLE_REDIRECT_URI).add("code", code)
                        .add("grant_type", Google.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
        return accessToken;
    }

    public static GoogleInformation getUserInfo(final String accessToken) throws ClientProtocolException, IOException {
        String link = Google.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = Request.Get(link).execute().returnContent().asString();
        GoogleInformation googlePojo = new Gson().fromJson(response, GoogleInformation.class);
        return googlePojo;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
