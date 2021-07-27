package tsp.smartplugin.mojang;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tsp.smartplugin.SmartPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class MojangAPI {

    private MojangAPI() {}

    public static JSONObject getUniqueId(String name, int timeout) throws IOException, ParseException {
        String req = "https://api.mojang.com/users/profiles/minecraft/" + name;
        String line;
        StringBuilder response = new StringBuilder();
        URLConnection connection = new URL(req).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getPlugin().getName() + "-UUIDFetcher");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = in.readLine()) != null) {
            response.append(line);
        }

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.toString());
    }

    public static JSONObject getSkinInfo(UUID uuid, int timeout) throws IOException, ParseException {
        String req = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "") + "?unsigned=false";
        String line;
        StringBuilder response = new StringBuilder();
        URLConnection connection = new URL(req).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getPlugin().getName() + "-SkinFetcher");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = in.readLine()) != null) {
            response.append(line);
        }

        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.toString());
    }

    public static JSONObject getSkinInfo(UUID uuid) throws IOException, ParseException {
        return getSkinInfo(uuid, 5000);
    }

    public static JSONArray getNameHistory(UUID uuid, int timeout) throws IOException, ParseException {
        String req = "https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names";
        String line;
        StringBuilder response = new StringBuilder();
        URLConnection connection = new URL(req).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getPlugin().getName() + "-NameHistoryFetcher");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = in.readLine()) != null) {
            response.append(line);
        }

        return (JSONArray) new JSONParser().parse(response.toString());
    }

    public static JSONArray getNameHistory(UUID uuid) throws IOException, ParseException {
        return getNameHistory(uuid, 5000);
    }

    public static List<String> getBlockedServers(int timeout) throws IOException {
        String req = "https://sessionserver.mojang.com/blockedservers";
        String line;
        List<String> blockedServers = new ArrayList<>();
        URLConnection connection = new URL(req).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getPlugin().getName() + "-BlockedServersFetcher");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = in.readLine()) != null) {
            blockedServers.add(line);
        }

        return blockedServers;
    }

    public static List<String> getBlockedServers() throws IOException {
        return getBlockedServers(5000);
    }

    public static JSONArray getServiceStatus(int timeout) throws IOException, ParseException {
        String req = "https://status.mojang.com/check";
        String line;
        StringBuilder response = new StringBuilder();
        URLConnection connection = new URL(req).openConnection();
        connection.setConnectTimeout(timeout);
        connection.setRequestProperty("User-Agent", SmartPlugin.getInstance().getPlugin().getName() + "-StatusFetcher");
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = in.readLine()) != null) {
            response.append(line);
        }

        return (JSONArray) new JSONParser().parse(response.toString());
    }

    public static JSONArray getServiceStatus() throws IOException, ParseException {
        return getServiceStatus(50000);
    }

}
