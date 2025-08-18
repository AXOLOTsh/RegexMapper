package io.github.axolotsh.regexMapper.utils;

import com.google.gson.GsonBuilder;
import io.github.axolotsh.regexMapper.entities.CustomModelData;
import io.github.axolotsh.regexMapper.entities.RegexCase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class RegexCaseUtils {
    private final RegexCase regexCase;
    private final NamespacedKey key;

    public RegexCaseUtils(RegexCase regexCase) {
        this.regexCase = regexCase;
        this.key = NamespacedKey.fromString("regexmapper:mapped");
    }

    public ItemMeta modifyMeta(ItemMeta meta) {
        var model = regexCase.getModel();
        if (model != null)
            meta.setItemModel(model);

        var customModelData = regexCase.getCustomModelData();
        if (customModelData != null) {
            var cmd = meta.getCustomModelDataComponent();
            cmd.setFloats(customModelData.getFloats());
            cmd.setFlags(customModelData.getFlags());
            cmd.setStrings(customModelData.getStrings());
            cmd.setColors(customModelData.getColors());

            meta.setCustomModelDataComponent(cmd);
        }

        var container = meta.getPersistentDataContainer();
        container.set(key, PersistentDataType.STRING, regexCase.getName());
        return meta;
    }

    public ItemMeta clearMeta(ItemMeta meta) {
        var model = regexCase.getModel();
        if (model != null)
            meta.setItemModel(null);

        var customModelData = regexCase.getCustomModelData();
        if (customModelData != null)
            meta.setCustomModelDataComponent(null);
        return meta;
    }

    public Component getComponent() {
        var output = "";
        output += getNameComponent();
        output += appendLine(getDescriptionComponent());
        output += "\n";

        output += appendLine(getItemComponent());
        output += appendLine(getPatternComponent());

        return MiniMessage.miniMessage().deserialize(output);
    }
    public Component getOpComponent() {
        var output = "";
        output += getOpNameComponent();
        output += appendLine(getDescriptionComponent());
        output += "\n";

        output += appendLine(getItemComponent());
        output += appendLine(getPatternComponent());
        output += appendLine(getModelComponent());
        output += appendLine(getCustomModelDataComponent());

        return MiniMessage.miniMessage().deserialize(output);
    }
    private String appendLine(String value) {
        if (value == null || value.isEmpty())
            return "";
        return  "\n" + value;
    }

    private String getNameComponent() {
        var name = regexCase.getName();
        return String.format("<aqua>%s</aqua>", name);
    }
    private String getOpNameComponent() {
        var name = regexCase.getName();
        return String.format("<click:suggest_command:'/regmap give %s'><u>%s</u></click>",name, getNameComponent());
    }
    private String getDescriptionComponent() {
        var description = regexCase.getDescription();
        if (description == null)
            return "";
        return String.format("<i><gray>%s</gray></i>", description);
    }

    private String getItemComponent() {
        var item = regexCase.getItem();
        if (item == null)
            return "";
        return getPropertyComponent("Item", item.name());
    }

    private String getPatternComponent() {
        return getPropertyComponent("Pattern", regexCase.getPattern());
    }

    private String getModelComponent() {
        var model = regexCase.getModel();
        if (model == null)
            return "";
        return getPropertyComponent("Model", model.asString());
    }

    private String getCustomModelDataComponent() {
        var customModelData = regexCase.getCustomModelData();
        if (customModelData == null)
            return "";
        var gson = new GsonBuilder()
                .setPrettyPrinting().create();
        var cmdString = gson.toJson(customModelData);

        return getPropertyComponent("CustomModelData", String.format("<u><hover:show_text:'%s'>{...}</hover></u>", cmdString));
        //return String.format("<b>Custom Model Data:</b> <hover:show_text:'%s'><u><i><gray>{...}</gray></i></u></hover>", cmdString);
    }
    private String getPropertyComponent(String name, String value) {
        return String.format("%s <blue>:</blue> <yellow>%s</yellow>", name, value);
    }

    public static java.util.List<RegexCase> getExampleCases() {
        List<RegexCase> cases = new ArrayList<>();

        cases.add(new RegexCase("Model Example", "(?i)example regex.*")
                .description("This example will change the <b>Model</b> of the <b>iron ingot</b> when matched against a pattern.\nExternal models will require a resource pack.")
                .item(Material.IRON_INGOT)
                .model(NamespacedKey.fromString("minecraft:torch")));

        cases.add(new RegexCase("CustomModelData Example", "(?i)example regex.*")
                .description("This example will change the <b>Custom Model Data</b> of <b>any item</b> when matched against a pattern.\nThe model is changed using a resource pack.")
                .customModelData(new CustomModelData().addString("example key")));

        return cases;
    }
}
