package tsp.smartplugin.player;

public class SkinInfo {

    private final String id;
    private final String name;
    private final String value;
    private final String signature;

    public SkinInfo(String id, String name, String value, String signature) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.signature = signature;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getSignature() {
        return signature;
    }

}
