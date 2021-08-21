package tsp.smartplugin.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tsp.smartplugin.mojang.MojangAPI;
import tsp.smartplugin.player.info.NameHistory;
import tsp.smartplugin.player.info.PlayerInfo;
import tsp.smartplugin.player.info.SkinInfo;
import tsp.smartplugin.server.ServerVersion;
import tsp.smartplugin.utils.StringUtils;

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

    public static void sendMessage(CommandSender receiver, String message, @Nullable UnaryOperator<String> function, @Nullable String... args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("$arg" + i, args[i]);
            }
        }

        receiver.sendMessage(StringUtils.colorize(function != null ? function.apply(message) : message));
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

    public static PlayerInfo getPlayerInfo(UUID uuid) throws IOException {
        return new PlayerInfo(uuid, getSkinInfo(uuid), getNameHistory(uuid));
    }

    public static PlayerInfo getPlayerInfo(String name) throws IOException {
        UUID uuid = getUniqueId(name);

        return new PlayerInfo(uuid, getSkinInfo(uuid), getNameHistory(uuid));
    }

    public static UUID getUniqueId(String name, int timeout) throws IOException {
        JsonObject obj = MojangAPI.getUniqueId(name, timeout);
        return UUID.fromString(obj.get("id").toString());
    }

    public static UUID getUniqueId(String name) throws IOException {
        return getUniqueId(name, 5000);
    }

    public static UUID getUniqueIdNoException(String name) {
        try {
            return getUniqueId(name);
        } catch (IOException ignored) {
            return null;
        }
    }

    public static SkinInfo getSkinInfo(UUID uuid, int timeout) throws IOException {
        JsonObject obj = MojangAPI.getSkinInfo(uuid, timeout);
        JsonArray properties = obj.get("properties").getAsJsonArray();
        JsonObject textures = properties.get(0).getAsJsonObject();
        
        String id = obj.get("id").toString();
        String name = obj.get("name").toString();
        String value = textures.get("value").toString();
        String signature = textures.get("signature").toString();
        return new SkinInfo(id, name, value, signature);
    }

    public static SkinInfo getSkinInfo(UUID uuid) throws IOException {
        return getSkinInfo(uuid, 5000);
    }

    public static NameHistory getNameHistory(UUID uuid, int timeout) throws IOException {
        JsonArray array = MojangAPI.getNameHistory(uuid, timeout);
        Map<String, Long> history = new HashMap<>();
        for (JsonElement e : array) {
            JsonObject obj = e.getAsJsonObject();
            String name = obj.get("name").toString();
            long timestamp = obj.get("changedToAt") != null ? obj.get("changedToAt").getAsLong() : -1;
            history.put(name, timestamp);
        }

        return new NameHistory(uuid, history);
    }

    public static NameHistory getNameHistory(UUID uuid) throws IOException {
        return getNameHistory(uuid, 5000);
    }

}
