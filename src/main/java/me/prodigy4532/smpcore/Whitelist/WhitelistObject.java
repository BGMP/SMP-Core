package me.prodigy4532.smpcore.Whitelist;

import me.prodigy4532.smpcore.SMP;
import me.prodigy4532.smpcore.Utils.ChatConstant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.List;

public class WhitelistObject {
    private boolean isEnabled;
    private String message;
    private List<String> players;

    public WhitelistObject(boolean enabled, String message, List<String> players) {
        this.isEnabled = enabled;
        this.message = message;
        this.players = players;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public void addPlayer(CommandSender sender, OfflinePlayer player) {
        if (!players.contains(player.getUniqueId().toString())) {
            players.add(player.getUniqueId().toString());
            List<String> newWhitelisted = SMP.get().getConfig().getStringList("whitelist.white-listed");
            newWhitelisted.add(player.getUniqueId().toString());

            SMP.get().getConfig().set("whitelist.white-listed", newWhitelisted);
            SMP.get().saveConfig();

            sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.DARK_AQUA + player.getName() + ChatColor.GREEN + " to the whitelist!");
        } else sender.sendMessage(ChatConstant.ALREADY_IN_WHITELIST.formatAsException());
    }

    public void removePlayer(CommandSender sender, OfflinePlayer player) {
        if (players.contains(player.getUniqueId().toString())) {
            players.remove(player.getUniqueId().toString());
            List<String> newWhitelisted = SMP.get().getConfig().getStringList("whitelist.white-listed");
            newWhitelisted.remove(player.getUniqueId().toString());

            SMP.get().getConfig().set("whitelist.white-listed", newWhitelisted);
            SMP.get().saveConfig();

            sender.sendMessage(ChatColor.RED + "Removed " + ChatColor.DARK_AQUA + player.getName() + ChatColor.RED + " from the whitelist!");
        } else sender.sendMessage(ChatConstant.NOT_WHITELISTED.formatAsException());
    }

    public void enable() {
        isEnabled = true;
        SMP.get().getConfig().set("whitelist.enabled", true);
        SMP.get().saveConfig();
    }

    public void disable() {
        isEnabled = false;
        SMP.get().getConfig().set("whitelist.enabled", false);
        SMP.get().saveConfig();
    }

    public void kick() {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (!players.contains(onlinePlayer.getUniqueId().toString())) {
                onlinePlayer.kickPlayer(message);
            }
        });
    }
}
