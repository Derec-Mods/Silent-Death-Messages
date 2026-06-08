package io.github.derexxd.silentDeathMessages.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class JsonStorage {
    private final JavaPlugin plugin;
    private final File file;
    private final Gson gson;
    private Set<String> ignoredPlayers;

    public JsonStorage(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "ignored.json");
        this.gson = new Gson();
        this.ignoredPlayers = new HashSet<>();
        load();
    }

    public void load() {
        if (!file.exists()) {
            saveSync();
            return;
        }
        try (Reader reader = new FileReader(file)) {
            Type setType = new TypeToken<HashSet<String>>(){}.getType();
            ignoredPlayers = gson.fromJson(reader, setType);

            if (ignoredPlayers == null) {
                ignoredPlayers = new HashSet<>();
            }
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to load ignored.json!");
            e.printStackTrace();
        }
    }

    public void saveSync() {
        file.getParentFile().mkdirs();
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(ignoredPlayers, writer);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save ignored.json!");
            e.printStackTrace();
        }
    }

    public void saveAsync() {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            Set<String> copyToSave = new HashSet<>(ignoredPlayers);

            file.getParentFile().mkdirs();
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(copyToSave, writer);
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to save ignored.json asynchronously!");
                e.printStackTrace();
            }
        });
    }

    public Set<String> getIgnoredPlayers() {
        return ignoredPlayers;
    }

    public void addPlayer(String uuid) {
        if (ignoredPlayers.add(uuid)) {
            saveAsync();
        }
    }
}