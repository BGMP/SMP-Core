package com.braeh.listeners;

import com.braeh.core.SERVER;
import com.braeh.util.Permissions;
import com.braeh.util.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerListener
        implements Listener
{
    private SERVER plugin;

    public PlayerListener(SERVER plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void earlyPlayerJoin(PlayerLoginEvent event)
    {
        if ((SettingsManager.getInstance().getConfig().getBoolean("enabled")) &&
                (!event.getPlayer().hasPermission(new Permissions().bypass)) && (!this.plugin.AllowedPlayers.contains(event.getPlayer().getName())))
        {
            String kickmsg = SettingsManager.getInstance().getConfig().getString("KickMessage").replaceAll("&", "\u00A7").replaceAll("NEWLINE", "\n");
            event.disallow(PlayerLoginEvent.Result.KICK_OTHER, kickmsg);
            Player[] arrayOfPlayer;
            int j = (arrayOfPlayer = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0])).length;
            for (int i = 0; i < j; i++)
            {
                Player player = arrayOfPlayer[i];
                if ((player.hasPermission(new Permissions().bypass)) || (this.plugin.AllowedPlayers.contains(player.getName()))) {
                    player.sendMessage(ChatColor.AQUA + event.getPlayer().getName() + " Tried To Join The Server.");
                }
            }
        }
    }
}