package com.lichenaut.dasher.triggers;

import com.lichenaut.dasher.Dasher;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class DCrouch implements Listener {

    private final Dasher plugin;

    public DCrouch(Dasher plugin) {this.plugin = plugin;}

    @EventHandler
    public void onPlayerCrouch (PlayerToggleSneakEvent e) {

    }
}
