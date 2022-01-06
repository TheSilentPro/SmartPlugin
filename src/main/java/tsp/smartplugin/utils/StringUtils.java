package tsp.smartplugin.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for managing {@link String}'s
 *
 * @author TheSilentPro
 */
public final class StringUtils {

    private StringUtils() {}

    /**
     * Join an array of strings into a single {@link String}
     *
     * @param start The starting point
     * @param end The ending point
     * @param args The arguments to join\
     * @param delimiter The delimiter
     * @return Arguments joint in a single string
     */
    public static String joinArgs(int start, int end, String delimiter, String... args) {
        return String.join(delimiter, Arrays.copyOfRange(args, start, end));
    }

    public static String joinArgs(int start, String... args) {
        return joinArgs(start, args.length, " ", args);
    }

    public static String joinArgs(String... args) {
        return joinArgs(0, args.length, " ", args);
    }

    /**
     * Colorize a list of strings
     *
     * @param list The list to colorize
     * @return Colorized list
     */
    public static List<String> colorize(List<String> list) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(i, list.get(i));
        }

        return result;
    }

    /**
     * Colorize a single string
     *
     * @param string The string to colorize
     * @return Colorized Stirng
     */
    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
