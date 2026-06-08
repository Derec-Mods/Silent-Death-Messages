package io.github.derexxd.silentDeathMessages;

import io.github.derexxd.silentDeathMessages.storage.JsonStorage;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.derexxd.silentDeathMessages.commands.CommandManager;
import io.github.derexxd.silentDeathMessages.listeners.PlayerDeathListener;

public class SilentDeathMessages extends JavaPlugin {

    private static SilentDeathMessages instance;
    private JsonStorage storage;

    @Override
    public void onEnable() {
        instance = this;

        this.storage = new JsonStorage(this);

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);

        CommandManager cmdManager = new CommandManager();
        if (getCommand("sdm") != null) {
            getCommand("sdm").setExecutor(cmdManager);
            getCommand("sdm").setTabCompleter(cmdManager);
        } else {
            getLogger().severe("Failed to register 'sdm' command! Ensure it is in your plugin.yml.");
        }
    }

    public static SilentDeathMessages getInstance() {
        return instance;
    }

    public JsonStorage getStorage() {
        return storage;
    }

    @Override
    public void onDisable() {
        if (storage != null) {
            storage.saveSync();
        }
    }
}