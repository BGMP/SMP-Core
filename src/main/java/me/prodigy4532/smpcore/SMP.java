package me.prodigy4532.smpcore;

import com.sk89q.bukkit.util.BukkitCommandsManager;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import me.prodigy4532.smpcore.Commands.SMPCommand;
import me.prodigy4532.smpcore.EventHandlers.JoinLeaveEvent;
import me.prodigy4532.smpcore.Utils.ChatConstant;
import me.prodigy4532.smpcore.Whitelist.WhitelistObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class SMP extends JavaPlugin {
    public static SMP getPlugin;
    public static WhitelistObject getWhitelist;
    public static LuckPermsApi luckPermsApi;

    private PluginDescriptionFile plugin = getDescription();

    private CommandsManager commands;
    private CommandsManagerRegistration commandRegistry;

    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            this.commands.execute(command.getName(), args, sender, sender);
        } catch (CommandPermissionsException exception) {
            sender.sendMessage(ChatConstant.NO_PERMISSION.formatAsException());
        } catch (MissingNestedCommandException exception) {
            sender.sendMessage(ChatColor.YELLOW + "⚠ " + ChatColor.RED + exception.getUsage());
        } catch (CommandUsageException exception) {
            sender.sendMessage(ChatColor.RED + exception.getMessage());
            sender.sendMessage(ChatColor.RED + exception.getUsage());
        } catch (WrappedCommandException exception) {
            if (exception.getCause() instanceof NumberFormatException) {
                sender.sendMessage(ChatConstant.NUMBER_STRING_EXCEPTION.formatAsException());
            } else {
                sender.sendMessage(ChatConstant.UNKNOWN_ERROR.formatAsException());
                exception.printStackTrace();
            }
        } catch (CommandException exception) {
            sender.sendMessage(ChatColor.RED + exception.getMessage());
        }
        return true;
    }

    @Override
    public void onEnable() {
        getPlugin = this;
        luckPermsApi = LuckPerms.getApi();
        loadConfiguration();

        getWhitelist = new WhitelistObject(
                getPlugin.getConfig().getBoolean("whitelist.enabled"),
                ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getPlugin.getConfig().getString("whitelist.message"))),
                getPlugin.getConfig().getStringList("whitelist.white-listed")
                );

        this.commands = new BukkitCommandsManager();
        this.commandRegistry = new CommandsManagerRegistration(this, this.commands);

        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[!] " + ChatColor.YELLOW + "SMP-Core " + ChatColor.WHITE + ">> " + "v" + plugin.getVersion() + " >> " + ChatColor.GREEN + "Enabled");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");

        registerEvents();
        registerCommands();
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[!] " + ChatColor.YELLOW + "SMP-Core " + ChatColor.WHITE + "<< " + "v" + plugin.getVersion() + " << " + ChatColor.RED + "Disabled");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
    }

    private void registerCommands() {
        commandRegistry.register(SMPCommand.class);
        commandRegistry.register(SMPCommand.SMPParentCommand.class);
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new JoinLeaveEvent(), this);
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
