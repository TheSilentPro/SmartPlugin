package tsp.smartplugin.mojang;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import tsp.smartplugin.SmartPlugin;
import tsp.smartplugin.player.info.NameHistory;
import tsp.smartplugin.player.info.SkinInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Class for async fetching non-authenticated info from mojang.
 *
 * @author TheSilentPro
 */
public class MojangAPI {

    private final JavaPlugin plugin;
    private final BukkitScheduler scheduler;

    public MojangAPI(JavaPlugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
    }

    /**
     * Retrieve the unique id of a player based on their name
     *
     * @param name The player name
     * @param timeout Connection timeout
     */
    public void getUniqueId(String name, int timeout, Consumer<UUID> uuid) {
        getUniqueIdJson(name, timeout, json -> {
            uuid.accept(UUID.fromString(json.get("id").toString()));
        });
    }

    public void getUniqueId(String name, Consumer<UUID> uuid) {
        getUniqueId(name, 5000, uuid);
    }

    /**
     * Retrieve skin information about a {@link UUID}
     *
     * @param uuid The unique id to check
     * @param timeout Connection timeout
     */
    public void getSkinInfo(UUID uuid, int timeout, Consumer<SkinInfo> skinInfo){
        getSkinInfoJson(uuid, timeout, json -> {
            JsonArray properties = json.get("properties").getAsJsonArray();
            JsonObject textures = properties.get(0).getAsJsonObject();

            String id = json.get("id").toString();
            String name = json.get("name").toString();
            String value = textures.get("value").toString();
            String signature = textures.get("signature").toString();
            skinInfo.accept(new SkinInfo(id, name, value, signature));
        });
    }

    public void getSkinInfo(UUID uuid, Consumer<SkinInfo> skinInfo) {
        getSkinInfo(uuid, 5000, skinInfo);
    }

    /**
     * Retrieve name history of a {@link UUID}
     *
     * @param uuid The unique id
     * @param timeout Connection timeout
     */
    public void getNameHistory(UUID uuid, int timeout, Consumer<NameHistory> nameHistory) {
        getNameHistoryJson(uuid, timeout, json -> {
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

    public void getNameHistory(UUID uuid, Consumer<NameHistory> nameHistory) {
        getNameHistory(uuid, 5000, nameHistory);
    }

    // Json
    public void getUniqueIdJson(String name, int timeout, Consumer<JsonObject> json) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            try {
                String req = "https://api.mojang.com/users/profiles/minecraft/" + name;
                String line;
                StringBuilder response = new StringBuilder();
                URLConnection connection = new URL(req).openConnection();
                connection.setConnectTimeout(timeout);
                connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getName() + "-UUIDFetcher");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                json.accept(JsonParser.parseString(response.toString()).getAsJsonObject());
            } catch (IOException ex) {
                ex.printStackTrace();
                json.accept(null);
            }
        });
    }

    public void getUniqueIdJson(String name, Consumer<JsonObject> json) {
        getUniqueIdJson(name, 5000, json);
    }

    public void getSkinInfoJson(UUID uuid, int timeout, Consumer<JsonObject> json) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            try {
                String req = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false";
                String line;
                StringBuilder response = new StringBuilder();
                URLConnection connection = new URL(req).openConnection();
                connection.setConnectTimeout(timeout);
                connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getName() + "-SkinFetcher");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }

                json.accept(JsonParser.parseString(response.toString()).getAsJsonObject());
            } catch (IOException ex) {
                ex.printStackTrace();
                json.accept(null);
            }
        });
    }

    public void getSkinInfoJson(UUID uuid, Consumer<JsonObject> json) {
        getSkinInfoJson(uuid, 5000, json);
    }

    public void getNameHistoryJson(UUID uuid, int timeout, Consumer<JsonArray> json) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            try {
                String req = "https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names";
                String line;
                StringBuilder response = new StringBuilder();
                URLConnection connection = new URL(req).openConnection();
                connection.setConnectTimeout(timeout);
                connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getName() + "-NameHistoryFetcher");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = in.readLine()) != null) {
                    response.append(line);
                }

                json.accept(JsonParser.parseString(response.toString()).getAsJsonArray());
            } catch (IOException ex) {
                ex.printStackTrace();
                json.accept(null);
            }
        });
    }

    public void getNameHistoryJson(UUID uuid, Consumer<JsonArray> json) {
        getNameHistoryJson(uuid, 5000, json);
    }

    public void getServiceStatusJson(int timeout, Consumer<JsonArray> json) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            try {
                String req = "https://status.mojang.com/check";
                String line;
                StringBuilder response = new StringBuilder();
                URLConnection connection = new URL(req).openConnection();
                connection.setConnectTimeout(timeout);
                connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getName() + "-StatusFetcher");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = in.readLine()) != null) {
                    response.append(line);
                }

                json.accept(JsonParser.parseString(response.toString()).getAsJsonArray());
            } catch (IOException ex) {
                ex.printStackTrace();
                json.accept(null);
            }
        });
    }

    public void getServiceStatusJson(Consumer<JsonArray> json) {
        getServiceStatusJson(5000, json);
    }

    public void getBlockedServersJson(int timeout, Consumer<List<String>> list) {
        scheduler.runTaskAsynchronously(plugin, () -> {
            try {
                String req = "https://sessionserver.mojang.com/blockedservers";
                String line;
                List<String> blockedServers = new ArrayList<>();
                URLConnection connection = new URL(req).openConnection();
                connection.setConnectTimeout(timeout);
                connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getName() + "-BlockedServersFetcher");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = in.readLine()) != null) {
                    blockedServers.add(line);
                }

                list.accept(blockedServers);
            } catch (IOException ex) {
                ex.printStackTrace();
                list.accept(null);
            }
        });
    }

    public void getBlockedServersJson(Consumer<List<String>> list) {
        getBlockedServersJson(5000, list);
    }

}
