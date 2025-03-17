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
            // Gia hạn default: lấy gói bảo hành hiện tại
            PackageWarranty pkg = pkgDao.getPackageWarrantyByID(packageWarrantyID);
            if(pkg != null){
                Date now = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(now);
                // Giả sử DurationMonths là 12 (nếu còn trường này, bạn có thể dùng pkg.getDurationMonths())
                int duration = 12;
                if(now.compareTo(pkg.getWarrantyEndDate()) <= 0){
                    // Nếu còn hiệu lực, cộng thêm duration tháng vào WarrantyEndDate
                    cal.setTime(pkg.getWarrantyEndDate());
                    cal.add(Calendar.MONTH, duration);
                    pkg.setWarrantyEndDate(cal.getTime());
                } else {
                    // Nếu đã hết hiệu lực, thiết lập lại WarrantyStartDate và WarrantyEndDate dựa trên ngày hiện tại
                    pkg.setWarrantyStartDate(now);
                    cal.setTime(now);
                    cal.add(Calendar.MONTH, duration);
                    pkg.setWarrantyEndDate(cal.getTime());
                }
                boolean updated = pkgDao.updatePackageWarranty(pkg);
                message = updated ? "Default warranty extended successfully." : "Failed to extend default warranty.";
            } else {
                message = "Package warranty not found.";
            }
        } else if(action.equals("extendExtended")){
            // Gia hạn extended: lấy packageWarrantyID và extendedWarrantyID được chọn từ form
            String extendedWarrantyID = request.getParameter("extendedWarrantyID");
            System.out.println("extendedWarrantyID la " + extendedWarrantyID);
            // Phương thức extendWarrantyDetail sẽ xử lý:
            // - Nếu ExtendedWarrantyDetail đã tồn tại cho gói bảo hành => cập nhật EndExtendedWarranty = EndExtendedWarranty + ExtendedPeriodInMonths (tính từ ExtendedWarranty)
            // - Nếu chưa tồn tại => tạo mới bản ghi với StartExtendedWarranty = now và EndExtendedWarranty = now + ExtendedPeriodInMonths
            boolean extendedUpdated = extDao.extendWarrantyDetail(packageWarrantyID, extendedWarrantyID);
            message = extendedUpdated ? "Extended warranty extended successfully." : "Failed to extend extended warranty.";
        }
        
        request.setAttribute("mess", message);
        // Sau khi xử lý, load lại trang gia hạn
        response.sendRedirect("packageWarranty?action=edit&packageWarrantyID="+ packageWarrantyID);
    }
    
    @Override
    public String getServletInfo() {
        return "Controller for extending warranty packages";
    }
}
