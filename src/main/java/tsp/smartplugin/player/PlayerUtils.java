package tsp.smartplugin.player;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import tsp.smartplugin.mojang.MojangAPI;
import tsp.smartplugin.player.info.NameHistory;
import tsp.smartplugin.player.info.PlayerInfo;
import tsp.smartplugin.player.info.SkinInfo;
import tsp.smartplugin.server.ServerVersion;
import tsp.smartplugin.utils.ChatUtils;
import tsp.smartplugin.utils.MessageUtils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.UnaryOperator;

/**
 * Utility class for a {@link Player}
 */
public final class PlayerUtils {

    private PlayerUtils() {}

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendConfigMessage(CommandSender receiver, String key, @Nullable UnaryOperator<String> function, @Nullable String... args) {
        String message = MessageUtils.getMessage(key);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("$arg" + i, args[i]);
            }
        }

        receiver.sendMessage(ChatUtils.colorize(function != null ? function.apply(message) : message));
    }

    public static void sendConfigMessage(CommandSender receiver, String key, @Nullable UnaryOperator<String> function) {
        sendConfigMessage(receiver, key, function, (String[]) null);
    }

    public static void sendConfigMessage(CommandSender receiver, String key, @Nullable String... args) {
        sendConfigMessage(receiver, key, null, args);
    }

    public static void sendConfigMessage(CommandSender receiver, String key) {
        sendConfigMessage(receiver, key, null, (String[]) null);
    }

    public static void sendMessage(CommandSender receiver, String message, @Nullable UnaryOperator<String> function, @Nullable String... args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("$arg" + i, args[i]);
            }
        }

        receiver.sendMessage(ChatUtils.colorize(function != null ? function.apply(message) : message));
    }

    public static void sendMessage(CommandSender receiver, String message, @Nullable UnaryOperator<String> function) {
        sendMessage(receiver, message, function, (String[]) null);
    }

    public static void sendMessage(CommandSender receiver, String message, @Nullable String... args) {
        sendMessage(receiver, message, null, args);
    }

    public static void sendMessage(CommandSender receiver, String message) {
        sendMessage(receiver, message, null, (String[]) null);
    }

    public static void sendMessages(String message, CommandSender... receivers) {
        for (CommandSender receiver : receivers) {
            sendMessage(receiver, message);
        }
    }

    public static int getPing(Player player) {
        if (ServerVersion.getVersion().isHigherThanOrEquals(ServerVersion.v_1_17)) {
            return player.getPing();
        }

        try {
            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static PlayerInfo getPlayerInfo(UUID uuid) throws IOException, ParseException {
        return new PlayerInfo(uuid, getSkinInfo(uuid), getNameHistory(uuid));
    }

    public static PlayerInfo getPlayerInfo(String name) throws IOException, ParseException {
        UUID uuid = getUniqueId(name);

        return new PlayerInfo(uuid, getSkinInfo(uuid), getNameHistory(uuid));
    }

    public static UUID getUniqueId(String name, int timeout) throws IOException, ParseException {
        JSONObject obj = MojangAPI.getUniqueId(name, timeout);
        return UUID.fromString(obj.get("id").toString());
    }

    public static UUID getUniqueId(String name) throws IOException, ParseException {
        return getUniqueId(name, 5000);
    }

    public static UUID getUniqueIdNoException(String name) {
        try {
            return getUniqueId(name);
        } catch (IOException | ParseException ignored) {
            return null;
        }
    }

    public static SkinInfo getSkinInfo(UUID uuid, int timeout) throws IOException, ParseException {
        JSONObject obj = MojangAPI.getSkinInfo(uuid, timeout);
        JSONArray properties = (JSONArray) obj.get("properties");
        JSONObject textures = (JSONObject) properties.get(0);
        
        String id = obj.get("id").toString();
        String name = obj.get("name").toString();
        String value = textures.get("value").toString();
        String signature = textures.get("signature").toString();
        return new SkinInfo(id, name, value, signature);
    }

    public static SkinInfo getSkinInfo(UUID uuid) throws IOException, ParseException {
        return getSkinInfo(uuid, 5000);
    }

    public static NameHistory getNameHistory(UUID uuid, int timeout) throws IOException, ParseException {
        JSONArray array = MojangAPI.getNameHistory(uuid, timeout);
        Map<String, Long> history = new HashMap<>();
        for (Object o : array) {
            JSONObject obj = (JSONObject) o;
            String name = obj.get("name").toString();
            long timestamp = obj.get("changedToAt") != null ? (long) obj.get("changedToAt") : -1;
            history.put(name, timestamp);
        }

        return new NameHistory(uuid, history);
    }

    public static NameHistory getNameHistory(UUID uuid) throws IOException, ParseException {
        return getNameHistory(uuid, 5000);
    }

}
