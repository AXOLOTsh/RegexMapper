package io.github.axolotsh.regexMapper;

import com.google.gson.Gson;
import io.github.axolotsh.regexMapper.entities.RegexCase;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;
import org.slf4j.Logger;

import java.util.*;
import java.util.List;

public class RegexMapper {
    private static final RegexMapper instance = new RegexMapper();
    public static RegexMapper getInstance() {
        return instance;
    }

    private final Logger LOGGER = Plugin.LOGGER;

    private final List<RegexCase> cases = new ArrayList<RegexCase>();
    private RegexMapper() { }

    public void addCase(RegexCase regexCase) {
        cases.add(regexCase);
    }
    public void removeCase(RegexCase regexCase) {
        cases.remove(regexCase);
    }
    public void removeCase(int index) {
        cases.remove(index);
    }
    public List<RegexCase> getCases() {
        return Collections.unmodifiableList(cases);
    }

    public void proceedItemRenameConditions(InventoryClickEvent event) {
        if (event.getInventory().getType() != InventoryType.ANVIL)
            return;

        var anvilInventory = (AnvilInventory) event.getInventory();
        if (event.getRawSlot() != 2)
            return;

        var resultItem = anvilInventory.getItem(2);
        if (resultItem == null || !resultItem.hasItemMeta())
            return;

        var meta = resultItem.getItemMeta();
        if (!meta.hasDisplayName())
            return;

        mapRegex(resultItem);
    }

    public void mapRegex(ItemStack item) {
        var meta = item.getItemMeta();
        if (!meta.hasDisplayName())
            return;

        var displayName = ((TextComponent)meta.displayName());
        assert displayName != null;
        var itemName = displayName.content();

        var key = NamespacedKey.fromString("regexmapper:mapped");
        assert key != null;
        for (RegexCase regexCase : cases) {
            var currentItemType = item.getType().getKey().toString();
            var itemType = regexCase.getItem();
            var pattern = regexCase.getPattern();

            if (itemType == null || Objects.equals(itemType, currentItemType)) {
                if (itemName.matches(pattern)) {
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
                    item.setItemMeta(meta);
                    return;
                }
            }
        }
        var container = meta.getPersistentDataContainer();
        if (!container.has(key))
            return;

        var containerValue = container.get(key, PersistentDataType.STRING);
        var filter = cases.stream().filter(x -> Objects.equals(x.getName(), containerValue)).findFirst();
        if (filter.isEmpty())
            return;
        var value = filter.get();

        var model = value.getModel();
        if (model != null)
            meta.setItemModel(null);

        var customModelData = value.getCustomModelData();
        if (customModelData != null)
            meta.setCustomModelDataComponent(null);
        item.setItemMeta(meta);
    }
}

