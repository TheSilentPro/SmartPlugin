package tsp.smartplugin.utils;

import java.util.regex.Pattern;

/**
 * Utility class for common {@link Pattern}'s
 *
 * @author TheSilentPro
 */
public final class Patterns {

    private Patterns() {}

    public static Pattern SPACE = Pattern.compile(" ");
    public static Pattern COLON = Pattern.compile(":");
    public static Pattern SEMI_COLON = Pattern.compile(";");
    public static Pattern DOT = Pattern.compile(".");
    public static Pattern COMMA = Pattern.compile(",");
    public static Pattern UNDERSCORE = Pattern.compile("_");
    public static Pattern EQUALS = Pattern.compile("=");

    public static Pattern HEX_CODE = Pattern.compile("(#[A-Fa-f0-9]{6})");

}
