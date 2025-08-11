package io.github.axolotsh.regexMapper;

import io.github.axolotsh.regexMapper.entities.RegexCase;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public final class RegexMapper extends JavaPlugin {
    public static final Logger LOGGER = LoggerFactory.getLogger("RegexMapper");
    @Override
    public void onEnable() {
        saveDefaultConfig();

        mapCases();

        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    private void mapCases() {
        var section = getConfig().getConfigurationSection("cases");
        if (section != null) {
            Set<String> keys = section.getKeys(false);

            for (String key : keys) {
                var value = section.getConfigurationSection(key);
                if (value != null) {
                    var item = value.getString("item");

                    var regex = value.getString("regex");
                    var model = value.getString("model");

                    var customModelData = value.getBoolean("custom_model_data");
                    if (regex == null || model == null) {
                        LOGGER.error(String.format("Can not load %s case!", key));
                        continue;
                    }

                    LOGGER.info(String.format("Loaded %s case:\nItem:%s\nRegex:%s\nModel:%s,\nCustom Model Data:%b", item, key, regex, model, customModelData));
                    RegexMapperService.getInstance().addCase(new RegexCase(item, regex, model, customModelData));
                }
            }
        }
    }
}
