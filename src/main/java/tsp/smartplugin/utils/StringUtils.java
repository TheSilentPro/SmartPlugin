package tsp.smartplugin.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {

    public static String joinArgs(int start, int end, String[] args) {
        return String.join(" ", Arrays.copyOfRange(args, start, end));
    }

    public static String joinArgs(int start, String[] args) {
        return joinArgs(start, 0, args);
    }

    public static String joinArgs(String[] args) {
        return joinArgs(0, args);
    }

    public static List<String> colorize(List<String> list) {
        List<String> result = new ArrayList<>();
        for (String line : list) {
            result.add(colorize(line));
        }

        return result;
    }

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
