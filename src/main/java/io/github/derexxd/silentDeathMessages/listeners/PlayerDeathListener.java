package io.github.derexxd.silentDeathMessages.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        if (player.hasPermission("silentdeathmessages.silent")) {
            event.deathMessage(null);
            event.setDeathMessage(null);
            // compat. idk. im thirsty i need waterrr
        }
    }
}