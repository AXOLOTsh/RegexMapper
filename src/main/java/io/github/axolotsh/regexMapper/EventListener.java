package io.github.axolotsh.regexMapper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.slf4j.Logger;

public class EventListener implements Listener {
    private final Logger LOGGER = RegexMapper.LOGGER;
    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {
        RegexMapperService.getInstance().proceedItemRenameConditions(event);
    }
}
