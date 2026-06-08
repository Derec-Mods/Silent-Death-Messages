package io.github.derexxd.silentDeathMessages.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonStorage {
    private final File file;
    private final Gson gson;
    private List<String> ignoredPlayers;

    public JsonStorage(JavaPlugin plugin) {
        this.file = new File(plugin.getDataFolder(), "ignored.json");
        this.gson = new Gson();
        this.ignoredPlayers = new ArrayList<>();
        load();
    }

    public void load() {
        if (!file.exists()) {
            save();
            return;
        }
        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<String>>(){}.getType();
            ignoredPlayers = gson.fromJson(reader, listType);
            if (ignoredPlayers == null) ignoredPlayers = new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        file.getParentFile().mkdirs();
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(ignoredPlayers, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getIgnoredPlayers() {
        return ignoredPlayers;
    }

    public void addPlayer(String uuid) {
        if (!ignoredPlayers.contains(uuid)) {
            ignoredPlayers.add(uuid);
            save();
        }
    }
}