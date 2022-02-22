package tsp.smartplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class SmartCommand implements CommandExecutor, TabCompleter {

    private String name;
    private String permission;
    private String noPermissionMessage;

    public SmartCommand(String name, String permission, String noPermissionMessage) {
        this.name = name;
        this.permission = permission;
        this.noPermissionMessage = noPermissionMessage;
    }

    public SmartCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
        this.noPermissionMessage = "";
    }

    public SmartCommand(String name) {
        this.name = name;
        this.permission = "";
        this.noPermissionMessage = "";
    }

    public void handle(CommandSender sender, String[] args) {}

    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage(colorize(noPermissionMessage));
            return true;
        }

        handle(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            return Collections.emptyList();
        }

        return tabComplete(sender, args);
    }

    public void register(JavaPlugin plugin) {
        PluginCommand pluginCommand = plugin.getCommand(name);
        pluginCommand.setExecutor(this);
        pluginCommand.setTabCompleter(this);
    }

    private String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setNoPermissionMessage(String noPermissionMessage) {
        this.noPermissionMessage = noPermissionMessage;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String getNoPermissionMessage() {
        return noPermissionMessage;
    }

}
