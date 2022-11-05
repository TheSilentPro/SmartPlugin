package tsp.smartplugin.logger;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PluginLogger extends AbstractLogger {

    private final Logger logger;

    public PluginLogger(JavaPlugin plugin, boolean debug) {
        super(plugin.getName(), debug);
        this.logger = Logger.getLogger(plugin.getName());
    }

    public PluginLogger(JavaPlugin plugin) {
        this(plugin, false);
    }

    @Override
    public void log(LogLevel level, String message) {
        logger.log(mapToLevel(level), message);
    }

    public void log(LogLevel level, Throwable ex) {
        logger.log(mapToLevel(level), ex.getLocalizedMessage(), ex);
    }

    private Level mapToLevel(LogLevel logLevel) {
        return switch (logLevel) {
            case INFO -> Level.INFO;
            case WARNING -> Level.WARNING;
            case ERROR -> Level.SEVERE;
            case DEBUG -> Level.FINE;
            case TRACE -> Level.FINER;
        };
    }

}
