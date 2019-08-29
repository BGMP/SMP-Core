package com.braeh.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import com.braeh.util.Permissions;
import com.braeh.util.SettingsManager;

public class reload
        extends MaintenanceCommand
{
    String noperm = ChatColor.RED + "You do not have permission to perform this command!";

    public reload()
    {
        super("reload", "Reloads the configuration.", null, "r");
    }

    public void run(CommandSender sender, String[] args)
    {
        if ((sender.hasPermission(new Permissions().all)) || (sender.hasPermission(new Permissions().reloadconfig)))
        {
            SettingsManager.getInstance().reloadConfig();

            sender.sendMessage(ChatColor.GREEN + "Configuration Reloaded!");
            return;
        }
        sender.sendMessage(this.noperm);
    }
}