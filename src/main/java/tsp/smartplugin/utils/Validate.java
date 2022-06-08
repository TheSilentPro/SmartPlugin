package tsp.smartplugin.utils;

/**
 * Validation checks.
 *
 * @author TheSilentPro (Silent)
 */
public final class Validate {

    private Validate() {}

    public static <T> void notNull(T object) {
        if (object == null) {
            throw new NullPointerException("Object can not be null!");
        }
    }

    public static <T> void notNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
    }

}
