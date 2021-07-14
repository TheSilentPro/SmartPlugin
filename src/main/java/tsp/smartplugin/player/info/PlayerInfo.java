package tsp.smartplugin.player.info;

import java.util.UUID;

public class PlayerInfo {

    private final UUID uuid;
    private final SkinInfo skinInfo;
    private final NameHistory nameHistory;

    public PlayerInfo(UUID uuid, SkinInfo skinInfo, NameHistory nameHistory) {
        this.uuid = uuid;
        this.skinInfo = skinInfo;
        this.nameHistory = nameHistory;
    }

    public NameHistory getNameHistory() {
        return nameHistory;
    }

    public SkinInfo getSkinInfo() {
        return skinInfo;
    }

    public UUID getUniqueId() {
        return uuid;
    }

}
