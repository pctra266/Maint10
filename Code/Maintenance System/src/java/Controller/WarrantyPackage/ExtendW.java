package Controller.WarrantyPackage;

import DAO.ExtendedWarrantyDAO;
import DAO.PackageWarrantyDAO;
import Model.PackageWarranty;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ExtendWarrantyController", urlPatterns = {"/extendWarranty"})
public class ExtendW extends HttpServlet {
    
    private final PackageWarrantyDAO pkgDao = new PackageWarrantyDAO();
    private final ExtendedWarrantyDAO extDao = new ExtendedWarrantyDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xem trang gia hạn, cần truyền packageWarrantyID làm tham số
        String packageWarrantyID = request.getParameter("packageWarrantyID");
        if(packageWarrantyID != null && !packageWarrantyID.trim().isEmpty()){
            PackageWarranty pkg = pkgDao.getPackageWarrantyByID(packageWarrantyID);
            request.setAttribute("packageWarranty", pkg);
            // Load danh sách ExtendedWarranty cho phần extended (có thể dùng extDao.getListExtendedWarranty())
            request.setAttribute("extendedWarrantyList", extDao.getListExtendedWarranty());
        }
        request.getRequestDispatcher("extendWarranty.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xác định action: extendDefault hoặc extendExtended
        String action = request.getParameter("action");
        String packageWarrantyID = request.getParameter("packageWarrantyID");
        String message = "";
        
        if(action.equals("extendDefault")){
            PackageWarranty pkg = pkgDao.getPackageWarrantyByID(packageWarrantyID);
            if(pkg != null){
                Date now = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(now);
                int duration = 12;
                if(now.compareTo(pkg.getWarrantyEndDate()) <= 0){
                    cal.setTime(pkg.getWarrantyEndDate());
                    cal.add(Calendar.MONTH, duration);
                    pkg.setWarrantyEndDate(cal.getTime());
                } else {
                    pkg.setWarrantyStartDate(now);
                    cal.setTime(now);
                    cal.add(Calendar.MONTH, duration);
                    pkg.setWarrantyEndDate(cal.getTime());
                }
                boolean updated = pkgDao.updatePackageWarranty(pkg);
                message = updated ? "Default warranty extended successfully." : "Failed to extend default warranty.";
                if(updated){
                    pkg.setActive(true);
                    pkgDao.updateActive(pkg);
                }
            } else {
                message = "Package warranty not found.";
            }
        } else if(action.equals("extendExtended")){
            PackageWarranty pkg = pkgDao.getPackageWarrantyByID(packageWarrantyID);
            
            String extendedWarrantyID = request.getParameter("extendedWarrantyID");
            System.out.println("extendedWarrantyID la " + extendedWarrantyID);
            boolean extendedUpdated = extDao.extendWarrantyDetail(packageWarrantyID, extendedWarrantyID);
            message = extendedUpdated ? "Extended warranty extended successfully." : "Failed to extend extended warranty.";
            if(extendedUpdated){
                    pkg.setActive(true);
                    pkgDao.updateActive(pkg);
                }
        
        }
        request.setAttribute("message", message);
        //lay lai chi so
         String id = request.getParameter("packageWarrantyID");
            PackageWarranty pkg = pkgDao.getPackageWarrantyByID(id);
            request.setAttribute("packageWarranty", pkg);
            request.setAttribute("extendedWarrantyDetailList", extDao.getExtendedWarrantyDetailList(id));
            request.setAttribute("extendedWarrantyList", extDao.getListExtendedWarranty());
        //end lay lai chi so
        request.getRequestDispatcher("editPackageWarranty.jsp").forward(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Controller for extending warranty packages";
    }
}
