/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.ComponentRequest;

import DAO.ComponentDAO;
import DAO.ComponentRequestDAO;
import Model.Component;
import Model.ComponentRequest;
import Model.ComponentRequestDetail;
import Model.Pagination;
import Model.ProductDetail;
import Utils.FormatUtils;
import Utils.SearchUtils;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tra Pham
 */
@WebServlet(name="ComponentRequestController", urlPatterns={"/componentRequest"})
public class ComponentRequestController extends HttpServlet {
    private static final int PAGE_SIZE = 5;
    private static final ComponentRequestDAO componentRequestDao = new ComponentRequestDAO();
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
            out.println("<title>Servlet ComponentRequestController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ComponentRequestController at " + request.getContextPath () + "</h1>");
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
        //action
        String action = request.getParameter("action");
        if(action == null){
            action = "viewComponentRequestDashboard";
        }
        System.out.println("action la: " + action);
//        String warrantyCardCode, String productCode,
//          String unknownProductCode,  String warrantyStatus,String typeMaintain, String sort, 
//          String order, int page, int pageSize
        //parameter
        String warrantyCardID = request.getParameter("warrantyCardID");
        String warrantyCardCode = SearchUtils.searchValidateNonSapce(request.getParameter("warrantyCardCode"));
        String productCode = SearchUtils.searchValidateNonSapce(request.getParameter("productCode"));
        String unknownProductCode = SearchUtils.searchValidateNonSapce(request.getParameter("unknownProductCode"));
        String warrantyStatus = request.getParameter("warrantyStatus");
        if(warrantyStatus== null || warrantyStatus.trim().isEmpty()){
            warrantyStatus = "fixing";
        }
        if(warrantyStatus.equalsIgnoreCase("all")){
            warrantyStatus = "";
        }
        String typeMaintain = request.getParameter("typeMaintain");
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String componentRequestID = request.getParameter("componentRequestID");
        String componentCode = SearchUtils.preprocessSearchQuery(request.getParameter("componentCode"));
        String componentName = SearchUtils.preprocessSearchQuery(request.getParameter("componentName"));
        String typeID = request.getParameter("typeID");
        String brandID = request.getParameter("brandID");
        String mess = request.getParameter("mess");
        String componentStatus = request.getParameter("componentStatus");
        request.setAttribute("componentStatus", componentStatus);
        request.setAttribute("mess", mess);
        request.setAttribute("brandID", brandID);
        request.setAttribute("typeID", typeID);
        request.setAttribute("componentName", componentName);
        request.setAttribute("componentCode", componentCode);
        request.setAttribute("componentRequestID", componentRequestID);
        request.setAttribute("warrantyCardID", warrantyCardID);
        request.setAttribute("warrantyCardCode", warrantyCardCode);
        request.setAttribute("productCode", productCode);
        request.setAttribute("unknownProductCode", unknownProductCode);
        request.setAttribute("warrantyStatus", warrantyStatus);
        request.setAttribute("typeMaintain", typeMaintain);
        request.setAttribute("sort", sort);
        request.setAttribute("order", order);
        request.setAttribute("typeList", componentRequestDao.getAllComponentType());
        request.setAttribute("brandList", componentRequestDao.getAllBrand());
        int total = 0;
        switch(action){
            case "viewComponentRequestDashboard":
                total = componentRequestDao.totalProductUnderMaintain(warrantyCardCode, productCode, unknownProductCode, warrantyStatus, typeMaintain);
                break;
            case "createComponentRequest":
                total = componentRequestDao.totalComponentByProductCode("", componentCode, componentName, typeID, brandID);
                break;
            case "viewListComponentRequest": case "updateStatusComponentRequest": case"addComponent":case"removeComponent":
                total = componentRequestDao.totalComponentRequest(warrantyCardCode);
                break;
        }
        //paging
        Pagination pagination = new Pagination();
        String pageParam = request.getParameter("page");
        String pageSizeParam = request.getParameter("page-size");
        int page = (FormatUtils.tryParseInt(pageParam) != null) ? FormatUtils.tryParseInt(pageParam) : 1;
        Integer pageSize;
        pageSize = (FormatUtils.tryParseInt(pageSizeParam) != null) ? FormatUtils.tryParseInt(pageSizeParam) : PAGE_SIZE;
        int totalPages = (int) Math.ceil((double) total / pageSize);
        if (page > totalPages) {
            page = totalPages;
        }
        page = page < 1 ? 1 : page;
                pagination.setListPageSize(total); 
                pagination.setCurrentPage(page);
                pagination.setTotalPages(totalPages);
                pagination.setTotalPagesToShow(5); 
                pagination.setPageSize(pageSize);
                pagination.setSort(sort);
                pagination.setOrder(order);
                pagination.setUrlPattern("/componentRequest");
        // Session
    HttpSession session = request.getSession();
    ArrayList<Component> selectedComponents = (ArrayList<Component>) session.getAttribute("selectedComponents");
        //do
        switch(action){
            case "viewComponentRequestDashboard":
                //======phan trang
                pagination.setSearchFields(new String[]{"action","productCode","unknownProductCode","warrantyStatus","typeMaintain"});
                pagination.setSearchValues(new String[]{"viewComponentRequestDashboard",productCode,unknownProductCode,warrantyStatus,typeMaintain});
                request.setAttribute("pagination", pagination);
                //======end phan trang
                ArrayList<ProductDetail> listProductUnderMaintain = componentRequestDao.getAllListProductUnderMaintain(warrantyCardCode, productCode, 
                unknownProductCode, warrantyStatus, typeMaintain, sort, order, page, pageSize);
                request.setAttribute("listProductUnderMaintain", listProductUnderMaintain);
                request.getRequestDispatcher("requestComponentDashboard.jsp").forward(request, response);
                break;
            case "createComponentRequest":
            String previousWarrantyCardID = (String) session.getAttribute("currentWarrantyCardID");
            if (warrantyCardID != null && !warrantyCardID.trim().isEmpty() && !warrantyCardID.equals(previousWarrantyCardID)) {
                if (productCode != null && !productCode.trim().isEmpty() ) {
                    
                    selectedComponents = componentRequestDao.getallListComponentByProductCode(productCode, null, null, null, null, 1, Integer.MAX_VALUE);
                } else {
                    selectedComponents = new ArrayList<>();
                }
                session.setAttribute("selectedComponents", selectedComponents);
                session.setAttribute("currentWarrantyCardID", warrantyCardID);
            } else if (warrantyCardID == null || warrantyCardID.trim().isEmpty()) {
                selectedComponents = new ArrayList<>();
                session.setAttribute("selectedComponents", selectedComponents);
                session.removeAttribute("currentWarrantyCardID");
            }
                //======phan trang
                pagination.setSearchFields(new String[]{"action","warrantyCardID","productCode","componentCode","componentName","typeID","brandID"});
                pagination.setSearchValues(new String[]{"createComponentRequest",warrantyCardID,productCode,componentCode,componentName,typeID,brandID});
                request.setAttribute("pagination", pagination);
                //======end phan trang
                ArrayList<Component> listComponentByProductCode = componentRequestDao.getallListComponentByProductCode("",componentCode,componentName,
                        typeID, brandID,page,pageSize);
                
                request.setAttribute("listComponentByProductCode", listComponentByProductCode);
                request.setAttribute("selectedComponents", selectedComponents != null ? selectedComponents : new ArrayList<>());
                request.getRequestDispatcher("createComponentRequest.jsp").forward(request, response);
                break;
                
            case "addComponent":
            if (selectedComponents == null) {
                selectedComponents = new ArrayList<>();
            }
            String componentID = request.getParameter("componentID");
            Component componentToAdd = getComponentById("", componentID);
            if (componentToAdd != null && !isComponentInList(selectedComponents, componentToAdd)) {
                componentToAdd.setQuantity(1); 
                selectedComponents.add(componentToAdd);
                session.setAttribute("selectedComponents", selectedComponents);
            }
                      //======phan trang
                pagination.setSearchFields(new String[]{"action","warrantyCardID","productCode","componentCode","componentName","typeID","brandID"});
                pagination.setSearchValues(new String[]{"createComponentRequest",warrantyCardID,productCode,componentCode,componentName,typeID,brandID});
                request.setAttribute("pagination", pagination);
                //======end phan trang
                ArrayList<Component> listComponentByProductCode1 = componentRequestDao.getallListComponentByProductCode("",componentCode,componentName,
                        typeID, brandID,page,pageSize);
                
                request.setAttribute("listComponentByProductCode", listComponentByProductCode1);
                request.setAttribute("selectedComponents", selectedComponents != null ? selectedComponents : new ArrayList<>());
                request.getRequestDispatcher("createComponentRequest.jsp").forward(request, response);
            break;

        case "removeComponent":
            if (selectedComponents != null) {
                String removeComponentID = request.getParameter("componentID");
                removeComponentById(selectedComponents, removeComponentID);
                session.setAttribute("selectedComponents", selectedComponents);
            }
                      //======phan trang
                pagination.setSearchFields(new String[]{"action","warrantyCardID","productCode","componentCode","componentName","typeID","brandID"});
                pagination.setSearchValues(new String[]{"createComponentRequest",warrantyCardID,productCode,componentCode,componentName,typeID,brandID});
                request.setAttribute("pagination", pagination);
                //======end phan trang
                ArrayList<Component> listComponentByProductCode2 = componentRequestDao.getallListComponentByProductCode("",componentCode,componentName,
                        typeID, brandID,page,pageSize);
                
                request.setAttribute("listComponentByProductCode", listComponentByProductCode2);
                request.setAttribute("selectedComponents", selectedComponents != null ? selectedComponents : new ArrayList<>());
                request.getRequestDispatcher("createComponentRequest.jsp").forward(request, response);
            break;
            case "viewListComponentRequest":
//                //======phan trang
//                pagination.setSearchFields(new String[]{"action","warrantyCardCode"});
//                pagination.setSearchValues(new String[]{"viewListComponentRequest",warrantyCardCode});
//                request.setAttribute("pagination", pagination);
//                //======end phan trang
//                ArrayList<ComponentRequest> listComponentRequest = componentRequestDao.getAllComponentRequest(warrantyCardCode,page, pageSize);
//                request.setAttribute("listComponentRequest", listComponentRequest);
//                request.getRequestDispatcher("viewListComponentRequest.jsp").forward(request, response);
                this.viewListComponentRequest(pagination, warrantyCardCode, page, pageSize, request, response);
                
                break;
            case "detailComponentRequest":
                ArrayList<ComponentRequestDetail> listComponentRequestDetail = componentRequestDao.getListComponentRequestDetailById(componentRequestID);
                request.setAttribute("listComponentRequestDetail", listComponentRequestDetail);
                request.getRequestDispatcher("detailComponentRequest.jsp").forward(request, response);
                break;
            case "updateStatusComponentRequest":
               componentRequestDao.updateStatusComponentRequest(componentRequestID,componentStatus);
              this.viewListComponentRequest(pagination, warrantyCardCode, page, pageSize, request, response);
                break;
        }
    }
    private Component getComponentById(String productCode, String componentID) {
    if (componentID == null || componentID.trim().isEmpty()) {
        return null;
    }
    ArrayList<Component> components = componentRequestDao.getallListComponentByProductCode(productCode, "", "", "", "", 1, Integer.MAX_VALUE);
    for (Component c : components) {
        if (c.getComponentID() == Integer.parseInt(componentID)) {
            return c;
        }
    }
    return null;
}private boolean isComponentInList(ArrayList<Component> selectedComponents, Component component) {
    if (selectedComponents == null || component == null) {
        return false;
    }
    for (Component c : selectedComponents) {
        if (c.getComponentID() == component.getComponentID()) {
            return true;
        }
    }
    return false;
    
}private void removeComponentById(ArrayList<Component> selectedComponents, String componentID) {
    if (selectedComponents == null || componentID == null || componentID.trim().isEmpty()) {
        return;
    }
    for (int i = 0; i < selectedComponents.size(); i++) {
        if (selectedComponents.get(i).getComponentID() == Integer.parseInt(componentID)) {
            selectedComponents.remove(i);
            break;
        }
    }
}
    
    private void viewListComponentRequest(Pagination pagination,String warrantyCardCode,int page, int pageSize, HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException{
        //======phan trang
                pagination.setSearchFields(new String[]{"action","warrantyCardCode"});
                pagination.setSearchValues(new String[]{"viewListComponentRequest",warrantyCardCode});
                request.setAttribute("pagination", pagination);
                //======end phan trang
                ArrayList<ComponentRequest> listComponentRequest = componentRequestDao.getAllComponentRequest(warrantyCardCode,page, pageSize);
                request.setAttribute("listComponentRequest", listComponentRequest);
                request.getRequestDispatcher("viewListComponentRequest.jsp").forward(request, response);
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
        
        //action
        String action = request.getParameter("action");
        
        
        if(action == null){
            action = "viewComponentRequestDashboard";
        }
        
        //parameter
        String productCode = request.getParameter("productCode");
        String warrantyCardIDstr = request.getParameter("warrantyCardID");
        String note = request.getParameter("note");
        request.setAttribute("note", note);
        request.setAttribute("warrantyCardID", warrantyCardIDstr);
        request.setAttribute("productCode", productCode);
        String[] quantities = request.getParameterValues("quantities");
        String[] componentIDs = request.getParameterValues("componentIDs");
        List<Integer> listQuantities = new ArrayList<Integer>();
        List<Integer> listComponentIDs = new ArrayList<Integer>();
           if (componentIDs != null && quantities != null) {
        for (int i = 0; i < componentIDs.length; i++) {
            try {
                int quantity = Integer.parseInt(quantities[i]); 
                if (quantity > 0) { 
                    listComponentIDs.add(Integer.parseInt(componentIDs[i]));
                    listQuantities.add(quantity);
                }
            } catch (NumberFormatException e) {
               
            }
        }
        
    }
           
        boolean valid = true;
        String mess ="";
        //do
        switch(action){
            case "createComponentRequest":
                // valid
                int totalQuantities = 0;
               for (Integer x : listQuantities) {
                   totalQuantities +=x;
               }
                if(totalQuantities <1){
                    valid = false;
                }
                System.out.println("totalQuantities: "+ totalQuantities);
                //end valid
            if(valid){
                if(!componentRequestDao.createComponentRequest(Integer.parseInt(warrantyCardIDstr), note, listComponentIDs, listQuantities)){
                    mess  = "Create fail";
                }else{
                    mess = "Create Successfully !";
                    request.getSession().removeAttribute("selectedComponents");
                }
                 response.sendRedirect("componentRequest?action=viewComponentRequestDashboard&mess="+mess);
                 return ;
            }else{
                mess = "invalid ...";
            }
            response.sendRedirect("componentRequest?action=createComponentRequest&warrantyCardID="+warrantyCardIDstr+"&productCode="+productCode+"&mess="+mess);
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
