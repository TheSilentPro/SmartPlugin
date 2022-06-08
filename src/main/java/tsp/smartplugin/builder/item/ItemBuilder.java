package tsp.smartplugin.builder.item;

import com.google.common.collect.Multimap;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class for building an {@link ItemStack}
 *
 * @author TheSilentPro
 */
public class ItemBuilder {

    private final ItemStack item;
    private ItemMeta meta;
    private boolean colorize = true;

    /**
     * Creates a new ItemBuilder for the given {@link Material}
     *
     * @param material The material used for creating the new Object
     */
    public ItemBuilder(@Nonnull Material material) {
        Validate.notNull(material, "Material must not be null");

        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    /**
     * Creates a new ItemBuilder for the given {@link ItemStack}
     *
     * @param item The item used for creating the new Object
     */
    public ItemBuilder(@Nonnull ItemStack item) {
        Validate.notNull(item, "Item must not be null");

        this.item = item;
        this.meta = item.getItemMeta();
    }

    /**
     * Creates a new ItemBuilder for the given {@link ItemStack} and {@link ItemMeta}
     *
     * @param item The item used for creating the new Object
     * @param meta The ItemMeta used for creating the new Object
     */
    public ItemBuilder(@Nonnull ItemStack item, @Nonnull ItemMeta meta) {
        Validate.notNull(item, "Item must not be null");
        Validate.notNull(meta, "Meta must not be null");

        this.item = item;
        this.meta = meta;
    }

    /**
     * Creates a new ItemBuilder for the given {@link Item}
     *
     * @param item The item used for creating the new Object
     */
    public ItemBuilder(@Nonnull Item item) {
        Validate.notNull(item, "Item must not be null");

        this.item = item.getItemStack();
        this.meta = item.getItemStack().getItemMeta();
    }

    /**
     * Sets the {@link Material} of the item
     *
     * @param material The item material
     */
    public ItemBuilder material(Material material) {
        item.setType(material);
        return this;
    }

    /**
     * Sets the name of the item
     *
     * @param name Name
     */
    public ItemBuilder name(@Nonnull String name) {
        Validate.notNull(name, "Name must not be null");

        meta.setDisplayName(colorize(name));
        return this;
    }

    /**
     * Sets the amount of the item
     *
     * @param amount Amount
     */
    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /**
     * Adds an enchantment
     *
     * @param enchantment Enchantment to add
     * @param level Enchantment Level
     */
    public ItemBuilder enchant(@Nonnull Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        Validate.notNull(enchantment, "Enchantment must not be null");

        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    public ItemBuilder enchant(@Nonnull Enchantment enchantment, int level) {
        Validate.notNull(enchantment, "Enchantment must not be null");

        meta.addEnchant(enchantment, level, false);
        return this;
    }

    /**
     * Adds multiple enchantments at once
     *
     * @param enchantments The enchantments to add
     */
    public ItemBuilder enchant(@Nonnull Map<Enchantment, Integer> enchantments, boolean ignoreLevelRestriction) {
        Validate.notNull(enchantments, "Enchantments must not be null");

        for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet()) {
            meta.addEnchant(enchantment.getKey(), enchantment.getValue(), ignoreLevelRestriction);
        }
        return this;
    }

    /**
     * Removes an enchantment
     *
     * @param enchantment The enchantment to remove
     */
    public ItemBuilder disenchant(@Nonnull Enchantment enchantment) {
        Validate.notNull(enchantment, "Enchantment must not be null");

        meta.removeEnchant(enchantment);
        return this;
    }

    /**
     * Remove multiple enchantments
     *
     * @param enchantments Collection of enchantments to remove
     */
    public ItemBuilder disenchant(@Nonnull Collection<Enchantment> enchantments) {
        Validate.notNull(enchantments, "Enchantments must not be null!");

        for (Enchantment enchantment : enchantments) {
            meta.removeEnchant(enchantment);
        }
        return this;
    }

    /**
     * Adds the given string as a lore
     *
     * @param lore The string to add
     */
    public ItemBuilder addLore(@Nonnull String lore) {
        Validate.notNull(lore, "Lore must not be null");

        List<String> loreList = meta.getLore() != null ? meta.getLore() : new ArrayList<>();
        loreList.add(colorize(lore));
        meta.setLore(loreList);
        return this;
    }

    /**
     * Adds the given strings as a lore
     *
     * @param lore The strings to add
     */
    public ItemBuilder setLore(@Nonnull String... lore) {
        Validate.notNull(lore, "Lore must not be null");

        this.meta.setLore(
                Arrays.stream(lore)
                .map(this::colorize)
                .collect(Collectors.toList())
        );
        return this;
    }

    /**
     * Adds the given list as a lore
     *
     * @param lore The list to add
     */
    public ItemBuilder setLore(@Nonnull List<String> lore) {
        Validate.notNull(lore, "Lore must not be null");

        this.meta.setLore(
                lore.stream()
                .map(this::colorize)
                .collect(Collectors.toList())
        );
        return this;
    }

    /**
     * Removes a line from the lore based on the index
     *
     * @param index The line to remove
     */
    public ItemBuilder removeLore(int index) {
        if (meta.getLore() != null) {
            List<String> loreList = meta.getLore();
            loreList.remove(index);
            this.meta.setLore(loreList);
        }
        return this;
    }

    /**
     * Removes a line from the lore based on the line string
     *
     * @param line The line to remove
     */
    public ItemBuilder removeLore(@Nonnull String line) {
        Validate.notNull(line, "line must not be null");

        if (meta.getLore() != null) {
            List<String> loreList = this.meta.getLore();
            loreList.remove(line);
            this.meta.setLore(loreList);
        }
        return this;
    }

    /**
     * Adds an {@link ItemFlag}
     *
     * @param itemFlags The ItemFlag to add
     */
    public ItemBuilder addItemFlags(@Nonnull ItemFlag... itemFlags) {
        Validate.notNull(item, "ItemFlag must not be null");

        meta.addItemFlags(itemFlags);
        return this;
    }

    /**
     * Removes an ItemFlag
     *
     * @param itemFlags The ItemFlag to remove
     */
    public ItemBuilder removeItemFlags(@Nonnull ItemFlag... itemFlags) {
        Validate.notNull(itemFlags, "ItemFlag must not be null");

        meta.removeItemFlags(itemFlags);
        return this;
    }

    /**
     * Sets the material data
     *
     * @param materialData The material data to set
     */
    @Deprecated
    public ItemBuilder setMaterialData(@Nonnull MaterialData materialData) {
        Validate.notNull(materialData, "MaterialData must not be null");

        item.setData(materialData);
        return this;
    }

    /**
     * Set as unbreakable
     *
     * @param unbreakable Whether the item should be unbreakable
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Set the durability
     *
     * @param durability The amount of durability
     */
    public ItemBuilder setDurability(int durability) {
        if (!(meta instanceof Damageable)) return this;
        ((Damageable) meta).setDamage(durability);
        return this;
    }

    /**
     * Adds an {@link Attribute}
     *
     * @param attribute The attribute to add
     * @param modifier The attribute modifier
     */
    public ItemBuilder addAttributeModifier(@Nonnull Attribute attribute, @Nonnull AttributeModifier modifier) {
        Validate.notNull(attribute, "Attribute must not be null");
        Validate.notNull(modifier, "Modifier must not be null");

        meta.addAttributeModifier(attribute, modifier);
        return this;
    }

    /**
     * Adds multiple Attributes at once
     *
     * @param attributes The attributes to add
     */
    public ItemBuilder setAttributeModifiers(@Nonnull Multimap<Attribute, AttributeModifier> attributes) {
        Validate.notNull(attributes, "Attributes must not be null");

        meta.setAttributeModifiers(attributes);
        return this;
    }

    /**
     * Set glowing
     *
     * @param glow Whether the item should glow
     */
    public ItemBuilder setGlow(boolean glow, boolean hideEnchantment) {
        if (glow) {
            enchant(item.getType() != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK, 1, true);
        } else {
            disenchant(item.getType() != Material.BOW ? Enchantment.ARROW_INFINITE : Enchantment.LUCK);
        }

        if (hideEnchantment) {
            addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        return this;
    }

    public ItemBuilder setGlow(boolean glow) {
        setGlow(glow, true);
        return this;
    }

    /**
     * Set the owner as an {@link OfflinePlayer}
     *
     * @param owner The owner
     */
    public ItemBuilder setOwner(@Nonnull OfflinePlayer owner) {
        Validate.notNull(owner, "Owner must not be null");

        if (meta instanceof SkullMeta) {
            ((SkullMeta) meta).setOwningPlayer(owner);
        }
        return this;
    }

    /**
     * Set custom model data
     *
     * @param i Model data
     */
    public ItemBuilder setCustomModelData(int i) {
        meta.setCustomModelData(i);
        return this;
    }

    /**
     * Set item meta
     *
     * @param meta The item meta
     */
    public ItemBuilder setItemMeta(@Nonnull ItemMeta meta) {
        Validate.notNull(meta, "Meta must not be null");

        this.meta = meta;
        return this;
    }

    /**
     * Set persistent data
     *
     * @param key The namespace for this data
     * @param type The type of data
     * @param value The data value
     * @param <T> Primitive
     * @param <Z> Complex
     */
    public <T, Z> ItemBuilder set(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        getContainer().set(key, type, value);
        return this;
    }

    public BannerBuilder toBannerBuilder() {
        return new BannerBuilder(build());
    }

    public BookBuilder toBookBuilder() {
        return new BookBuilder(build());
    }

    public BundleBuilder toBundleBuilder() {
        return new BundleBuilder(build());
    }

    public CompassBuilder toCompassBuilder() {
        return new CompassBuilder(build());
    }

    public LeatherArmorBuilder toLeatherArmorBuilder() {
        return new LeatherArmorBuilder(build());
    }

    public PotionBuilder toPotionBuilder() {
        return new PotionBuilder(build());
    }

    /**
     * Build the item
     *
     * @return The finished item
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Retrieve the {@link PersistentDataContainer} of the item
     *
     * @return The container
     */
    public PersistentDataContainer getContainer() {
        return this.meta.getPersistentDataContainer();
    }

    /**
     * Set if strings should be colorized by the builder
     *
     * @param b Whether to colorize strings
     */
    public ItemBuilder colorize(boolean b) {
        colorize = b;
        return this;
    }

    /**
     * Colorize a string
     *
     * @param string The string to colorize
     * @return Colorized string
     */
    private String colorize(String string) {
        if (!colorize) return string;
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}