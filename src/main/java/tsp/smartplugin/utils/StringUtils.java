package tsp.smartplugin.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

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

    /**
     * Colorize a string as gradient such that each character is a different color.
     * If there are more characters than passed colors then the colors will repeat.
     *
     * @param string The string to colorize
     * @param colors The colors used for gradient
     * @return String in gradient colors
     */
    public static String gradient(String string, ChatColor... colors) {
        StringBuilder builder = new StringBuilder();
        int color = 0;
        for (char c : string.toCharArray()) {
            builder.append(colors[color]).append(c);

            color = (color + 1 < colors.length) ? color + 1 : 0;
        }

        return builder.toString();
    }

    /**
     * Colorize a string as gradient such that each word will be a different color.
     * Words will be split with the provided pattern.
     * Notice: This uses {@link String#split(String)} which compiles a pattern each time it is used!
     *
     * @param string The string to colorize
     * @param colors The colors used for gradient
     * @return String in gradient colors
     * @see #gradientWord(String, Pattern, ChatColor...)
     */
    public static String gradientWord(String string, String delimiter, ChatColor... colors) {
        String[] args = string.split(delimiter);
        StringBuilder builder = new StringBuilder();
        int color = 0;
        for (String arg : args) {
            builder.append(colors[color]).append(arg);
        }

        return builder.toString();
    }

    /**
     * Colorize a string as gradient with the specified delimiter.
     *
     * @param string The string to colorize
     * @param colors The colors used for gradient
     * @return String in gradient colors
     */
    public static String gradientWord(String string, Pattern delimiter, ChatColor... colors) {
        String[] args = delimiter.split(string);
        StringBuilder builder = new StringBuilder();
        int color = 0;
        for (String arg : args) {
            builder.append(colors[color]).append(arg);
        }

        return builder.toString();
    }

    /**
     * Capitalizes the first letter of the string.
     *
     * @param string The string
     * @return String with the first letter capitalized
     */
    public static String capitalize(String string) {
        return string.substring(0, 1).toUpperCase(Locale.ROOT) + string.substring(1);
    }

    /**
     * Capitalize the first letter of every word in a string using the specified delimiter.
     *
     * @param string The string
     * @param delimiter The split pattern
     * @return String with capitalized words
     */
    public static String capitalizeWords(String string, Pattern delimiter) {
        StringBuilder builder = new StringBuilder();
        String[] words = delimiter.split(string);
        for (String word : words) {
            builder.append(capitalize(word)).append(delimiter);
        }

        return builder.toString();
    }

    /**
     * Capitalize the first letter of every word in a string with space (" ") as a delimiter.
     * Notice: You should use the method above and provide an already compiled pattern!
     *
     * @param string The string
     * @return String with capitalized words
     * @see #capitalizeWords(String, Pattern)
     */
    public static String capitalizeWords(String string) {
        return capitalizeWords(string, Pattern.compile(" "));
    }

}
