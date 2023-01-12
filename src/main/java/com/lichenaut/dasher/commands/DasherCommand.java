package com.lichenaut.dasher.commands;

import com.lichenaut.dasher.Dasher;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

public class DasherCommand implements CommandExecutor {

    private final Dasher plugin;

    public DasherCommand(Dasher plugin) {this.plugin = plugin;}

    public void messageSender(CommandSender sender, String message) {
        if (sender instanceof Player) {sender.sendMessage(message);
        } else {plugin.getLog().info(ChatColor.stripColor(message));}
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        String helpMessage = "...";
        String invalidMessage = ChatColor.RED + "Invalid usage of '/jumper'. Use '...'";
        String onlyPlayerMessage = ChatColor.RED + "Command can only be used by a player!";
        StringBuilder arguments = new StringBuilder("jumper");
        if (args.length != 0) {for (String arg : args) {arguments.append(" ").append(arg);}}
        String fakeUnknown = ChatColor.RED + "Unknown or incomplete command, see below for error\n" + ChatColor.RED + ChatColor.UNDERLINE + arguments + ChatColor.RESET +
                ChatColor.RED + ChatColor.ITALIC + "<--[HERE]";

        if (sender instanceof Player && !sender.hasPermission("dasher.help") && !sender.hasPermission("dasher.dash") && !sender.hasPermission("dasher.reload")) {messageSender(sender, fakeUnknown);return false;}
        if (args.length == 0) {messageSender(sender, invalidMessage);return false;}
        switch (args[0]) {
            case "help":
                if (sender instanceof Player && !sender.hasPermission("dasher.help")) {messageSender(sender, fakeUnknown);return false;}
                messageSender(sender, helpMessage);break;
            case "dash":
                if (sender instanceof Player) {
                    if (!sender.hasPermission("dasher.dash")) {messageSender(sender, fakeUnknown);return false;}
                    Player p = (Player) sender;
                    Vector playerDirection = p.getLocation().getDirection();
                    double degrees = 180 / Math.PI;
                    double playerLook = -Math.atan2(playerDirection.getZ(), playerDirection.getX()) * degrees + 90;
                    if (playerLook < 0) {playerLook += 360;}
                    System.out.println("angle of player look: " + playerLook);
                    System.out.println("dash x: " + plugin.getVelocities('x').get(Integer.parseInt(args[1])));
                    System.out.println("dash z: " + plugin.getVelocities('z').get(Integer.parseInt(args[3])));
                    double dashAngle = 90 + Math.atan2(plugin.getVelocities('z').get(Integer.parseInt(args[3])), plugin.getVelocities('x').get(Integer.parseInt(args[1]))) * degrees;
                    System.out.println("angle of dash: " + dashAngle);
                    double velocityLength = Math.sqrt(Math.pow(plugin.getVelocities('z').get(Integer.parseInt(args[3])), 2) + plugin.getVelocities('x').get(Integer.parseInt(args[1])));
                    System.out.println("velocity hypotenuse: " + velocityLength);
                    double newDashAngle = playerLook - dashAngle + 90;
                    if (newDashAngle < 0) {newDashAngle += 360;}
                    System.out.println("new dash angle: " + newDashAngle);
                    double newZ = Math.cos(newDashAngle / degrees) * velocityLength;
                    double newX = Math.sin(newDashAngle / degrees) * velocityLength;
                    System.out.println("new dash z: " + newZ);
                    System.out.println("new dash x: " + newX);
                    p.setVelocity(new Vector(newX, plugin.getVelocities('y').get(Integer.parseInt(args[2])), newZ));
                } else {messageSender(sender, onlyPlayerMessage);}break;
            case "reload":
                if (sender instanceof Player) {
                    if (!sender.hasPermission("dasher.reload")) {messageSender(sender, fakeUnknown);return false;}
                    plugin.reloadConfig();
                } else {plugin.reloadConfig();}break;
            default:
                messageSender(sender, fakeUnknown);break;
        }
        return false;
    }
}