package tsp.smartplugin.data;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.annotation.Nonnull;

public final class PersistentJsonDataType<T extends JsonElement> implements PersistentDataType<String, T> {

    public static final PersistentJsonDataType<JsonObject> JSON_OBJECT = new PersistentJsonDataType<>(JsonObject.class);
    public static final PersistentJsonDataType<JsonArray> JSON_ARRAY = new PersistentJsonDataType<>(JsonArray.class);

    private final Class<T> jsonClass;

    public PersistentJsonDataType(@Nonnull Class<T> jsonClass) {
        this.jsonClass = jsonClass;
    }

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public Class<T> getComplexType() {
        return jsonClass;
    }

    @Override
    public String toPrimitive(JsonElement complex, PersistentDataAdapterContext context) {
        return complex.toString();
    }

    @Override
    public T fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        JsonElement json = JsonParser.parseString(primitive);

        if (jsonClass.isInstance(json)) {
            return jsonClass.cast(json);
        } else {
            return null;
        }
    }

}
