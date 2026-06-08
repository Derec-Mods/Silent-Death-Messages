package io.github.derexxd.silentDeathMessages;

import org.bukkit.plugin.java.JavaPlugin;
import io.github.derexxd.silentDeathMessages.listeners.PlayerDeathListener;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
    }

    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        if (storage != null) {
            storage.saveSync();
        }
    }
}