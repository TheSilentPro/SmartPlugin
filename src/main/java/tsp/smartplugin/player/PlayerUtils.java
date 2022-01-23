package tsp.smartplugin.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tsp.smartplugin.SmartPlugin;
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
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Utility class for a {@link Player}
 */
public final class PlayerUtils {

    private PlayerUtils() {}

    /**
     * Send a message to a {@link CommandSender} or {@link Player}
     *
     * @param receiver The reciever of the message
     * @param message The message to send
     * @param function Optional: Function to apply to the message
     * @param args Optional: Arguments for replacing. Format: {$argX} where X can be any argument number starting from 0.
     */
    public static void sendMessage(CommandSender receiver, String message, @Nullable UnaryOperator<String> function, @Nullable String... args) {
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                message = message.replace("{$arg" + i + "}", args[i]);
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

    /**
     * Retrieve the ping of the {@link Player}
     *
     * @param player The player to check
     * @return The players ping. Returns -1 if an error occurs
     */
    public static int getPing(Player player) {
        if (ServerVersion.getVersion().isNewerThanOrEquals(ServerVersion.v_1_17)) {
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

    /**
     * Retrieve information about a {@link UUID} from Mojang
     *
     * @param uuid The uuid to check
     */
    public static void getPlayerInfo(UUID uuid, Consumer<PlayerInfo> playerInfo) {
        // TODO: finsih this brain melted
    }

    /**
     * Retrieve the unique id of a player based on their name
     *
     * @param name The player name
     * @param timeout Connection timeout
     */
    public static void getUniqueId(String name, int timeout, Consumer<UUID> uuid) {
        SmartPlugin.getInstance().getMojangAPI().getUniqueId(name, timeout, json -> {
            uuid.accept(UUID.fromString(json.get("id").toString()));
        });
    }

    public static void getUniqueId(String name, Consumer<UUID> uuid) {
        getUniqueId(name, 5000, uuid);
    }

    /**
     * Retrieve skin information about a {@link UUID}
     *
     * @param uuid The unique id to check
     * @param timeout Connection timeout
     */
    public static void getSkinInfo(UUID uuid, int timeout, Consumer<SkinInfo> skinInfo){
        SmartPlugin.getInstance().getMojangAPI().getSkinInfo(uuid, timeout, json -> {
            JsonArray properties = json.get("properties").getAsJsonArray();
            JsonObject textures = properties.get(0).getAsJsonObject();

            String id = json.get("id").toString();
            String name = json.get("name").toString();
            String value = textures.get("value").toString();
            String signature = textures.get("signature").toString();
            skinInfo.accept(new SkinInfo(id, name, value, signature));
        });
    }

    public static void getSkinInfo(UUID uuid, Consumer<SkinInfo> skinInfo) {
        getSkinInfo(uuid, 5000, skinInfo);
    }

    /**
     * Retrieve name history of a {@link UUID}
     *
     * @param uuid The unique id
     * @param timeout Connection timeout
     */
    public static void getNameHistory(UUID uuid, int timeout, Consumer<NameHistory> nameHistory) {
        SmartPlugin.getInstance().getMojangAPI().getNameHistory(uuid, timeout, json -> {
            Map<String, Long> history = new HashMap<>();
            for (JsonElement e : json) {
                JsonObject obj = e.getAsJsonObject();
                String name = obj.get("name").toString();
                long timestamp = obj.get("changedToAt") != null ? obj.get("changedToAt").getAsLong() : -1;
                history.put(name, timestamp);
            }

            nameHistory.accept(new NameHistory(uuid, history));
        });
    }

    public static void getNameHistory(UUID uuid, Consumer<NameHistory> nameHistory) {
        getNameHistory(uuid, 5000, nameHistory);
    }

}
