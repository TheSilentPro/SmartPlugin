package tsp.smartplugin.player.info;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class NameHistory {

    private final UUID uuid;
    private final Map<String, Long> history;

    public NameHistory(UUID uuid, Map<String, Long> history) {
        this.uuid = uuid;
        this.history = history;
    }

    public Date getFormatted(String name) {
        long timestamp = history.get(name);
        return new Date(timestamp);
    }

    public Map<String, Long> getHistoryMap() {
        return history;
    }

    public UUID getUniqueId() {
        return uuid;
    }

}
