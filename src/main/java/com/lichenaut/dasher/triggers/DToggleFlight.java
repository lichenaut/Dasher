package com.lichenaut.dasher.triggers;

import com.lichenaut.dasher.Dasher;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class DToggleFlight implements Listener {

    private final Dasher plugin;

    public DToggleFlight(Dasher plugin) {this.plugin = plugin;}

    @EventHandler
    public void onPlayerToggleFlight (PlayerToggleFlightEvent e) {

    }
}
