package club.someoneice.pineapplepsychic.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    public static String stringHumpToUnderline (String str) {
        String letter = "([A-Z])";
        Matcher matcher = Pattern.compile(letter).matcher(str);
        while (matcher.find()) {
            String getStr = matcher.group();
            str = str.replaceAll(getStr, "_" + getStr.toLowerCase());
        }
        return str;
    }

    public static String stringUnderlineToHump (String str) {
        String letter = "_(.)";
        Matcher matcher = Pattern.compile(letter).matcher(str);
        while (matcher.find()) {
            String getStr = matcher.group(1);
            str = str.replaceAll("_" + getStr, getStr.toUpperCase());
        }
        return str;
    }

    public static String stringToLowOnFirst (String str){
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}