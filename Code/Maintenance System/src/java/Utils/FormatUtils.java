/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtils {

    /**
     * Chuyển đổi chuỗi thành số nguyên.
     *
     * @param str Chuỗi cần chuyển đổi.
     * @return Giá trị số nguyên nếu chuyển đổi thành công; null nếu chuỗi không
     * phải là số hợp lệ.
     */
    public static Integer tryParseInt(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public static Double tryParseDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public static Date parseDate(String returnDateString) {
        Date returnDate = null;

        if (returnDateString != null && !returnDateString.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust the format as needed
            try {
                returnDate = dateFormat.parse(returnDateString);
            } catch (ParseException e) {
                return null;
                // Handle the exception (e.g., set returnDate to null or show an error message)
            }
        }
        return returnDate;
    }
// Assuming this is inside a method where you handle the request

}
