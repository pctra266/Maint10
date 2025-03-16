// Package cho Controller sản phẩm
package Controller.Product; // Định nghĩa package chứa file code này

// Import các lớp DAO để truy xuất dữ liệu từ cơ sở dữ liệu
import DAO.StaffDAO; // Import lớp StaffDAO để lấy thông tin nhân viên
import DAO.WarrantyCardDAO; // Import lớp WarrantyCardDAO để lấy thông tin phiếu bảo hành

// Import các thư viện Java cần thiết
import java.io.IOException; // Để xử lý ngoại lệ I/O
import java.text.SimpleDateFormat; // Để định dạng ngày tháng theo mẫu
import java.util.List; // Để sử dụng cấu trúc dữ liệu List
import java.util.Map; // Để sử dụng cấu trúc dữ liệu Map

// Import các lớp từ thư viện iText để tạo file PDF
import com.itextpdf.text.BaseColor; // Để sử dụng màu sắc trong PDF
import com.itextpdf.text.Document; // Đại diện cho tài liệu PDF
import com.itextpdf.text.DocumentException; // Xử lý ngoại lệ khi làm việc với PDF
import com.itextpdf.text.Element; // Hỗ trợ căn chỉnh các phần tử trong PDF
import com.itextpdf.text.Font; // Để định nghĩa phông chữ sử dụng trong PDF
import com.itextpdf.text.FontFactory; // Để tạo các đối tượng Font
import com.itextpdf.text.Paragraph; // Để tạo đoạn văn trong PDF
import com.itextpdf.text.Phrase; // Để tạo các đoạn văn ngắn, cụm từ trong PDF
import com.itextpdf.text.Rectangle; // Để xác định hình chữ nhật (ví dụ: kích thước cell)
import com.itextpdf.text.pdf.PdfPCell; // Để tạo cell cho bảng PDF
import com.itextpdf.text.pdf.PdfPCellEvent; // Giao diện xử lý sự kiện của cell khi vẽ
import com.itextpdf.text.pdf.PdfContentByte; // Để vẽ các đối tượng đồ họa trong PDF
import com.itextpdf.text.pdf.PdfPTable; // Để tạo bảng trong PDF
import com.itextpdf.text.pdf.PdfWriter; // Để ghi tài liệu PDF vào OutputStream

// Import các thư viện của Jakarta Servlet
import jakarta.servlet.ServletException; // Để xử lý ngoại lệ liên quan đến Servlet
import jakarta.servlet.http.HttpServlet; // Lớp cơ sở cho các servlet xử lý HTTP
import jakarta.servlet.http.HttpServletRequest; // Đại diện cho yêu cầu HTTP từ client
import jakarta.servlet.http.HttpServletResponse; // Đại diện cho phản hồi HTTP gửi về cho client

// Import các model để ánh xạ dữ liệu từ cơ sở dữ liệu
import Model.Customer; // Model chứa thông tin khách hàng
import Model.Product; // Model chứa thông tin sản phẩm
import Model.Staff; // Model chứa thông tin nhân viên
import Model.WarrantyCard; // Model chứa thông tin phiếu bảo hành
import Model.UnknownProduct; // Model cho sản phẩm không xác định
import java.time.LocalDate; // Để làm việc với ngày hiện tại theo định dạng LocalDate

// Định nghĩa lớp ExportPDF kế thừa từ HttpServlet để xử lý yêu cầu HTTP
public class ExportPDF extends HttpServlet {

    // Khởi tạo đối tượng DAO để truy xuất thông tin phiếu bảo hành
    private final WarrantyCardDAO warrantyCardDAO = new WarrantyCardDAO();
    // Khởi tạo đối tượng DAO để truy xuất thông tin nhân viên
    private final StaffDAO staffDAO = new StaffDAO();

    // Lớp nội bộ dùng để vẽ đường viền nét đứt cho PdfPCell
    class DottedCell implements PdfPCellEvent {

        // Ghi đè phương thức cellLayout để tùy chỉnh cách vẽ của cell
        @Override
        public void cellLayout(PdfPCell cell, Rectangle rect, PdfContentByte[] canvas) {
            PdfContentByte cb = canvas[PdfPTable.LINECANVAS]; // Lấy đối tượng vẽ từ canvas theo kênh LINECANVAS
            cb.setLineDash(3f, 2f); // Thiết lập kiểu nét đứt: đoạn nét dài 3f, khoảng trắng 2f
            cb.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight()); // Vẽ hình chữ nhật dựa trên vị trí và kích thước của cell
            cb.stroke(); // Thực hiện lệnh vẽ đường nét đứt lên PDF
        }
    }

    // Hàm chuyển đổi chuỗi sao cho chữ cái đầu tiên viết hoa, phần còn lại viết thường
    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) { // Nếu chuỗi rỗng hoặc null
            return input; // Trả về chuỗi gốc
        }
        // Lấy ký tự đầu tiên chuyển thành chữ hoa và nối với phần còn lại chuyển thành chữ thường
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    // Hàm chuyển đổi số thành chữ tiếng Anh (hỗ trợ từ 0 đến hàng tỷ)
    private String convertNumberToEnglishWords(long number) {
        if (number < 0) { // Nếu số âm
            return "Invalid"; // Trả về chuỗi "Invalid"
        }
        if (number == 0) { // Nếu số bằng 0
            return "zero"; // Trả về "zero"
        }
        String[] units = {"", "thousand", "million", "billion"}; // Mảng chứa đơn vị: không, nghìn, triệu, tỷ
        String[] belowTwenty = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"}; // Mảng số từ 0 đến 19
        String[] tens = {"", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"}; // Mảng số hàng chục từ 20 trở lên

        String result = ""; // Khởi tạo chuỗi kết quả rỗng
        int unitIndex = 0;   // Biến theo dõi đơn vị hiện tại (0: không; 1: thousand; v.v.)

        while (number > 0) { // Lặp cho đến khi số bằng 0
            if (number % 1000 != 0) { // Nếu nhóm 3 chữ số hiện tại khác 0
                // Chuyển nhóm số đó thành chữ, nối đơn vị tương ứng và nối vào chuỗi kết quả hiện tại
                result = convertHundred((int) (number % 1000), belowTwenty, tens) + " " + units[unitIndex] + " " + result;
            }
            number /= 1000; // Chia số cho 1000 để chuyển sang nhóm số tiếp theo
            unitIndex++; // Tăng chỉ số đơn vị lên 1
        }
        return result.trim(); // Loại bỏ khoảng trắng thừa và trả về kết quả
    }

    // Hàm chuyển đổi số từ 0 đến 999 thành chữ tiếng Anh
    private String convertHundred(int number, String[] belowTwenty, String[] tens) {
        String result = ""; // Khởi tạo chuỗi kết quả
        if (number >= 100) { // Nếu số có hàng trăm
            result += belowTwenty[number / 100] + " hundred"; // Lấy chữ của hàng trăm và thêm từ "hundred"
            number %= 100; // Lấy phần dư sau khi chia cho 100 (hàng chục và đơn vị)
            if (number > 0) { // Nếu còn số dư
                result += " and "; // Nối thêm "and" giữa hàng trăm và phần còn lại
            }
        }
        if (number < 20) { // Nếu số nhỏ hơn 20
            result += belowTwenty[number]; // Dùng mảng belowTwenty để chuyển thành chữ
        } else { // Nếu số từ 20 trở lên
            result += tens[number / 10]; // Lấy hàng chục và chuyển thành chữ qua mảng tens
            if (number % 10 > 0) { // Nếu có đơn vị (số dư khác 0)
                result += "-" + belowTwenty[number % 10]; // Nối thêm dấu "-" và chữ của số đơn vị
            }
        }
        return result; // Trả về chuỗi kết quả cho số 0-999
    }

    // Hàm định dạng giá thành chuỗi số nguyên (loại bỏ phần thập phân nếu có)
    private String formatPrice(Object obj) {
        if (obj == null) { // Nếu đối tượng null
            return ""; // Trả về chuỗi rỗng
        }
        try {
            double value = Double.parseDouble(obj.toString()); // Chuyển đối tượng sang số double
            return String.valueOf((long) value); // Ép kiểu về long (loại bỏ phần thập phân) và chuyển thành chuỗi
        } catch (NumberFormatException e) { // Nếu có lỗi chuyển đổi số
            return obj.toString(); // Trả về chuỗi gốc của đối tượng
        }
    }

    // Hàm trả về chuỗi an toàn: nếu đối tượng null thì trả về chuỗi rỗng, ngược lại trả về toString()
    private String getSafeString(Object obj) {
        return (obj == null) ? "" : obj.toString(); // Sử dụng toán tử điều kiện để đảm bảo an toàn
    }

    // Ghi đè phương thức doGet của HttpServlet để xử lý yêu cầu GET từ client
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/pdf"); // Đặt loại nội dung của phản hồi là file PDF
        response.setHeader("Content-Disposition", "attachment; filename=\"WarrantyCard.pdf\""); // Đặt header để trình duyệt tải file PDF với tên "WarrantyCard.pdf"

        String warrantyCardCode = request.getParameter("warrantyCardCode"); // Lấy tham số "warrantyCardCode" từ yêu cầu
        WarrantyCard wr = warrantyCardDAO.searchWarrantyCardByCode(warrantyCardCode); // Tìm phiếu bảo hành dựa trên mã
        if (wr == null) { // Nếu không tìm thấy phiếu bảo hành
            return; // Dừng xử lý, không tạo PDF
        }

        Staff staff = staffDAO.getStaffById(wr.getHandlerID()); // Lấy thông tin nhân viên dựa trên handlerID của phiếu bảo hành
        Customer customer = warrantyCardDAO.getCustomerByWarrantyProductID(wr.getWarrantyProductID()); // Lấy thông tin khách hàng liên quan đến phiếu bảo hành
        Object product = warrantyCardDAO.getProductByWarrantyProductId(wr.getWarrantyProductID()); // Lấy thông tin sản phẩm liên quan (có thể là Product hoặc UnknownProduct)
        boolean isUnknownProduct = (product instanceof UnknownProduct); // Kiểm tra xem sản phẩm có phải là UnknownProduct không

        List<Map<String, Object>> component = warrantyCardDAO.getWarrantyCardDetails(wr.getWarrantyCardID()); // Lấy danh sách chi tiết linh kiện của phiếu bảo hành

        LocalDate today = LocalDate.now(); // Lấy ngày hiện tại theo LocalDate
        int day = today.getDayOfMonth();      // Lấy ngày trong tháng
        int month = today.getMonthValue();    // Lấy giá trị của tháng
        int year = today.getYear();           // Lấy năm hiện tại

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Tạo đối tượng định dạng ngày theo mẫu "dd-MM-yyyy"

        Document document = new Document(); // Tạo đối tượng Document cho file PDF
        try {
            PdfWriter.getInstance(document, response.getOutputStream()); // Liên kết Document với OutputStream của response để ghi PDF trực tiếp về client
            document.open(); // Mở tài liệu PDF để bắt đầu ghi nội dung

            // Tạo các đối tượng Font với kiểu và kích cỡ khác nhau
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16); // Font cho tiêu đề
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12); // Font cho tiêu đề các phần (header)
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);      // Font cho văn bản thông thường

            Paragraph title = new Paragraph("Warranty Card Preview", titleFont); // Tạo đoạn văn tiêu đề với nội dung "Warranty Card Preview"
            title.setAlignment(Element.ALIGN_CENTER); // Căn giữa tiêu đề
            document.add(title); // Thêm tiêu đề vào tài liệu PDF
            document.add(new Paragraph(" ")); // Thêm đoạn trống để tạo khoảng cách

            // ---------------- Tạo bảng thông tin (infoTable) ----------------
            PdfPTable infoTable = new PdfPTable(2); // Tạo bảng với 2 cột cho thông tin
            infoTable.setWidthPercentage(100); // Đặt bảng chiếm 100% chiều rộng trang
            infoTable.setSpacingBefore(5f); // Đặt khoảng cách phía trên bảng là 5f
            infoTable.setSpacingAfter(5f);  // Đặt khoảng cách phía dưới bảng là 5f

            // ---- Tạo cell bên trái chứa thông tin cửa hàng ----
            PdfPCell storeLeftCell = new PdfPCell(); // Tạo cell mới cho bên trái
            storeLeftCell.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            Paragraph storeLeftPara = new Paragraph(); // Tạo đoạn văn để chứa thông tin cửa hàng
            storeLeftPara.setAlignment(Element.ALIGN_CENTER); // Căn giữa nội dung đoạn văn
            storeLeftPara.add(new Phrase("Store: ABC Electronics\n", normalFont)); // Thêm thông tin tên cửa hàng
            storeLeftPara.add(new Phrase("Address: 123 XYZ Street, Hanoi\n", normalFont)); // Thêm địa chỉ cửa hàng
            storeLeftPara.add(new Phrase("Contact: 0901 234 567\n", normalFont)); // Thêm số điện thoại cửa hàng
            storeLeftPara.add(new Phrase("Email: support@abc-electronics.com\n", normalFont)); // Thêm email cửa hàng
            storeLeftCell.addElement(storeLeftPara); // Thêm đoạn văn vào cell
            infoTable.addCell(storeLeftCell); // Thêm cell vào bảng thông tin

            // ---- Tạo cell bên phải chứa thông tin Form, Serial, No ----
            PdfPCell storeRightCell = new PdfPCell(); // Tạo cell mới cho bên phải
            storeRightCell.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            Paragraph storeRightPara = new Paragraph(); // Tạo đoạn văn cho thông tin bên phải
            storeRightPara.setAlignment(Element.ALIGN_CENTER); // Căn giữa nội dung đoạn văn
            storeRightPara.add(new Phrase("Form: support@abc-electronics.com\n", normalFont)); // Thêm thông tin Form
            storeRightPara.add(new Phrase("Serial: support@abc-electronics.com\n", normalFont)); // Thêm thông tin Serial
            storeRightPara.add(new Phrase("No: support@abc-electronics.com\n", normalFont)); // Thêm thông tin No
            storeRightCell.addElement(storeRightPara); // Thêm đoạn văn vào cell
            infoTable.addCell(storeRightCell); // Thêm cell vào bảng thông tin

            // ---- Tạo spacer row trước khi thêm đường kẻ ----
            PdfPCell spacer1 = new PdfPCell(new Phrase("")); // Tạo cell rỗng để làm khoảng cách
            spacer1.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell spacer
            spacer1.setColspan(2); // Đặt cell này chiếm 2 cột (toàn bộ bảng)
            spacer1.setFixedHeight(10f); // Đặt chiều cao cố định là 10f
            infoTable.addCell(spacer1); // Thêm cell spacer vào bảng

            // ---- Tạo cell vẽ đường kẻ (lineCell1) để phân chia các thông tin ----
            PdfPCell lineCell1 = new PdfPCell(new Phrase("")); // Tạo cell rỗng để vẽ đường kẻ
            lineCell1.setBorder(PdfPCell.BOTTOM); // Chỉ hiển thị đường viền dưới của cell
            lineCell1.setBorderWidthBottom(2f);  // Đặt độ dày đường viền dưới là 2f
            lineCell1.setBorderColor(BaseColor.BLACK); // Đặt màu đường viền là màu đen
            lineCell1.setColspan(2); // Đặt cell này chiếm 2 cột
            infoTable.addCell(lineCell1); // Thêm cell vẽ đường kẻ vào bảng

            // ---- Tạo row chứa thông tin phiếu bảo hành (bên trái) và thông tin nhân viên (bên phải) ----
            // --- Tạo cell chứa thông tin phiếu bảo hành ---
            PdfPCell warrantyLeftCell = new PdfPCell(); // Tạo cell mới cho thông tin phiếu bảo hành bên trái
            warrantyLeftCell.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            Paragraph warrantyLeftPara = new Paragraph(); // Tạo đoạn văn chứa thông tin phiếu bảo hành
            warrantyLeftPara.add(new Phrase("Warranty Card Code: " + wr.getWarrantyCardCode() + "\n", normalFont)); // Thêm mã phiếu bảo hành vào đoạn văn
            if (wr.getCreatedDate() != null) { // Nếu có ngày tạo phiếu bảo hành
                warrantyLeftPara.add(new Phrase("Created Date: " + sdf.format(wr.getCreatedDate()) + "\n", normalFont)); // Thêm ngày tạo đã định dạng vào đoạn văn
            }
            if (wr.getCompletedDate() != null) { // Nếu có ngày trả hoặc hoàn thành
                warrantyLeftPara.add(new Phrase("Return Date: " + sdf.format(wr.getCompletedDate()) + "\n", normalFont)); // Thêm ngày trả đã định dạng vào đoạn văn
            }
            warrantyLeftCell.addElement(warrantyLeftPara); // Thêm đoạn văn vào cell
            infoTable.addCell(warrantyLeftCell); // Thêm cell vào bảng thông tin

            // --- Tạo cell chứa thông tin nhân viên phụ trách ---
            PdfPCell staffRightCell = new PdfPCell(); // Tạo cell mới cho thông tin nhân viên bên phải
            staffRightCell.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            Paragraph staffRightPara = new Paragraph(); // Tạo đoạn văn chứa thông tin nhân viên
            if (staff != null) { // Nếu thông tin nhân viên không null
                staffRightPara.add(new Phrase("Staff in charge: " + staff.getName() + "\n", normalFont)); // Thêm tên nhân viên vào đoạn văn
                staffRightPara.add(new Phrase("Staff phone: " + staff.getPhone() + "\n", normalFont)); // Thêm số điện thoại nhân viên vào đoạn văn
                staffRightPara.add(new Phrase("Staff email: " + staff.getEmail() + "\n", normalFont)); // Thêm email nhân viên vào đoạn văn
            }
            staffRightCell.addElement(staffRightPara); // Thêm đoạn văn vào cell
            infoTable.addCell(staffRightCell); // Thêm cell vào bảng thông tin

            // ---- Tạo spacer row thứ 2 ----
            PdfPCell spacer2 = new PdfPCell(new Phrase("")); // Tạo cell rỗng để làm khoảng cách thứ 2
            spacer2.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell spacer
            spacer2.setColspan(2); // Đặt cell này chiếm 2 cột
            spacer2.setFixedHeight(10f); // Đặt chiều cao cố định là 10f
            infoTable.addCell(spacer2); // Thêm cell spacer vào bảng

            // ---- Tạo cell vẽ đường kẻ thứ 2 để phân chia các thông tin ----
            PdfPCell lineCell2 = new PdfPCell(new Phrase("")); // Tạo cell rỗng để vẽ đường kẻ thứ 2
            lineCell2.setBorder(PdfPCell.BOTTOM); // Chỉ hiển thị đường viền dưới của cell
            lineCell2.setBorderWidthBottom(2f);  // Đặt độ dày đường viền dưới là 2f
            lineCell2.setBorderColor(BaseColor.BLACK); // Đặt màu đường viền là màu đen
            lineCell2.setColspan(2); // Đặt cell này chiếm 2 cột
            infoTable.addCell(lineCell2); // Thêm cell vẽ đường kẻ thứ 2 vào bảng

            // ---- Tạo row chứa thông tin khách hàng (bên trái) và thông tin sản phẩm (bên phải) ----
            // --- Tạo cell chứa thông tin khách hàng ---
            PdfPCell customerLeftCell = new PdfPCell(); // Tạo cell mới cho thông tin khách hàng bên trái
            customerLeftCell.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            Paragraph customerLeftPara = new Paragraph("Customer\n", headerFont); // Tạo đoạn văn với tiêu đề "Customer" dùng font header
            if (customer != null) { // Nếu thông tin khách hàng không null
                customerLeftPara.add(new Phrase("Name: " + customer.getName() + "\n", normalFont)); // Thêm tên khách hàng vào đoạn văn
                if (customer.getDateOfBirth() != null) { // Nếu có ngày sinh khách hàng
                    customerLeftPara.add(new Phrase("DOB: " + sdf.format(customer.getDateOfBirth()) + "\n", normalFont)); // Thêm ngày sinh đã định dạng vào đoạn văn
                }
                customerLeftPara.add(new Phrase("Phone: " + customer.getPhone() + "\n", normalFont)); // Thêm số điện thoại khách hàng vào đoạn văn
                customerLeftPara.add(new Phrase("Email: " + customer.getEmail() + "\n", normalFont)); // Thêm email khách hàng vào đoạn văn
                customerLeftPara.add(new Phrase("Address: " + customer.getAddress() + "\n", normalFont)); // Thêm địa chỉ khách hàng vào đoạn văn
            } else { // Nếu không có thông tin khách hàng
                customerLeftPara.add(new Phrase("No customer info.\n", normalFont)); // Thêm thông báo không có thông tin khách hàng
            }
            customerLeftCell.addElement(customerLeftPara); // Thêm đoạn văn vào cell
            infoTable.addCell(customerLeftCell); // Thêm cell vào bảng thông tin

            // --- Tạo cell chứa thông tin sản phẩm ---
            PdfPCell productRightCell = new PdfPCell(); // Tạo cell mới cho thông tin sản phẩm bên phải
            productRightCell.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            Paragraph productRightPara = new Paragraph("Product\n", headerFont); // Tạo đoạn văn với tiêu đề "Product" dùng font header
            if (isUnknownProduct) { // Nếu sản phẩm thuộc kiểu UnknownProduct
                UnknownProduct up = (UnknownProduct) product; // Ép kiểu đối tượng thành UnknownProduct
                productRightPara.add(new Phrase("Name: " + up.getProductName() + "\n", normalFont)); // Thêm tên sản phẩm vào đoạn văn
                productRightPara.add(new Phrase("Code: " + up.getProductCode() + "\n", normalFont)); // Thêm mã sản phẩm vào đoạn văn
                productRightPara.add(new Phrase("Description: " + up.getDescription() + "\n", normalFont)); // Thêm mô tả sản phẩm vào đoạn văn
                if (up.getReceivedDate() != null) { // Nếu có ngày nhận sản phẩm
                    productRightPara.add(new Phrase("Received Date: " + up.getReceivedDate() + "\n", normalFont)); // Thêm ngày nhận sản phẩm vào đoạn văn
                }
            } else { // Nếu sản phẩm thuộc kiểu Product thông thường
                Product p = (Product) product; // Ép kiểu đối tượng thành Product
                productRightPara.add(new Phrase("Name: " + p.getProductName() + "\n", normalFont)); // Thêm tên sản phẩm vào đoạn văn
                productRightPara.add(new Phrase("Code: " + p.getCode() + "\n", normalFont)); // Thêm mã sản phẩm vào đoạn văn
                productRightPara.add(new Phrase("Brand: " + p.getBrandName() + "\n", normalFont)); // Thêm thương hiệu sản phẩm vào đoạn văn
                productRightPara.add(new Phrase("Type: " + p.getProductTypeName() + "\n", normalFont)); // Thêm loại sản phẩm vào đoạn văn
                if (p.getPurchaseDate() != null) { // Nếu có ngày mua sản phẩm
                    productRightPara.add(new Phrase("Purchased Date: " + p.getPurchaseDate() + "\n", normalFont)); // Thêm ngày mua sản phẩm vào đoạn văn
                }
            }
            productRightCell.addElement(productRightPara); // Thêm đoạn văn vào cell
            infoTable.addCell(productRightCell); // Thêm cell vào bảng thông tin

            document.add(infoTable); // Thêm bảng thông tin (infoTable) vào tài liệu PDF

            // ---------------- Tạo bảng chi tiết linh kiện ----------------
            PdfPTable table = new PdfPTable(8); // Tạo bảng với 8 cột để hiển thị thông tin chi tiết linh kiện
            table.setWidthPercentage(100); // Đặt bảng chiếm 100% chiều rộng trang
            table.setSpacingBefore(10f);   // Đặt khoảng cách trước bảng là 10f
            table.setSpacingAfter(10f);    // Đặt khoảng cách sau bảng là 10f

            // Mảng chứa tiêu đề cho 8 cột của bảng
            String[] headers = {
                "Component Code", "Component Name", "Brand Name", "Type Name",
                "Price($)", "Quantity", "Note", "Total Price($)"
            };
            Font headerTableFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10); // Tạo font cho tiêu đề bảng với kích thước 10 và kiểu in đậm
            for (String header : headers) { // Duyệt qua từng tiêu đề trong mảng
                PdfPCell cell = new PdfPCell(new Phrase(header, headerTableFont)); // Tạo cell chứa tiêu đề
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY); // Đặt màu nền cho cell là xám nhạt
                cell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa nội dung cell
                table.addCell(cell); // Thêm cell tiêu đề vào bảng
            }

            double grandTotal = 0.0; // Khởi tạo biến để tính tổng tiền của các linh kiện

            if (component != null && !component.isEmpty()) { // Nếu danh sách linh kiện không null và không rỗng
                for (Map<String, Object> detail : component) { // Duyệt qua từng linh kiện trong danh sách
                    table.addCell(new Phrase(getSafeString(detail.get("ComponentCode")), normalFont)); // Thêm cell chứa Component Code
                    table.addCell(new Phrase(getSafeString(detail.get("ComponentName")), normalFont)); // Thêm cell chứa Component Name
                    table.addCell(new Phrase(getSafeString(detail.get("BrandName")), normalFont)); // Thêm cell chứa Brand Name
                    table.addCell(new Phrase(getSafeString(detail.get("TypeName")), normalFont)); // Thêm cell chứa Type Name
                    table.addCell(new Phrase(formatPrice(detail.get("pricePerPiece")), normalFont)); // Thêm cell chứa Price per piece (định dạng giá)
                    table.addCell(new Phrase(getSafeString(detail.get("numberOfUses")), normalFont)); // Thêm cell chứa số lượng sử dụng (Quantity)
                    table.addCell(new Phrase(getSafeString(detail.get("Note")), normalFont)); // Thêm cell chứa Note
                    table.addCell(new Phrase(formatPrice(detail.get("totalPrice")), normalFont)); // Thêm cell chứa Total Price (định dạng giá)

                    if (detail.get("totalPrice") != null) { // Nếu trường totalPrice không null
                        try {
                            double price = Double.parseDouble(detail.get("totalPrice").toString()); // Chuyển đổi totalPrice sang kiểu double
                            grandTotal += price; // Cộng dồn vào biến grandTotal
                        } catch (NumberFormatException ex) { // Nếu gặp lỗi chuyển đổi số
                            // Bỏ qua lỗi nếu chuyển đổi không thành công
                        }
                    }
                }
            } else { // Nếu danh sách linh kiện rỗng hoặc null
                PdfPCell noDataCell = new PdfPCell(new Phrase("No data available.", normalFont)); // Tạo cell thông báo "No data available."
                noDataCell.setColspan(8); // Đặt cell chiếm 8 cột
                noDataCell.setHorizontalAlignment(Element.ALIGN_CENTER); // Căn giữa nội dung cell
                table.addCell(noDataCell); // Thêm cell vào bảng
            }
            document.add(table); // Thêm bảng chi tiết linh kiện vào tài liệu PDF

            // ---------------- Hiển thị tổng tiền ----------------
            Paragraph totalPara = new Paragraph(); // Tạo đoạn văn để hiển thị tổng tiền
            totalPara.add(new Phrase("Total: " + formatPrice(grandTotal) + "$" + "\n", normalFont)); // Thêm dòng hiển thị tổng tiền dưới dạng số và kèm dấu "$"
            long totalLong = (long) grandTotal; // Ép kiểu tổng tiền sang long (loại bỏ phần thập phân)
            String totalWords = convertNumberToEnglishWords(totalLong); // Chuyển tổng tiền thành chữ tiếng Anh
            totalWords = capitalizeFirstLetter(totalWords); // Viết hoa chữ cái đầu tiên của chuỗi số
            totalPara.add(new Phrase("Total (in words): " + totalWords + " dollars\n", normalFont)); // Thêm dòng hiển thị tổng tiền dưới dạng chữ
            document.add(totalPara); // Thêm đoạn văn tổng tiền vào tài liệu PDF

            // ---------------- Tạo bảng chữ ký (Signature Main Table) ----------------
            PdfPTable signatureMainTable = new PdfPTable(2); // Tạo bảng chữ ký với 2 cột
            signatureMainTable.setWidthPercentage(100); // Đặt bảng chữ ký chiếm 100% chiều rộng trang
            signatureMainTable.setWidths(new float[]{1f, 1f}); // Đặt tỷ lệ hai cột bằng nhau
            signatureMainTable.setSpacingBefore(20f); // Đặt khoảng cách phía trên bảng chữ ký là 20f

            // ---- Hàng 1: Tạo cell bên trái chứa "Signature Confirmation" ----
            PdfPCell leftCell1 = new PdfPCell(new Phrase("Signature Confirmation", normalFont)); // Tạo cell chứa "Signature Confirmation"
            leftCell1.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            leftCell1.setHorizontalAlignment(Element.ALIGN_LEFT); // Căn trái nội dung cell
            leftCell1.setPadding(0f); // Không padding
            signatureMainTable.addCell(leftCell1); // Thêm cell vào bảng chữ ký

            // ---- Hàng 1: Tạo cell bên phải chứa thông tin ngày (ví dụ "Hanoi, dd/mm/yyyy") ----
            PdfPCell rightCell1 = new PdfPCell(new Phrase("Hanoi, " + day + "/" + month + "/" + year, normalFont)); // Tạo cell chứa thông tin ngày với thành phố "Hanoi"
            rightCell1.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            rightCell1.setHorizontalAlignment(Element.ALIGN_RIGHT); // Căn phải nội dung cell
            rightCell1.setPadding(0f); // Không padding
            signatureMainTable.addCell(rightCell1); // Thêm cell vào bảng chữ ký

            // ---- Hàng 2: Tạo spacer row để tạo khoảng cách giữa các hàng chữ ký ----
            PdfPCell spacerRow = new PdfPCell(new Phrase("")); // Tạo cell rỗng làm khoảng cách
            spacerRow.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            spacerRow.setColspan(2); // Đặt cell chiếm 2 cột
            spacerRow.setFixedHeight(15f); // Đặt chiều cao cố định là 15f
            signatureMainTable.addCell(spacerRow); // Thêm cell spacer vào bảng chữ ký

            // ---- Hàng 3: Tạo sub-table cho chữ ký của Customer và Technical Staff ----
            // --- Sub-table bên trái cho chữ ký của Customer ---
            PdfPTable leftSubTable = new PdfPTable(2); // Tạo bảng con với 2 cột cho chữ ký của Customer
            leftSubTable.setWidthPercentage(100); // Đặt bảng con chiếm 100% chiều rộng cell chứa nó
            leftSubTable.setWidths(new float[]{1f, 3f}); // Đặt tỷ lệ cột của bảng con là 1:3
            leftSubTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền mặc định cho các cell trong bảng con

            PdfPCell custLabelCell = new PdfPCell(new Phrase("Customer:", normalFont)); // Tạo cell chứa nhãn "Customer:"
            custLabelCell.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            custLabelCell.setPadding(0f); // Không padding
            leftSubTable.addCell(custLabelCell); // Thêm cell vào bảng con

            PdfPCell dottedCell1 = new PdfPCell(); // Tạo cell rỗng để hiển thị đường nét đứt cho phần chữ ký của Customer
            dottedCell1.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền mặc định
            dottedCell1.setCellEvent(new DottedCell()); // Gán sự kiện vẽ đường nét đứt thông qua lớp DottedCell
            dottedCell1.setFixedHeight(25f); // Đặt chiều cao của cell là 25f
            dottedCell1.setHorizontalAlignment(Element.ALIGN_LEFT); // Căn trái nội dung cell
            dottedCell1.setPadding(0f); // Không padding
            leftSubTable.addCell(dottedCell1); // Thêm cell có đường nét đứt vào bảng con

            PdfPCell leftCell3 = new PdfPCell(); // Tạo cell mới để chứa bảng con của Customer
            leftCell3.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            leftCell3.addElement(leftSubTable); // Thêm bảng con vào cell
            signatureMainTable.addCell(leftCell3); // Thêm cell vào bảng chữ ký (cột bên trái của hàng 3)

            // --- Sub-table bên phải cho chữ ký của Technical Staff ---
            PdfPTable rightSubTable = new PdfPTable(2); // Tạo bảng con với 2 cột cho chữ ký của Technical Staff
            rightSubTable.setWidthPercentage(100); // Đặt bảng con chiếm 100% chiều rộng cell chứa nó
            rightSubTable.setWidths(new float[]{2f, 4f}); // Đặt tỷ lệ cột của bảng con là 2:4
            rightSubTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền mặc định cho các cell trong bảng con

            PdfPCell staffLabelCell = new PdfPCell(new Phrase("Technical Staff:", normalFont)); // Tạo cell chứa nhãn "Technical Staff:"
            staffLabelCell.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            staffLabelCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Căn phải nội dung cell
            staffLabelCell.setPaddingRight(5f); // Đặt padding bên phải là 5f
            rightSubTable.addCell(staffLabelCell); // Thêm cell vào bảng con

            PdfPCell dottedCell2 = new PdfPCell(); // Tạo cell rỗng để hiển thị đường nét đứt cho phần chữ ký của Technical Staff
            dottedCell2.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền mặc định
            dottedCell2.setCellEvent(new DottedCell()); // Gán sự kiện vẽ đường nét đứt thông qua lớp DottedCell
            dottedCell2.setFixedHeight(25f); // Đặt chiều cao của cell là 25f
            dottedCell2.setHorizontalAlignment(Element.ALIGN_LEFT); // Căn trái nội dung cell
            dottedCell2.setPadding(0f); // Không padding
            rightSubTable.addCell(dottedCell2); // Thêm cell có đường nét đứt vào bảng con

            PdfPCell rightCell3 = new PdfPCell(); // Tạo cell mới để chứa bảng con của Technical Staff
            rightCell3.setBorder(PdfPCell.NO_BORDER); // Loại bỏ đường viền của cell
            rightCell3.addElement(rightSubTable); // Thêm bảng con vào cell
            signatureMainTable.addCell(rightCell3); // Thêm cell vào bảng chữ ký (cột bên phải của hàng 3)

            document.add(signatureMainTable); // Thêm bảng chữ ký vào tài liệu PDF

        } catch (DocumentException e) { // Nếu có ngoại lệ trong quá trình tạo PDF
            throw new IOException("Error while creating PDF: " + e.getMessage()); // Ném ra IOException với thông báo lỗi
        } finally {
            document.close(); // Đóng tài liệu PDF để giải phóng tài nguyên
        }
    }
}
