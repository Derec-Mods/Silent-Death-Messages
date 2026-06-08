package io.github.derexxd.silentDeathMessages.listeners;

import io.github.derexxd.silentDeathMessages.SilentDeathMessages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Set;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String uuid = player.getUniqueId().toString();

        Set<String> ignoredPlayers = SilentDeathMessages.getInstance().getStorage().getIgnoredPlayers();

        if (player.hasPermission("silentdeathmessages.silent") || ignoredPlayers.contains(uuid)) {
            event.setDeathMessage(null);
        }
    }
}