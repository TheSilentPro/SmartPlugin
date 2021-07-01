package tsp.smartplugin.server;

import org.bukkit.Bukkit;

/**
 * @author retrooper
 */
public enum ServerVersion {

    v_1_7_10((short) 5), v_1_8((short) 47), v_1_8_3((short) 47), v_1_8_4((short) 47), v_1_8_5((short) 47),
    v_1_8_6((short) 47), v_1_8_7((short) 47), v_1_8_8((short) 47), v_1_9((short) 107), v_1_9_2((short) 109),
    v_1_9_4((short) 110), v_1_10((short) 210), v_1_10_2((short) 210), v_1_11((short) 315), v_1_11_1((short) 316),
    v_1_11_2((short) 316), v_1_12((short) 335), v_1_12_1((short) 338), v_1_12_2((short) 340), v_1_13((short) 393),
    v_1_13_1((short) 401), v_1_13_2((short) 404), v_1_14((short) 477), v_1_14_1((short) 480), v_1_14_2((short) 485),
    v_1_14_3((short) 490), v_1_14_4((short) 498), v_1_15((short) 573), v_1_15_1((short) 575), v_1_15_2((short) 578),
    v_1_16((short) 735), v_1_16_1((short) 736), v_1_16_2((short) 751), v_1_16_3((short) 753), v_1_16_4((short) 754), v_1_17((short) 755), ERROR((short) -1);

    private static final String NMS_VERSION_SUFFIX = Bukkit.getServer().getClass().getPackage().getName()
            .replace(".", ",").split(",")[3];
    public static ServerVersion[] reversedValues = new ServerVersion[values().length];
    private static ServerVersion cachedVersion;
    private final short protocolId;

    ServerVersion(short protocolId) {
        this.protocolId = protocolId;
    }

    private static ServerVersion getVersionNoCache() {
        if (reversedValues[0] == null) {
            reversedValues = ServerVersion.reverse(values());
        }
        if (reversedValues == null) {
            throw new IllegalStateException("Failed to reverse the ServerVersion enum constant values.");
        }
        for (final ServerVersion val : reversedValues) {
            String valName = val.name().substring(2).replace("_", ".");
            if (Bukkit.getBukkitVersion().contains(valName)) {
                return val;
            }
        }

        return ERROR;
    }

    public static ServerVersion getVersion() {
        if (cachedVersion == null) {
            cachedVersion = getVersionNoCache();
        }
        return cachedVersion;
    }

    public static ServerVersion[] reverse(final ServerVersion[] arr) {
        ServerVersion[] array = arr.clone();
        if (array == null) {
            return null;
        }
        int i = 0;
        int j = array.length - 1;
        ServerVersion tmp;
        while (j > i) {
            tmp = array[j];
            array[j--] = array[i];
            array[i++] = tmp;
        }
        return array;
    }

    public static String getNmsSuffix() {
        return NMS_VERSION_SUFFIX;
    }

    public static String getNMSDirectory() {
        return "net.minecraft.server." + getNmsSuffix();
    }

    public static String getOBCDirectory() {
        return "org.bukkit.craftbukkit." + getNmsSuffix();
    }

    public short getProtocolVersion() {
        return protocolId;
    }

    /**
     * Returns if the current version is more up to date than the argument passed
     * version
     *
     * @param version The version to compare to the server's value.
     * @return True if the supplied version is lower, false if it is equal or higher
     * than the server's version.
     */
    public boolean isHigherThan(final ServerVersion version) {
        return protocolId > version.protocolId;
    }

    /**
     * Returns if the current version is more up to date than the argument passed
     * version
     *
     * @param version The version to compare to the server's value.
     * @return True if the supplied version is lower or equal, false if it is higher
     * than the server's version.
     */
    public boolean isHigherThanOrEquals(final ServerVersion version) {
        return protocolId >= version.protocolId;
    }

    /**
     * Returns if the current version is more older than the argument passed
     * version
     *
     * @param version The version to compare to the server's value.
     * @return True if the supplied version is higher, false if it is equal or lower
     * than the server's version.
     */
    public boolean isLowerThan(final ServerVersion version) {
        return version.protocolId > protocolId;
    }

    /**
     * Returns if the current version is more older than the argument passed
     * version
     *
     * @param version The version to compare to the server's value.
     * @return True if the supplied version is higher or equal, false if it is lower
     * than the server's version.
     */
    public boolean isLowerThanOrEquals(final ServerVersion version) {
        return version.protocolId >= protocolId;
    }
}