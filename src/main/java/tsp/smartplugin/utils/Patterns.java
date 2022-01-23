package tsp.smartplugin.utils;

import java.util.regex.Pattern;

/**
 * Utility class for common {@link Pattern}'s
 * In order to use them you will need to call {@link #compile()} first.
 *
 * @author TheSilentPro
 */
public final class Patterns {

    private Patterns() {}

    public static Pattern SPACE;
    public static Pattern COLON;
    public static Pattern DOT;
    public static Pattern COMMA;
    public static Pattern UNDERSCORE;
    public static Pattern EQUALS;

    public static Pattern HEX_CODE;

    public static void compile() {
        SPACE = Pattern.compile(" ");
        COLON = Pattern.compile(";");
        DOT = Pattern.compile(".");
        COMMA = Pattern.compile(",");
        UNDERSCORE = Pattern.compile("_");
        EQUALS = Pattern.compile("=");
        HEX_CODE = Pattern.compile("(#[A-Fa-f0-9]{6})");
    }

}
