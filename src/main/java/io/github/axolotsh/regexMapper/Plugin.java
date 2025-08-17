package io.github.axolotsh.regexMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.axolotsh.regexMapper.commands.RegexMapperCommand;
import io.github.axolotsh.regexMapper.entities.RegexCase;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public final class Plugin extends JavaPlugin {
    public static final Logger LOGGER = LoggerFactory.getLogger("RegexMapper");
    @Override
    public void onEnable() {
        saveDefaultConfig();

        loadCases();

        registerCommands();

        getServer().getPluginManager().registerEvents(new EventListener(), this);
    }

    private void saveExampleCases() {
        var file = new File(getDataFolder(), "cases.json");
        if (file.exists())
            return;

        var gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter(file.getPath())) {
            gson.toJson(RegexCase.getExampleCases(), writer);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private void loadCases() {
        var file = new File(getDataFolder(), "cases.json");
        if (!file.exists())
            saveExampleCases();

        Gson gson = new Gson();
        var mapper = RegexMapper.getInstance();
        try (FileReader reader = new FileReader(file.getPath())) {
            List <RegexCase> cases = gson.fromJson(reader, new TypeToken<List<RegexCase>>() {}.getType());
            cases.forEach(mapper::addCase);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        /*var section = getConfig().getConfigurationSection("cases");
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

                    //LOGGER.info(String.format("Loaded %s case:\nItem:%s\nRegex:%s\nModel:%s,\nCustom Model Data:%b", key, item, regex, model, customModelData));
                    RegexMapper.getInstance().addCase(new RegexCase(key, item, regex, model, customModelData));
                }
            }
        }*/
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
