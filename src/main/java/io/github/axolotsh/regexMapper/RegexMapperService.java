package io.github.axolotsh.regexMapper;

import io.github.axolotsh.regexMapper.entities.RegexCase;
import io.papermc.paper.datacomponent.item.CustomModelData;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slf4j.Logger;

import java.util.*;
import java.util.List;

public class RegexMapperService {
    private static final RegexMapperService instance = new RegexMapperService();
    public static RegexMapperService getInstance() {
        return instance;
    }

    private final Logger LOGGER = RegexMapper.LOGGER;

    private final List<RegexCase> cases = new ArrayList<RegexCase>();
    private RegexMapperService() { }

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

        for (RegexCase regexCase : cases) {
            var currentItemType = item.getType().getKey().toString();
            var itemType = regexCase.getItemType();
            var pattern = regexCase.getRegexPattern();

            if (itemType == null || Objects.equals(itemType, currentItemType)) {
                if (itemName.matches(pattern)) {
                    var modelName = regexCase.getItemModel();
                    if (regexCase.isCustomModelData()) {
                        var customData = meta.getCustomModelDataComponent();

                        var strings = new ArrayList<String>();
                        strings.add(modelName);
                        customData.setStrings(strings);

                        meta.setCustomModelDataComponent(customData);
                    }
                    else  {
                        var model = NamespacedKey.fromString(modelName);

                        meta.setItemModel(model);
                    }

                    item.setItemMeta(meta);
                    return;
                }
            }
        }

        if (meta.hasItemModel())
            meta.setItemModel(null);
        if (meta.hasCustomModelDataComponent()) {
            var customData = meta.getCustomModelDataComponent();
            customData.setStrings(new ArrayList<>());
            meta.setCustomModelDataComponent(customData);
        }
        item.setItemMeta(meta);
    }
}
