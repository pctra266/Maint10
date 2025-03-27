/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package Filter;

import DAO.PermissionDAO;
import Model.Permissions;
import Model.Staff;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author ADMIN
 */
public class AuthenticationFilter implements Filter {

    private PermissionDAO permissionDao = new PermissionDAO();

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public AuthenticationFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("AuthenticationFilter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        if (debug) {
            log("AuthenticationFilter:doFilter()");
        }

        doBeforeProcessing(request, response);
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false); // Không tạo session mới nếu chưa có

        PermissionDAO permissionDao = new PermissionDAO();
        Integer roleId = (session != null) ? (Integer) session.getAttribute("roleId") : null;
        Integer customerId = (session != null) ? (Integer) session.getAttribute("customerId") : null;

        // Chỉ lấy đường dẫn chính, bỏ qua query string
        String servletPath = req.getServletPath();
        String action = req.getParameter("action");
        // Nêu la may cai file nmhu nay thi bo qua mong la noi chay
        if (servletPath.matches(".*\\.(jpg|png|css|js|ico|gif|woff|woff2|ttf|svg)$")) {
            chain.doFilter(request, response);
            return;
        }
        // Danh sách URL không cần kiểm tra quyền
        Set<String> EXCLUDED_URLS = Set.of("/401Page.jsp", "/login", "/logout", "/ForgotPasswordForm.jsp",
                "/LoginForm.jsp", "/profile", "/Home", "/chatBox.jsp", "/chatRoomServer", "/login-google",

                "/SearchWarrantyController", "/customerContact?action=createCustomerContact",
                "/BlogController", "/BlogController?action=More", "/changepassword",
                "/img/serviceItems/","/Notification/GetUnread","/Notification/MarkRead","/Redirect",
                "/css/light.css","/js/app.js","/ChangePasswordForm.jsp","/dashBoard.jsp");

        // Danh sách URL mà Customer được phép truy cập
        Set<String> CUSTOMER_ALLOWED_URLS = Set.of(
                "/feedback?action=viewFeedbackDashboard",
                "/feedback?action=createFeedback",
                "/feedback?action=viewListFeedbackByCustomerId",
                "/feedback?action=deleteFeedbackFromCustomer",
                "/yourwarrantycard",
                "/yourWarrantyCardDetail",
                "/purchaseproduct",
                "/WarrantyCard/Add",
                "/Redirect"
        );

        System.out.println("Requested URL: " + servletPath);
        System.out.println("Customer ID: " + customerId);
        System.out.println("Role ID (staff): " + roleId);

        if (EXCLUDED_URLS.contains(servletPath)) {
            System.out.println("Skipping auth check for: " + servletPath);
            chain.doFilter(request, response);
            return;
        }

        if (customerId != null) {
            if (!CUSTOMER_ALLOWED_URLS.contains((action == null) ? servletPath : servletPath + "?action=" + action)) {
                System.out.println("Customer not allowed to access: " + servletPath);
                res.sendRedirect(req.getContextPath() + "/401Page.jsp");
                return;
            } else {
                System.out.println("Customer allowed to access: " + servletPath);
                chain.doFilter(request, response);
                return;
            }
        }

        if (roleId == null) {
            System.out.println("User is not logged in, redirecting to login page.");
            res.sendRedirect("LoginForm.jsp");
            return;
        }

        String requiredPermission = (action == null) ? servletPath : servletPath + "?action=" + action;
        System.out.println("Checking permission for: " + requiredPermission);

        if (!permissionDao.checkRolePermission(roleId, requiredPermission)) {
            System.out.println("Staff does not have permission for: " + requiredPermission);
            res.sendRedirect(req.getContextPath() + "/401Page.jsp");
            return;
        }

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(request, response);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }

    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("AuthenticationFilter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("AuthenticationFilter()");
        }
        StringBuffer sb = new StringBuffer("AuthenticationFilter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
    /*
    public boolean hasPermission(HttpSession session, String per) {
        if (session.getAttribute("customer") != null) {
            return false;
        }
        if (session.getAttribute("staff") != null) {
            System.out.println("check1");
            Staff staff = (Staff) session.getAttribute("staff");
            return staff.hasPermissions(per);
        }
        return false;
    }
     */
}
