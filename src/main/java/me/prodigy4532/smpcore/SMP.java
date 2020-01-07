package me.prodigy4532.smpcore;

import com.sk89q.bukkit.util.BukkitCommandsManager;
import com.sk89q.bukkit.util.CommandsManagerRegistration;
import com.sk89q.minecraft.util.commands.*;
import me.prodigy4532.smpcore.Chat.ChannelRegistry;
import me.prodigy4532.smpcore.Chat.PrivateMessage;
import me.prodigy4532.smpcore.Commands.*;
import me.prodigy4532.smpcore.Listeners.JoinLeaveEvent;
import me.prodigy4532.smpcore.Listeners.PlayerChatEvent;
import me.prodigy4532.smpcore.Utils.ChatConstant;
import me.prodigy4532.smpcore.Whitelist.WhitelistObject;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Objects;

public final class SMP extends JavaPlugin {
    public static SMP smp;
    private static WhitelistObject whitelist;

    private static Chat chat = null;
    private static ChannelRegistry channelRegistry;
    private static PrivateMessage.ReplyQueue replyQueue;

    private CommandsManager commands;
    private CommandsManagerRegistration commandRegistry;

    public static SMP get() {
        return smp;
    }

    public WhitelistObject getWhitelist() {
        return  whitelist;
    }

    public ChannelRegistry getChannelRegistry() {
        return channelRegistry;
    }

    public PrivateMessage.ReplyQueue getReplyQueue() {
        return replyQueue;
    }

    public Chat getChat() {
        return chat;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, @NotNull String[] args) {
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
        loadConfiguration();

        smp = this;
        
        whitelist = new WhitelistObject(
                getConfig().getBoolean("whitelist.enabled"),
                ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString("whitelist.message"))),
                getConfig().getStringList("whitelist.white-listed")
        );

        channelRegistry = new ChannelRegistry(new HashMap<>());
        replyQueue = new PrivateMessage.ReplyQueue(new HashMap<>());

        this.commands = new BukkitCommandsManager();
        this.commandRegistry = new CommandsManagerRegistration(this, this.commands);

        setupChat();
        if (!setupChat()) Bukkit.shutdown();

        registerEvents();
        registerCommands();

        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[!] " + ChatColor.YELLOW + "SMP-Core " + ChatColor.WHITE + ">> " + "v" + getDescription().getVersion() + " >> " + ChatColor.GREEN + "Enabled");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "[!] " + ChatColor.YELLOW + "SMP-Core " + ChatColor.WHITE + "<< " + "v" + getDescription().getVersion() + " << " + ChatColor.RED + "Disabled");
        Bukkit.getConsoleSender().sendMessage(ChatColor.WHITE + "-------------------------------");
    }

    private void registerCommands() {
        commandRegistry.register(SMPCommand.class);
        commandRegistry.register(SMPCommand.SMPParentCommand.class);
        commandRegistry.register(ChatCommand.class);
        commandRegistry.register(BroadcastCommand.class);
        commandRegistry.register(PrivateMessageCommand.class);
        commandRegistry.register(ReplyCommand.class);
    }

    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new JoinLeaveEvent(), this);
        pluginManager.registerEvents(new PlayerChatEvent(), this);
    }

    private void loadConfiguration() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> registeredServiceProvider = getServer().getServicesManager().getRegistration(Chat.class);
        assert registeredServiceProvider != null;
        chat = registeredServiceProvider.getProvider();
        return true;
    }
}
