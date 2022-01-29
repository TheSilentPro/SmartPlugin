package tsp.smartplugin.mojang;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import tsp.smartplugin.SmartPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
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

    public void getUniqueId(String name, int timeout, Consumer<JsonObject> json) {
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

    public void getUniqueId(String name, Consumer<JsonObject> json) {
        getUniqueId(name, 5000, json);
    }

    public void getSkinInfo(UUID uuid, int timeout, Consumer<JsonObject> json) {
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

    public void getSkinInfo(UUID uuid, Consumer<JsonObject> json) {
        getSkinInfo(uuid, 5000, json);
    }

    public void getNameHistory(UUID uuid, int timeout, Consumer<JsonArray> json) {
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

    public void getNameHistory(UUID uuid, Consumer<JsonArray> json) {
        getNameHistory(uuid, 5000, json);
    }

    public void getServiceStatus(int timeout, Consumer<JsonArray> json) {
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

    public void getServiceStatus(Consumer<JsonArray> json) {
        getServiceStatus(5000, json);
    }

    public void getBlockedServers(int timeout, Consumer<List<String>> list) {
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

    public void getBlockedServers(Consumer<List<String>> list) {
        getBlockedServers(5000, list);
    }

}
