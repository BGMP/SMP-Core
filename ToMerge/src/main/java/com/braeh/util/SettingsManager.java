package com.braeh.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager
{
    static SettingsManager instance = new SettingsManager();
    Plugin p;
    FileConfiguration config;
    File cfile;

    public static SettingsManager getInstance()
    {
        return instance;
    }

    public void setup(Plugin p)
    {
        this.config = p.getConfig();
        this.config.options().copyDefaults(true);
        this.cfile = new File(p.getDataFolder(), "config.yml");
        saveConfig();
    }

    public FileConfiguration getConfig()
    {
        return this.config;
    }

    public void saveConfig()
    {
        try
        {
            this.config.save(this.cfile);
        }
        catch (IOException e)
        {
            Bukkit.getServer().getLogger().severe(new Messages().ERRORCONFIG);
        }
    }

    public void reloadConfig()
    {
        this.config = YamlConfiguration.loadConfiguration(this.cfile);
    }

    public PluginDescriptionFile getDesc()
    {
        return this.p.getDescription();
    }
}