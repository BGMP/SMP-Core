package com.braeh.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import com.braeh.core.SERVER;
import com.braeh.util.Permissions;

public class playerAdderAndRemover
        extends MaintenanceCommand
{
    private String noperm = ChatColor.RED + "You do not have permission to perform this command!";

    public playerAdderAndRemover()
    {
        super("player", "Add a player to the allowed-players.txt file.", "add <player>", "p");
    }

    public void run(CommandSender sender, String[] args)
    {
        if (args.length == 2)
        {
            if ((args[0].equalsIgnoreCase("add")) || (args[0].equalsIgnoreCase("allow")))
            {
                if ((sender.hasPermission(new Permissions().playeralll)) || (sender.hasPermission(new Permissions().playeradd)) || (sender.hasPermission(new Permissions().all)))
                {
                    sender.sendMessage(ChatColor.YELLOW + "Sending request to allow " + args[1] + " to join the server...");
                    SERVER.main.AllowedPlayers.add(args[1]);
                    SERVER.main.logger.info(args[1] + " has been added to the allowed-players.txt list.");
                    sender.sendMessage(ChatColor.GREEN + args[1] + " has been added to the allowed-players.txt list.");
                    SERVER.main.AllowedPlayers.save();
                    return;
                }
                sender.sendMessage(this.noperm);
            }
            else if ((args[0].equalsIgnoreCase("remove")) || (args[0].equalsIgnoreCase("delete")))
            {
                if ((sender.hasPermission(new Permissions().playeralll)) || (sender.hasPermission(new Permissions().playerremove)) || (sender.hasPermission(new Permissions().all)))
                {
                    sender.sendMessage(ChatColor.YELLOW + "Sending request to remove " + args[1] + " from the allowed-players.txt file...");
                    SERVER.main.AllowedPlayers.remove(args[1]);
                    SERVER.main.logger.info(args[1] + " has been removed from the allowed-players.txt list.");
                    sender.sendMessage(ChatColor.GREEN + args[1] + " has been removed from the allowed-players.txt list.");
                    SERVER.main.AllowedPlayers.save();
                    return;
                }
                sender.sendMessage(this.noperm);
            }
            else
            {
                sender.sendMessage(ChatColor.AQUA + "============== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Server Maintenance Help " + ChatColor.AQUA + "==============");
                sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player add <player> " + ChatColor.DARK_AQUA + " Add a player to the allowed-players.txt file!");
                sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player remove <player> " + ChatColor.DARK_AQUA + "Removes a player from the allowed-palyers.txt file!");
                sender.sendMessage(ChatColor.AQUA + "=====================================================");
            }
        }
        else
        {
            sender.sendMessage(ChatColor.AQUA + "============== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Server Maintenance Help " + ChatColor.AQUA + "==============");
            sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player add <player> " + ChatColor.DARK_AQUA + " Add a player to the allowed-palyers.txt file!");
            sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player remove <player> " + ChatColor.DARK_AQUA + "Removes a player from the allowed-players.txt file!");
            sender.sendMessage(ChatColor.AQUA + "=====================================================");
        }
    }
}
