package tsp.smartplugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SmartCommand implements CommandExecutor {

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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!permission.isEmpty() && !sender.hasPermission(permission)) {
            sender.sendMessage(colorize(noPermissionMessage));
            return true;
        }

        handle(sender, args);
        return true;
    }

    public void register(JavaPlugin plugin) {
        PluginCommand pluginCommand = plugin.getCommand(name);
        pluginCommand.setExecutor(this);
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
