package com.braeh.commands;

import com.braeh.core.SERVER;
import com.braeh.util.Messages;
import com.braeh.util.Permissions;
import com.braeh.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class toggledelay
        extends MaintenanceCommand
{
    int startCount;

    public toggledelay()
    {
        super("toggledelay", "Toggle the maintenance mode with delay.", "<time>", "td");
    }

    public void run(CommandSender sender, String[] args)
    {
        if ((sender.hasPermission(new Permissions().toggle)) || (sender.hasPermission(new Permissions().all)))
        {
            if (args.length != 1)
            {
                sender.sendMessage(ChatColor.DARK_RED + "Usage: /maintenance toggledelay (delay time in seconds)");
            }
            else if (SettingsManager.getInstance().getConfig().getBoolean("enabled"))
            {
                SettingsManager.getInstance().getConfig().set("enabled", Boolean.valueOf(false));
                Bukkit.getServer().broadcastMessage(SettingsManager.getInstance().getConfig().getString("BroadcastOnMaintenanceDisableMessage").replaceAll("&", "\u00A7"));
            }
            else
            {
                try
                {
                    this.startCount = Integer.parseInt(args[0]);
                }
                catch (NumberFormatException e1)
                {
                    sender.sendMessage("Needs To be a Number!");
                    return;
                }
                long Count = this.startCount * 20;
                final List<Integer> seconds = SettingsManager.getInstance().getConfig().getIntegerList("BroadcastTime");
                final int taskID = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SERVER.main, new Runnable()
                {
                    public void run()
                    {
                        String Seconds = String.valueOf(toggledelay.this.startCount);
                        if ((seconds.contains(Integer.valueOf(toggledelay.this.startCount))) && (toggledelay.this.startCount > 1) && (toggledelay.this.startCount < 60)) {
                            Bukkit.broadcastMessage(ChatColor.DARK_RED + new Messages().LINE);
                            Bukkit.broadcastMessage(SettingsManager.getInstance().getConfig().getString("BroadcastSecondsMessage").replaceAll("&", "\u00A7").replaceAll("%t", Seconds));
                            Bukkit.broadcastMessage(ChatColor.DARK_RED + new Messages().LINE);
                        } else if ((seconds.contains(Integer.valueOf(toggledelay.this.startCount))) && (toggledelay.this.startCount == 1)) {
                            Bukkit.broadcastMessage(ChatColor.DARK_RED + new Messages().LINE);
                            Bukkit.broadcastMessage(SettingsManager.getInstance().getConfig().getString("BroadcastSecondMessage").replaceAll("&", "\u00A7").replaceAll("%t", Seconds));
                            Bukkit.broadcastMessage(ChatColor.DARK_RED + new Messages().LINE);
                        } else if ((seconds.contains(Integer.valueOf(toggledelay.this.startCount))) && (toggledelay.this.startCount == 60)) {
                            Bukkit.broadcastMessage(ChatColor.DARK_RED + new Messages().LINE);
                            Bukkit.broadcastMessage(SettingsManager.getInstance().getConfig().getString("BroadcastMinuteMessage").replaceAll("&", "\u00A7").replaceAll("%t", "1"));
                            Bukkit.broadcastMessage(ChatColor.DARK_RED + new Messages().LINE);
                        }
                        toggledelay.this.startCount -= 1;
                    }
                }, 20L, 20L);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(SERVER.main, new Runnable()
                {
                    public void run()
                    {
                        Bukkit.getScheduler().cancelTask(taskID);
                        SettingsManager.getInstance().getConfig().set("enabled", Boolean.valueOf(true));
                        Bukkit.getServer().broadcastMessage(SettingsManager.getInstance().getConfig().getString("BroadcastOnMaintenanceEnableMessage").replaceAll("&", "\u00A7"));
                        Player[] arrayOfPlayer;
                        int j = (arrayOfPlayer = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0])).length;
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
                }, Count + 20L);
            }
        }
        else {
            sender.sendMessage(new Messages().NOPERM);
        }
    }
}
