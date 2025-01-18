/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

public class NumberUtils {
    /**
     * Chuyển đổi chuỗi thành số nguyên.
     * @param str Chuỗi cần chuyển đổi.
     * @return Giá trị số nguyên nếu chuyển đổi thành công; null nếu chuỗi không phải là số hợp lệ.
     */
    public static Integer tryParseInt(String str) {
        try {
            return Integer.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public static Double tryParseDouble(String str) {
        try {
            return Double.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
