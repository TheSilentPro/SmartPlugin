package tsp.smartplugin.player;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * Utility class for a {@link Player}
 */
public class PlayerUtils {

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendConfigMessage(CommandSender receiver, String key) {
        receiver.sendMessage(MessageUtils.getMessage(key));
    }

    public static void sendMessage(CommandSender receiver, String message) {
        receiver.sendMessage(colorize(message));
    }

    public static int getPing(Player player) {
        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public UUID fetchUUIDNoException(String name) {
        try {
            return fetchUUID(name);
        } catch (IOException | ParseException ignored) {
            return null;
        }
    }

    public static UUID fetchUUID(String name) throws IOException, ParseException {
        return fetchUUID(name, 5000);
    }

    public static UUID fetchUUID(String name, int timeout) throws IOException, ParseException {
        String req = "https://api.mojang.com/users/profiles/minecraft/" + name;
        String line;
        StringBuilder response = new StringBuilder();
        URLConnection connection = new URL(req).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", "SmartPlugin-UUIDFetcher");
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()))) {
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(response.toString());
        return UUID.fromString(obj.get("id").toString());
    }

}
