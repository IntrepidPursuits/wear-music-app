package intrepid.weardemo.utils;

/**
 * Created by Chris on 8/27/14.
 */
public class CustomUtils {

    public static String stripNonLetters(String string) {
        return string.replaceAll("\\s", "").replaceAll("\\W", "");
    }
}
