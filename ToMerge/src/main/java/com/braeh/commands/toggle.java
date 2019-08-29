package com.braeh.commands;

import com.braeh.core.SERVER;
import com.braeh.util.Messages;
import com.braeh.util.Permissions;
import com.braeh.util.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class toggle
        extends MaintenanceCommand
{

    public toggle()
    {
        super("toggle", "Toggle the maintenance mode.", null, "t");
    }

    public void run(CommandSender sender, String[] args)
    {
        if ((sender.hasPermission(new Permissions().toggle)) || (sender.hasPermission(new Permissions().all)))
        {
            if (SettingsManager.getInstance().getConfig().getBoolean("enabled"))
            {
                SettingsManager.getInstance().getConfig().set("enabled", Boolean.valueOf(false));
                SERVER.main.getServer().broadcastMessage(SettingsManager.getInstance().getConfig().getString("BroadcastOnMaintenanceDisableMessage").replaceAll("&", "\u00A7"));
            }
            else
            {
                SettingsManager.getInstance().getConfig().set("enabled", Boolean.valueOf(true));
                SERVER.main.getServer().broadcastMessage(SettingsManager.getInstance().getConfig().getString("BroadcastOnMaintenanceEnableMessage").replaceAll("&", "\u00A7"));
                Player[] arrayOfPlayer;
                int j = (arrayOfPlayer = SERVER.main.getServer().getOnlinePlayers().toArray(new Player[0])).length;
                for (int i = 0; i < j; i++)
                {
                    Player player = arrayOfPlayer[i];
                    if ((player.hasPermission(new Permissions().bypass)) || (SERVER.main.AllowedPlayers.contains(player.getName())))
                    {
                        player.sendMessage("Only players with the perm bypass can join or players in the file allowed-players.txt");
                    }
                    else
                    {
                        String kickmsg = SettingsManager.getInstance().getConfig().getString("KickMessage").replaceAll("&", "\u00A7").replaceAll("NEWLINE", "\n");
                        player.kickPlayer(kickmsg);
                    }
                }
            }
            return;
        }
        sender.sendMessage(new Messages().NOPERM);
    }
}