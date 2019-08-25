package me.prodigy4532.smpcore.listeners;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveEvent
        implements Listener
{
    @EventHandler(priority = EventPriority.HIGH)
    public void HideJoinMessage(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Bukkit.broadcastMessage(e.getPlayer().getDisplayName() + ChatColor.YELLOW + "" + ChatColor.BOLD + " joined the game!");
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void HideLeaveMessage(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Bukkit.broadcastMessage(e.getPlayer().getDisplayName() + ChatColor.RED + "" + ChatColor.BOLD + " left the game! ;C!");
    }
}