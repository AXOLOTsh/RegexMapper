package io.github.axolotsh.regexMapper.entities;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.Nullable;

public class RegexCase {
    private final String name;

    public String getName() {
        return name;
    }

    @Nullable
    private final String itemType;

    @Nullable
    public String getItemType() {
        return itemType;
    }

    private final String regexPattern;

    public String getRegexPattern() {
        return regexPattern;
    }

    private final String itemModel;

    public String getItemModel() {
        return itemModel;
    }

    private final boolean customModelData;

    public boolean isCustomModelData() {
        return customModelData;
    }

    public RegexCase(String name, @Nullable String itemType, String regexPattern, String itemModel, boolean customModelData) {
        this.name = name;
        this.itemType = itemType;
        this.regexPattern = regexPattern;
        this.itemModel = itemModel;
        this.customModelData = customModelData;
    }

    public Component getComponent() {
        return Component.text(getName()).appendNewline()
                .append(Component.text("Pattern: ")
                        .append(Component.text(getRegexPattern())
                                .color(NamedTextColor.GOLD))).appendNewline()
                .append(Component.text("TODO")).appendNewline()
                .append(Component.text("TODO"));
    }
}
