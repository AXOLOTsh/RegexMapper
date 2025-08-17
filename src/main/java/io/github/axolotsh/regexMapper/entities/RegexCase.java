package io.github.axolotsh.regexMapper.entities;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public Component getComponent() {
        var output = Component.text(getName())
                .color(NamedTextColor.AQUA)
                .decorate(TextDecoration.BOLD).appendNewline();

        var description = getDescription();
        if (description != null) {
            output = output.appendNewline()
                    .append(Component.text(description)
                            .color(NamedTextColor.GRAY)
                    .appendNewline());
        }

        output = output.appendNewline()
                .append(Component.text("Pattern: ")
                        .decorate(TextDecoration.BOLD))

                .append(Component.text(getPattern())
                        .color(NamedTextColor.GRAY));

        var model = getModel();
        if (model != null)  {
            output = output.appendNewline()
                    .append(Component.text("Model: ")
                            .decorate(TextDecoration.BOLD))
                    .append(Component.text(model.asString())
                            .color(NamedTextColor.GRAY));
        }

        var customModelData = getCustomModelData();
        if (customModelData != null) {
            var gson = new Gson();

            output = output.appendNewline()
                    .append(Component.text("CustomModelData: ")
                            .decorate(TextDecoration.BOLD))

                            .append(Component.text("{ ... }")
                                    .color(NamedTextColor.GRAY)
                                    .decorate(TextDecoration.UNDERLINED)
                                    .hoverEvent(HoverEvent.showText(
                                            Component.text(gson.toJson(customModelData)))));
        }

        return output;
    }

    public static List<RegexCase> getExampleCases() {
        List<RegexCase> cases = new ArrayList<>();

        cases.add(new RegexCase("Model Example", "(?i)example regex.*")
                .description("This example will change model of iron ingot item when you match pattern to torch. External models will require a resource pack.")
                .model(NamespacedKey.fromString("minecraft:torch")));

        cases.add(new RegexCase("CustomModelData Example", "(?i)example regex.*")
                .description("This example will change custom model data of any item when you match pattern. Model changes by resource pack.")
                .customModelData(new CustomModelData().addString("example key")));

        return cases;
    }
}
