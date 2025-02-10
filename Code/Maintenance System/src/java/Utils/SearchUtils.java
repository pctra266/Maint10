/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

/**
 *
 * @author ADMIN
 */
public class SearchUtils {
        // Xóa dấu cách thừa
    public static String normalizeString(String input) {
        if(input==null) return "";
        return input.trim().replaceAll("\\s+", " ").toLowerCase();
    }
    public static String normalizeString2(String input) {
        if(input==null) return "";
        return input.trim().replaceAll("\\s+", "").toLowerCase();
    }

    // Loại bỏ dấu tiếng Việt (nếu có)
    public static String removeAccents(String input) {
        if(input==null) return "";
        return java.text.Normalizer.normalize(input, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }

    // Chuẩn hóa trước khi tìm kiếm
    public static String preprocessSearchQuery(String query) {
        if(query==null) return "";
        return removeAccents(normalizeString(query));
    }
    public static String searchValidateNonSapce(String query) {
        if(query==null) return "";
        return removeAccents(normalizeString2(query));
    }
    
    //Advance, kho su dung
    public static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    int cost = (s1.charAt(i - 1) == s2.charAt(j - 1)) ? 0 : 1;
                    dp[i][j] = Math.min(Math.min(
                            dp[i - 1][j] + 1, // Xóa ký tự
                            dp[i][j - 1] + 1), // Thêm ký tự
                            dp[i - 1][j - 1] + cost // Thay thế ký tự
                    );
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    public static String soundex(String s) {
        char[] x = s.toUpperCase().toCharArray();
        char firstLetter = x[0];

        for (int i = 1; i < x.length; i++) {
            switch (x[i]) {
                case 'B':
                case 'F':
                case 'P':
                case 'V':
                    x[i] = '1';
                    break;
                case 'C':
                case 'G':
                case 'J':
                case 'K':
                case 'Q':
                case 'S':
                case 'X':
                case 'Z':
                    x[i] = '2';
                    break;
                case 'D':
                case 'T':
                    x[i] = '3';
                    break;
                case 'L':
                    x[i] = '4';
                    break;
                case 'M':
                case 'N':
                    x[i] = '5';
                    break;
                case 'R':
                    x[i] = '6';
                    break;
                default:
                    x[i] = '0';
            }
        }

        StringBuilder output = new StringBuilder().append(firstLetter);
        for (int i = 1; i < x.length; i++) {
            if (x[i] != '0' && x[i] != x[i - 1]) {
                output.append(x[i]);
            }
        }

        return output.append("0000").substring(0, 4);
    }

    public static boolean isPhoneticallySimilar(String input, String target) {
        return soundex(input).equals(soundex(target));
    }

    // Kiểm tra xem hai chuỗi có tương đồng không (mức sai số <= ngưỡng cho phép)
    public static boolean islevenshteinDistanceSimilar(String input, String target, int threshold) {
        return levenshteinDistance(input.toLowerCase(), target.toLowerCase()) <= threshold ;
    }
    
    public static boolean isSimilar(String input, String target) {
        return isPhoneticallySimilar(input, target)||islevenshteinDistanceSimilar(input, target, 1);
    }

}
