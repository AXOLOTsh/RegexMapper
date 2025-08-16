package io.github.axolotsh.regexMapper;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.axolotsh.regexMapper.commands.RegexMapperCommand;
import io.github.axolotsh.regexMapper.entities.RegexCase;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;

public final class Plugin extends JavaPlugin {
    public static final Logger LOGGER = LoggerFactory.getLogger("RegexMapper");
    @Override
    public void onEnable() {
        saveDefaultConfig();

        mapCases();

        registerCommands();

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

                    LOGGER.info(String.format("Loaded %s case:\nItem:%s\nRegex:%s\nModel:%s,\nCustom Model Data:%b", key, item, regex, model, customModelData));
                    RegexMapper.getInstance().addCase(new RegexCase(key, item, regex, model, customModelData));
                }
            }
        }
    }

    private void registerCommands() {
        /*var root = Commands.literal("regexmapper")
                .then(Commands.literal("reload")
                        .executes(x -> {
                    return Command.SINGLE_SUCCESS;
                }))

                .then(Commands.literal("list")
                        .executes(x -> {

                }))


                .then(Commands.literal("give"));*/

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(new RegexMapperCommand().getCommand().build());
        });
    }
}
