package tsp.smartplugin.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
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
import java.util.function.UnaryOperator;

/**
 * Utility class for a {@link Player}
 */
public class PlayerUtils {

    public static void sendConfigMessage(CommandSender receiver, String key, UnaryOperator<String> function) {
        receiver.sendMessage(MessageUtils.colorize(function.apply(MessageUtils.getMessage(key))));
    }

    public static void sendConfigMessage(CommandSender receiver, String key) {
        receiver.sendMessage(MessageUtils.colorize(MessageUtils.getMessage(key)));
    }

    public static void sendMessage(CommandSender receiver, String message, UnaryOperator<String> function) {
        receiver.sendMessage(MessageUtils.colorize(function.apply(message)));
    }

    public static void sendMessage(CommandSender receiver, String message) {
        receiver.sendMessage(MessageUtils.colorize(message));
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
