package tsp.smartplugin.mojang;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tsp.smartplugin.SmartPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class for fetching non-authenticated info from Mojang
 * Responses are formatted in JSON. https://wiki.vg/Mojang_API
 *
 * @author TheSilentPro
 */
public final class MojangAPI {

    private MojangAPI() {}

    public static JsonObject getUniqueId(String name, int timeout) throws IOException {
        String req = "https://api.mojang.com/users/profiles/minecraft/" + name;
        String line;
        StringBuilder response = new StringBuilder();
        URLConnection connection = new URL(req).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getName() + "-UUIDFetcher");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = in.readLine()) != null) {
            response.append(line);
        }

        return JsonParser.parseString(response.toString()).getAsJsonObject();
    }

    public static JsonObject getSkinInfo(UUID uuid, int timeout) throws IOException {
        String req = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false";
        String line;
        StringBuilder response = new StringBuilder();
        URLConnection connection = new URL(req).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getName() + "-SkinFetcher");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = in.readLine()) != null) {
            response.append(line);
        }

        return JsonParser.parseString(response.toString()).getAsJsonObject();
    }

    public static JsonObject getSkinInfo(UUID uuid) throws IOException {
        return getSkinInfo(uuid, 5000);
    }

    public static JsonArray getNameHistory(UUID uuid, int timeout) throws IOException {
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

        return JsonParser.parseString(response.toString()).getAsJsonArray();
    }

    public static JsonArray getNameHistory(UUID uuid) throws IOException {
        return getNameHistory(uuid, 5000);
    }

    public static List<String> getBlockedServers(int timeout) throws IOException {
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

        return blockedServers;
    }

    public static List<String> getBlockedServers() throws IOException {
        return getBlockedServers(5000);
    }

    public static JsonArray getServiceStatus(int timeout) throws IOException {
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

        return JsonParser.parseString(response.toString()).getAsJsonArray();
    }

    public static JsonArray getServiceStatus() throws IOException {
        return getServiceStatus(50000);
    }

}
