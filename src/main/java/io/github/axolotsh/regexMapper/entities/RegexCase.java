package io.github.axolotsh.regexMapper.entities;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

public class RegexCase {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String value) { name = value; }
    public RegexCase name(String value) { setName(value); return  this; }

    @Nullable
    private String description;
    @Nullable
    public String getDescription() { return description; }
    public void setDescription(@Nullable String value) { description = value; }
    public RegexCase description(@Nullable String value) { setDescription(value); return this; }

    @Nullable
    private Material item;
    @Nullable
    public Material getItem() {
        return item;
    }
    public void setItem(@Nullable Material value) { item = value; }
    public RegexCase item(@Nullable Material value) { setItem(value); return this; }

    private String pattern;
    public String getPattern() { return pattern; }
    public void setPattern(String value) { pattern = pattern; }
    public RegexCase pattern(String value) { setPattern(value); return this; }

    @Nullable
    private Integer weight;
    public Integer getWeight() {
        if (weight == null)
            return 0;
        return weight;
    }
    public void setWeight(@Nullable Integer value) { weight = value; }
    public RegexCase weight(@Nullable Integer value) { setWeight(value); return this; }

    @Nullable
    private NamespacedKey model;
    @Nullable
    public NamespacedKey getModel() {
        return model;
    }
    public void setModel(@Nullable NamespacedKey value) { model = value; }
    public RegexCase model(@Nullable NamespacedKey value) { setModel(value); return this; }

    @Nullable
    private CustomModelData customModelData;
    @Nullable
    public CustomModelData getCustomModelData() { return customModelData; }
    public void setCustomModelData(@Nullable CustomModelData value) { customModelData = value; }
    public RegexCase customModelData(@Nullable CustomModelData value) { setCustomModelData(value); return this; }

    public RegexCase() { }
    public RegexCase(String name, String pattern) {
        this.name = name;
        this.pattern = pattern;
    }
}
