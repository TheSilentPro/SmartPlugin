package tsp.smartplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Consumer;

public class PluginUtils {

    /**
     * Retrieve the latest release from spigot
     *
     * @param plugin The plugin
     * @param id The resource id on spigot
     * @param latest Whether the plugin is on the latest version
     */
    public static void isLatestVersion(JavaPlugin plugin, int id, Consumer<Boolean> latest) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, checkTask -> {
            try {
                URLConnection connection = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + id).openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestProperty("User-Agent", plugin.getName() + "-VersionChecker");

                latest.accept(new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine().equals(plugin.getDescription().getVersion()));
            } catch (IOException e) {
                latest.accept(true); // Assume the version is latest if checking fails as to avoid any confusion
                e.printStackTrace();
            }
        });
    }

}
