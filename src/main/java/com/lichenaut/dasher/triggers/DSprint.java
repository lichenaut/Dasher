package com.lichenaut.dasher.triggers;

import com.lichenaut.dasher.Dasher;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class DSprint implements Listener {

    private final Dasher plugin;

    public DSprint(Dasher plugin) {this.plugin = plugin;}

    @EventHandler
    public void onPlayerSprint (PlayerToggleSprintEvent e) {

    }
}
