package io.github.derexxd.silentDeathMessages.listeners;

import io.github.derexxd.silentDeathMessages.Main; // Import your main class
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String uuid = player.getUniqueId().toString();

        List<String> ignoredPlayers = Main.getInstance().getConfig().getStringList("ignored-players");

        if (player.hasPermission("silentdeathmessages.silent") || ignoredPlayers.contains(uuid)) {
            event.deathMessage(null);
        }
    }
}