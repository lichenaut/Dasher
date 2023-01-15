package com.lichenaut.dasher.triggers;

import com.lichenaut.dasher.Dasher;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class DClick implements Listener {

    private final Dasher plugin;

    public DClick(Dasher plugin) {this.plugin = plugin;}

    @EventHandler
    public void onPlayerClick (PlayerInteractEvent e) {

    }
}
