package com.braeh.core;

import com.braeh.commands.StaffListCMD;
import com.braeh.listeners.BCListener;
import com.braeh.listeners.MOTDListener;
import com.braeh.listeners.PlayerListener;
import com.braeh.util.ListStore;
import com.braeh.util.Permissions;
import com.braeh.util.SettingsManager;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

public class SERVER
        extends JavaPlugin
{
    public static SERVER main;
    SettingsManager settings = SettingsManager.getInstance();
    public final Logger logger = Logger.getLogger("Minecraft");
    public ListStore AllowedPlayers;
    protected Logger log;
    public boolean versionDiff = false;
    public static boolean update = false;
    public static int count = 0;
    private CommandsManager<CommandSender> commands;
    private BCListener bclistener;
    public long Interval = 10L;

    public void onEnable()
    {
        main = this;
        this.settings.setup(this);
        this.log = getLogger();
        loadAllowedPlayers();
        PluginManager pm = getServer().getPluginManager();
        getCommand("maintenance").setExecutor(new CommandManager());
        getCommand("servermaintenance").setExecutor(new CommandManager());
        getCommand("m").setExecutor(new CommandManager());
        getCommand("sm").setExecutor(new CommandManager());
        getCommand("ma").setExecutor(new CommandManager());
        pm.registerEvents(new PlayerListener(this), this);
        pm.registerEvents(new MOTDListener(), this);
        pm.addPermission(new Permissions().all);
        pm.addPermission(new Permissions().bypass);
        pm.addPermission(new Permissions().playeradd);
        pm.addPermission(new Permissions().playerremove);
        pm.addPermission(new Permissions().playerlist);
        pm.addPermission(new Permissions().playeralll);
        pm.addPermission(new Permissions().toggle);
        pm.addPermission(new Permissions().update);
        pm.addPermission(new Permissions().stop);
        PluginDescriptionFile plugin = getDescription();
        this.Interval = getConfig().getLong("AB.Interval");
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, this.bclistener, this.Interval * 20L, this.Interval * 20L);


        Bukkit.getConsoleSender().sendMessage("\u00A7e-=-=-=-=-=-=-=-=-=-=-=-=-");
        Bukkit.getConsoleSender().sendMessage("\u00A7aSERVER has been enabled!");
        Bukkit.getConsoleSender().sendMessage("\u00A73Version: " + plugin.getVersion());
        Bukkit.getConsoleSender().sendMessage("\u00A7e-=-=-=-=-=-=-=-=-=-=-=-=-");

        setupCommands();
    }

    public void onDisable()
    {
        saveConfig();
        PluginDescriptionFile plugin = getDescription();

        Bukkit.getConsoleSender().sendMessage("\u00A7e-=-=-=-=-=-=-=-=-=-=-=-=-");
        Bukkit.getConsoleSender().sendMessage("\u00A7aSERVER has been disabled!");
        Bukkit.getConsoleSender().sendMessage("\u00A73Version: " + plugin.getVersion());
        Bukkit.getConsoleSender().sendMessage("\u00A7e-=-=-=-=-=-=-=-=-=-=-=-=-");

        PluginManager pm = getServer().getPluginManager();
        pm.removePermission(new Permissions().all);
        pm.removePermission(new Permissions().bypass);
        pm.removePermission(new Permissions().playeradd);
        pm.removePermission(new Permissions().playerremove);
        pm.removePermission(new Permissions().playerlist);
        pm.removePermission(new Permissions().playeralll);
        pm.removePermission(new Permissions().toggle);
        pm.removePermission(new Permissions().update);
        pm.removePermission(new Permissions().stop);
    }

    private void loadAllowedPlayers()
    {
        String pluginFolder = getDataFolder().getAbsolutePath();
        new File(pluginFolder).mkdirs();
        this.AllowedPlayers = new ListStore(new File(pluginFolder + File.separator + "allowed-players.txt"));
        this.AllowedPlayers.load();
    }

    public SERVER()
    {
        this.bclistener = new BCListener(this);
    }

    public static String Color(String s)
    {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public List<String> message()
    {
        List<String> messages = getConfig().getStringList("AB.Mensajes");
        return messages;
    }

    public void bc(String mainmsg)
    {
        String bcmsg = "\u0026" + getConfig().getString("SCAB.TextColor") + mainmsg;
        Bukkit.broadcastMessage(Color(SettingsManager.getInstance().getConfig().getString("BROADCAST.STRING")));
        Bukkit.broadcastMessage(bcmsg.replaceAll("\u0026", "\u00A7"));
        Bukkit.broadcastMessage(Color(SettingsManager.getInstance().getConfig().getString("BROADCAST.STRING")));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        try {
            this.commands.execute(cmd.getName(), args, sender, sender);
        } catch (CommandPermissionsException e) {
            sender.sendMessage(ChatColor.RED + "You don't have permission.");
        } catch (MissingNestedCommandException e) {
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (CommandUsageException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
            sender.sendMessage(ChatColor.RED + e.getUsage());
        } catch (WrappedCommandException e) {
            if (e.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatColor.RED + "Number expected, string received instead.");
            } else {
                sender.sendMessage(ChatColor.RED + "An error has occurred. See console.");
                e.printStackTrace();
            }
        } catch (CommandException e) {
            sender.sendMessage(ChatColor.RED + e.getMessage());
        }

        return true;
    }

    private void setupCommands() {
        this.commands = new CommandsManager<CommandSender>() {
            @Override
            public boolean hasPermission(CommandSender sender, String perm) {
                return sender instanceof ConsoleCommandSender || sender.hasPermission(perm);
            }
        };
        CommandsManagerRegistration cmdRegister = new CommandsManagerRegistration(this, this.commands);
        cmdRegister.register(StaffListCMD.class);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerClickTab(PlayerChatTabCompleteEvent e)
    {
        e.getTabCompletions().clear();
        e.getChatMessage().charAt(0);
    }
}