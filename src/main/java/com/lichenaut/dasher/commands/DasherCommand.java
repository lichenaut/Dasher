package com.lichenaut.dasher.commands;

import com.lichenaut.dasher.Dasher;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class DasherCommand implements CommandExecutor {

    private final Dasher plugin;

    public DasherCommand(Dasher plugin) {this.plugin = plugin;}

    public void messageSender(CommandSender sender, String message) {
        if (sender instanceof Player) {sender.sendMessage(message);
        } else {plugin.getLog().info(ChatColor.stripColor(message));}
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String helpMessage = "...";
        String invalidMessage = ChatColor.RED + "Invalid usage of '/jumper'. Use '...'";
        String onlyPlayerMessage = ChatColor.RED + "Command can only be used by a player!";
        StringBuilder arguments = new StringBuilder("jumper");
        if (args.length != 0) {for (String arg : args) {arguments.append(" ").append(arg);}}
        String fakeUnknown = ChatColor.RED + "Unknown or incomplete command, see below for error\n" + ChatColor.RED + ChatColor.UNDERLINE + arguments + ChatColor.RESET +
                ChatColor.RED + ChatColor.ITALIC + "<--[HERE]";

        if (sender instanceof Player && !sender.hasPermission("jumper.help") && !sender.hasPermission("jumper.jump")) {messageSender(sender, fakeUnknown);return false;}
        if (args.length == 0) {messageSender(sender, invalidMessage);return false;}
        if (args[0].equals("help")) {
            if (sender instanceof Player && !sender.hasPermission("jumper.help")) {messageSender(sender, fakeUnknown);return false;}
            messageSender(sender, helpMessage);
        } else if (args[0].equals("dash")) {
            if (sender instanceof Player) {
                if (!sender.hasPermission("jumper.jump")) {messageSender(sender, fakeUnknown);return false;}
                Player p = (Player) sender;
                Vector jumpVector = p.getVelocity();
                jumpVector.setX((plugin.getVelocities('x').get(Integer.parseInt(args[1])))*p.getLocation().getDirection().getX());
                jumpVector.setY(plugin.getVelocities('y').get(Integer.parseInt(args[2])));
                jumpVector.setZ((plugin.getVelocities('z').get(Integer.parseInt(args[3])))*p.getLocation().getDirection().getZ());
                p.setVelocity(jumpVector);
            } else {messageSender(sender, onlyPlayerMessage);return false;}
        } else {messageSender(sender, invalidMessage);}
        return false;
    }
}