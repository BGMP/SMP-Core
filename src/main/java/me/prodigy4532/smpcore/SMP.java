package me.prodigy4532.smpcore;

import me.prodigy4532.smpcore.listeners.JoinLeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMP
        extends JavaPlugin
{

    @Override
    public void onEnable() {
        PluginDescriptionFile plugin = getDescription();

        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[!] " + ChatColor.YELLOW + "SMP-Core " + ChatColor.WHITE + ">> " + "v" + plugin.getVersion() + " >> " + ChatColor.GREEN + "Enabled");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");

        this.getServer().getPluginManager().registerEvents(new JoinLeaveEvent(), this);
    }

    @Override
    public void onDisable() {
        PluginDescriptionFile plugin = getDescription();

        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[!] " + ChatColor.YELLOW + "SMP-Core " + ChatColor.WHITE + "<< " + "v" + plugin.getVersion() + " << " + ChatColor.RED + "Disabled");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
    }
}
