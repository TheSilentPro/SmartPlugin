package tsp.smartplugin.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

/**
 * Utilities for serializing objects
 *
 * @author TheSilentPro
 */
public final class SerializationUtils {

    private SerializationUtils() {}

    /**
     * Serialize an {@link ItemStack} into a {@link JsonObject}
     *
     * @param item The item to serialize
     * @return Serialized item as a json object
     */
    public static JsonObject serializeItem(ItemStack item) {
        JsonObject main = new JsonObject();

        main.addProperty("material", item.getType().name());
        main.addProperty("amount", item.getAmount());
        main.addProperty("durability", item.getDurability());

        JsonObject enchantments = new JsonObject();
        for (Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
            NamespacedKey key = entry.getKey().getKey();
            enchantments.addProperty(key.getNamespace() + ":" + key.getKey(), entry.getValue());
        }
        main.add("enchantments", enchantments);

        ItemMeta meta = item.getItemMeta();
        JsonObject jsonMeta = new JsonObject();
        if (meta != null) {
            jsonMeta.addProperty("displayName", meta.hasDisplayName() ? meta.getDisplayName() : null);
            jsonMeta.addProperty("localizedName", meta.hasLocalizedName() ? meta.getLocalizedName() : null);
            jsonMeta.addProperty("customModelData", meta.getCustomModelData());

            JsonArray lore = new JsonArray();
            if (meta.hasLore()) {
                for (String line : meta.getLore()) {
                    lore.add(line);
                }
            }
            jsonMeta.add("lore", lore);

            JsonArray flags = new JsonArray();
            for (ItemFlag flag : meta.getItemFlags()) {
                flags.add(flag.name());
            }
            jsonMeta.add("flags", flags);

            JsonObject attributes = new JsonObject();
            if (meta.hasAttributeModifiers()) {
                for (Map.Entry<Attribute, AttributeModifier> entry : meta.getAttributeModifiers().entries()) {
                    JsonObject attribute = new JsonObject();
                    attribute.addProperty("name", entry.getValue().getName());
                    attribute.addProperty("amount", entry.getValue().getAmount());
                    attribute.addProperty("operation", entry.getValue().getOperation().name());
                    attribute.addProperty("slot", entry.getValue().getSlot() != null ? entry.getValue().getSlot().name() : null);
                    attribute.addProperty("uuid", entry.getValue().getUniqueId().toString());

                    attributes.add(entry.getKey().name(), attribute);
                }
            }
            jsonMeta.add("attributes", attributes);
        }

        main.add("meta", jsonMeta);
        return main;
    }

    public static ItemStack deserializeItem(JsonObject json) {
        ItemStack item = new ItemStack(Material.valueOf(json.get("material").getAsString()));
        item.setAmount(json.get("amount").getAsInt());
        item.setDurability(json.get("durability").getAsShort());

        JsonObject enchantments = json.get("enchantments").getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : enchantments.entrySet()) {
            item.addUnsafeEnchantment(Enchantment.getByKey(NamespacedKey.fromString(entry.getKey())), entry.getValue().getAsInt());
        }

        ItemMeta meta = item.getItemMeta();
        JsonObject jsonMeta = json.get("meta").getAsJsonObject();
        meta.setDisplayName(jsonMeta.get("displayName").getAsString());
        meta.setLocalizedName(jsonMeta.get("localizedName").getAsString());
        meta.setCustomModelData(jsonMeta.get("customModelData").getAsInt());

        JsonArray lore = jsonMeta.get("lore").getAsJsonArray();
        for (int i = 0; i < lore.size(); i++) {
            meta.getLore().add(lore.get(i).getAsString());
        }

        JsonArray flags = jsonMeta.get("flags").getAsJsonArray();
        for (int i = 0; i < flags.size(); i++) {
            meta.addItemFlags(ItemFlag.valueOf(flags.get(i).getAsString()));
        }

        JsonObject attributes = jsonMeta.get("attributes").getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : attributes.entrySet()) {
            JsonObject jsonAttribute = entry.getValue().getAsJsonObject();
            UUID uuid = UUID.fromString(jsonAttribute.get("uuid").getAsString());
            String name = jsonAttribute.get("name").getAsString();
            double amount = jsonAttribute.get("amount").getAsDouble();
            AttributeModifier.Operation operation = AttributeModifier.Operation.valueOf(jsonAttribute.get("operation").getAsString());
            EquipmentSlot slot = EquipmentSlot.valueOf(jsonAttribute.get("slot").getAsString());

            meta.addAttributeModifier( Attribute.valueOf(entry.getKey()), new AttributeModifier(uuid, name, amount, operation, slot));
        }

        item.setItemMeta(meta);
        return item;
    }

    // Location
    /**
     * Serialize location in a {@link FileConfiguration} under a specific key
     *
     * @param location The location to serialize
     * @param data The file configuration
     * @param key The key to serialize the location under
     * @see #deserializeLocation(FileConfiguration, String)
     */
    public static void serializeLocation(Location location, FileConfiguration data, @Nonnull String key) {
        if (!key.isEmpty()) {
            key = key + ".";
        }

        String uid = location.getWorld().getUID().toString();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        data.set(key + "world", uid);
        data.set(key + "x", x);
        data.set(key + "y", y);
        data.set(key + "z", z);
        data.set(key + "yaw", yaw);
        data.set(key + "pitch", pitch);
    }

    /**
     * Deserialize a location from a {@link FileConfiguration}
     *
     * @param data The file configuration
     * @param key The key that the location has been serialized under
     * @return Location
     * @see #serializeLocation(Location, FileConfiguration, String)
     */
    public static Location deserializeLocation(FileConfiguration data, @Nonnull String key) {
        if (!key.isEmpty()) {
            key = key + ".";
        }

        String uid = data.getString(key + "world");
        double x = data.getDouble(key + "x");
        double y = data.getDouble(key + "y");
        double z = data.getDouble(key + "z");
        float yaw = Float.parseFloat(data.getString(key + "yaw"));
        float pitch = Float.parseFloat(data.getString(key + "pitch")); // For some reason FileConfiguration doesn't have getFloat :/

        return new Location(Bukkit.getWorld(UUID.fromString(uid)), x, y, z, yaw, pitch);
    }

    /**
     * Serializes a location to a single string
     *
     * @param location The location to serialize
     * @return Serialized Location. Format: world;x;y;z;yaw;pitch
     * @see #deserializeLocation(String)
     */
    public static String serializeLocation(Location location) {
        String uid = location.getWorld().getUID().toString();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        return uid +
                ";" +
                x +
                ";" +
                y +
                ";" +
                z +
                ";" +
                yaw +
                ";" +
                pitch;
    }

    /**
     * Deserializes a location
     * that has been serailized with {@link #serializeLocation(Location)}
     *
     * @param serialized Location as a serialized string
     * @return Location
     * @see #serializeLocation(Location)
     */
    public static Location deserializeLocation(String serialized) {
        String[] args = serialized.split(";");
        String uid = args[0];
        double x = Double.parseDouble(args[1]);
        double y = Double.parseDouble(args[2]);
        double z = Double.parseDouble(args[3]);
        float yaw = Float.parseFloat(args[4]);
        float pitch = Float.parseFloat(args[5]);

        return new Location(Bukkit.getWorld(UUID.fromString(uid)), x, y, z, yaw, pitch);
    }

}
