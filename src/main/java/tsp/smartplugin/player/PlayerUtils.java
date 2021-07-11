package tsp.smartplugin.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.function.UnaryOperator;

/**
 * Utility class for a {@link Player}
 */
public class PlayerUtils {

    public static void sendConfigMessage(CommandSender receiver, String key, UnaryOperator<String> function, String... args) {
        String message = MessageUtils.getMessage(key);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("$" + i, args[i]);
            }
        }

        receiver.sendMessage(MessageUtils.colorize(function != null ? function.apply(message) : message));
    }

    public static void sendConfigMessage(CommandSender receiver, String key, UnaryOperator<String> function) {
        sendConfigMessage(receiver, key, function, (String[]) null);
    }

    public static void sendConfigMessage(CommandSender receiver, String key, String... args) {
        sendConfigMessage(receiver, key, null, args);
    }

    public static void sendConfigMessage(CommandSender receiver, String key) {
        sendConfigMessage(receiver, key, null, (String[]) null);
    }

    public static void sendMessage(CommandSender receiver, String message, @Nullable UnaryOperator<String> function, @Nullable String... args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("$" + i, args[i]);
            }
        }

        receiver.sendMessage(MessageUtils.colorize(function != null ? function.apply(message) : message));
    }

    public static void sendMessage(CommandSender receiver, String message, UnaryOperator<String> function) {
        sendMessage(receiver, message, function, (String[]) null);
    }

    public static void sendMessage(CommandSender receiver, String message, String... args) {
        sendMessage(receiver, message, null, args);
    }

    public static void sendMessage(CommandSender receiver, String message) {
        sendMessage(receiver, message, null, (String[]) null);
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

    public static SkinInfo getSkinInfo(UUID uuid) throws IOException, ParseException {
        return getSkinInfo(uuid, 5000);
    }

    public static SkinInfo getSkinInfo(UUID uuid, int timeout) throws IOException, ParseException {
        String req = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false";
        String line;
        StringBuilder response = new StringBuilder();
        URLConnection connection = new URL(req).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", "SmartPlugin-SkinFetcher");
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()))) {
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        }
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject) parser.parse(response.toString());
        JSONArray properties = (JSONArray) obj.get("properties");
        JSONObject textures = (JSONObject) properties.get(0);
        
        String id = obj.get("id").toString();
        String name = obj.get("name").toString();
        String value = textures.get("value").toString();
        String signature = textures.get("signature").toString();
        return new SkinInfo(id, name, value, signature);
    }

}
