package tsp.smartplugin.logger;

/**
 * An abstract logger that can be implemented to fit any way of logging.
 *
 * @author TheSilentPro (Silent)
 */
@SuppressWarnings("unused")
public abstract class AbstractLogger {

    private final String name;
    private final boolean debug;

    public AbstractLogger(String name, boolean debug) {
        this.name = name;
        this.debug = debug;
    }

    public AbstractLogger(String name) {
        this(name, false);
    }

    public void info(String message) {
        this.log(LogLevel.INFO, message);
    }

    public void warning(String message) {
        this.log(LogLevel.WARNING, message);
    }

    public void error(String message) {
        this.log(LogLevel.ERROR, message);
    }

    public void debug(String message) {
        this.log(LogLevel.DEBUG, message);
    }

    public void trace(String message) {
        this.log(LogLevel.TRACE, message);
    }

    public abstract void log(LogLevel level, String message);

    public String getName() {
        return name;
    }

    public boolean isDebug() {
        return debug;
    }

    public enum LogLevel {

        INFO,
        WARNING,
        ERROR,
        DEBUG,
        TRACE;

        /**
         * Get the text color of the level
         *
         * @return The color
         */
        public String getColor() {
            switch (this) {
                case INFO:
                    return "\u001B[32m"; // Green
                case WARNING:
                    return "\u001B[33m"; // Yellow
                case ERROR:
                    return "\u001B[31m"; // Red
                case DEBUG:
                    return "\u001B[36m"; // Cyan
                case TRACE:
                    return "\u001b[35m"; // Purple
                default:
                    return "\u001B[37m"; // White
            }
        }

    }

}
