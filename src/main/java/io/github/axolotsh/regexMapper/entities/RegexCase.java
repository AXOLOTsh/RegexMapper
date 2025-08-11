package io.github.axolotsh.regexMapper.entities;

import org.jetbrains.annotations.Nullable;

public class RegexCase {
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

    public RegexCase(@Nullable String itemType, String regexPattern, String itemModel, boolean customModelData) {
        this.itemType = itemType;
        this.regexPattern = regexPattern;
        this.itemModel = itemModel;
        this.customModelData = customModelData;
    }
}
