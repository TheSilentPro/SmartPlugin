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
        switch (logLevel) {
            case INFO:
                return Level.INFO;
            case WARNING:
                return Level.WARNING;
            case ERROR:
                return Level.SEVERE;
            case DEBUG:
                return Level.FINE;
            case TRACE:
                return Level.FINER;
            default:
                return Level.ALL; // not possible
        }
    }

}
