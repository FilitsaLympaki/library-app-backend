package gr.aueb.cf.librarymanagementsystem.utils;

public class StringUtils {

    public static String trim(String str) {
        if (str == null) {
            return null;
        }
        String trimmed = str.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
