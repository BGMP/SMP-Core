package me.prodigy4532.smpcore.EventHandlers;

import me.prodigy4532.smpcore.SMP;
import me.prodigy4532.smpcore.Whitelist.WhitelistObject;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class JoinLeaveEvent implements Listener {

    private ConfigurationSection messages = SMP.getPlugin.getConfig().getConfigurationSection("messages");

    private String parseJoinMessage(ConfigurationSection section, Player player) {
        String raw = section.getString("join");
        assert raw != null;
        return ChatColor.translateAlternateColorCodes('&', raw.replaceAll("%player%", player.getDisplayName()));
    }

    private String parseLeaveMessage(ConfigurationSection section, Player player) {
        String raw = section.getString("leave");
        assert raw != null;
        return ChatColor.translateAlternateColorCodes('&', raw.replaceAll("%player%", player.getDisplayName()));
    }

    // TODO: Fix handleDisconnection() called twice
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        WhitelistObject whitelist = SMP.getWhitelist;
        if (!whitelist.isEnabled() || whitelist.getPlayers().contains(player.getUniqueId().toString())) {
            player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', SMP.getChat().getPlayerPrefix(player) + player.getDisplayName()));
            event.setJoinMessage(parseJoinMessage(Objects.requireNonNull(messages), player));
        } else {
            event.setJoinMessage(null);
            player.kickPlayer(whitelist.getMessage());
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLeave (PlayerQuitEvent event){
        event.setQuitMessage(parseLeaveMessage(Objects.requireNonNull(messages), event.getPlayer()));
    }
}
