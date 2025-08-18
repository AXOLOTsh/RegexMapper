package io.github.axolotsh.regexMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.axolotsh.regexMapper.commands.RegexMapperCommand;
import io.github.axolotsh.regexMapper.entities.RegexCase;
import io.github.axolotsh.regexMapper.utils.RegexCaseUtils;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
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
            gson.toJson(RegexCaseUtils.getExampleCases(), writer);
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
    }

    private void registerCommands() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(new RegexMapperCommand().getCommand().build());
        });
    }
}
