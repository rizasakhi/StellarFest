package util;

public class StringUtil {

    public static String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }

        return str.substring(0, maxLength) + "...";
    }

}
