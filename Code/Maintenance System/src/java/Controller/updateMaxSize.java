    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
     */

    package Controller;

    import java.io.IOException;
    import java.io.PrintWriter;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;

    /**
     *
     * @author Tra Pham
     */
    @WebServlet(name="updateMaxSize", urlPatterns={"/updateMaxSize"})
    public class updateMaxSize extends HttpServlet {
        private static final int MAX_IMAGE_SIZE_MB = 10; 
        private static final int MAX_VIDEO_SIZE_MB = 10; 
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
                out.println("<title>Servlet updateMaxSize</title>");  
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Servlet updateMaxSize at " + request.getContextPath () + "</h1>");
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
            processRequest(request, response);
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
            String maxSizeImageStr = request.getParameter("maxSizeImage");
            String maxSizeVideoStr = request.getParameter("maxSizeVideo");

            String customSizeImageStr = request.getParameter("customSizeImage");
            String customSizeVideoStr = request.getParameter("customSizeVideo");

            String errorMessage = null;
            int maxSizeImageMB = 5; 
            int maxSizeVideoMB = 10;

               try {
                maxSizeImageMB = parseSize(maxSizeImageStr, customSizeImageStr, MAX_IMAGE_SIZE_MB);
                maxSizeVideoMB = parseSize(maxSizeVideoStr, customSizeVideoStr,  MAX_VIDEO_SIZE_MB);
            } catch (NumberFormatException e) {
                errorMessage = "Invalid input. Please enter a valid number.";
            }

            if (errorMessage != null) {
                request.setAttribute("errorMessage", errorMessage);
            } else {
                getServletContext().setAttribute("maxUploadSizeImageMB", maxSizeImageMB);
                getServletContext().setAttribute("maxUploadSizeVideoMB", maxSizeVideoMB);
                request.setAttribute("successMessage", "Updated successfully!");
            }

            getServletContext().setAttribute("maxUploadSizeImageMB", maxSizeImageMB);
            getServletContext().setAttribute("maxUploadSizeVideoMB", maxSizeVideoMB);

            response.sendRedirect("adminDashboard.jsp");
        }    
        
           private int parseSize(String sizeStr, String customSizeStr, int maxLimit) throws NumberFormatException {
        int sizeMB;
        if ("custom".equals(sizeStr) && customSizeStr != null && !customSizeStr.isEmpty()) {
            int customSize = Integer.parseInt(customSizeStr);
            sizeMB = Math.max(1, customSize);
            if (sizeMB > maxLimit) {
                throw new NumberFormatException("Size exceeds maximum limit of " + maxLimit + " MB.");
            }
        } else {
            sizeMB = Integer.parseInt(sizeStr);
            if (sizeMB > maxLimit) {
                throw new NumberFormatException("Size exceeds maximum limit of " + maxLimit + " MB.");
            }
        }
        return sizeMB;
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
