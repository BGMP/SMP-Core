package me.prodigy4532.smpcore.Commands;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import com.sk89q.minecraft.util.commands.NestedCommand;
import me.prodigy4532.smpcore.SMP;
import me.prodigy4532.smpcore.Utils.ChatConstant;
import me.prodigy4532.smpcore.Whitelist.WhitelistObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

public class SMPCommand {
    @Command(
            aliases = {"add"},
            desc = "Adds a player to the SMP.",
            usage = "[player]",
            min = 1,
            max = 1
    )
    @CommandPermissions("smp.core.add")
    @SuppressWarnings("deprecation")
    public static void add(final CommandContext args, final CommandSender sender) throws CommandException {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.getString(0));
        SMP.getWhitelist.addPlayer(sender, offlinePlayer);
        sender.sendMessage(ChatColor.GREEN + "Added " + ChatColor.DARK_AQUA + offlinePlayer.getName() + ChatColor.GREEN + " to the whitelist!");
    }

    @Command(
            aliases = {"remove"},
            desc = "Removes a player from the SMP.",
            usage = "[player]",
            min = 1,
            max = 1
    )
    @CommandPermissions("smp.core.remove")
    @SuppressWarnings("deprecation")
    public static void remove(final CommandContext args, final CommandSender sender) throws CommandException {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args.getString(0));
        SMP.getWhitelist.removePlayer(sender, offlinePlayer);
        sender.sendMessage(ChatColor.RED + "Removed " + ChatColor.DARK_AQUA + offlinePlayer.getName() + ChatColor.RED + " from the whitelist!");
    }

    @Command(
            aliases = {"toggle"},
            desc = "Toggles the SMP whitelist.",
            max = 0
    )
    @CommandPermissions("smp.core.toggle")
    public static void toggle(final CommandContext args, final CommandSender sender) throws CommandException {
        WhitelistObject whitelist = SMP.getWhitelist;
        if (whitelist.isEnabled()) {
            whitelist.disable();
            sender.sendMessage(ChatConstant.DISABLE_WHITELIST.formatAsSuccess());
        } else {
            whitelist.enable();
            whitelist.kick();
            sender.sendMessage(ChatConstant.ENABLED_WHITELIST.formatAsSuccess());
        }
    }

    @Command(
            aliases = {"kick"},
            desc = "Kicks everyone who's not in the whitelist.",
            max = 0
    )
    @CommandPermissions("smp.core.kick")
    public static void kick(final CommandContext args, final CommandSender sender) throws CommandException {
        WhitelistObject whitelist = SMP.getWhitelist;
        whitelist.kick();
    }

    @Command(
            aliases = {"reload"},
            desc = "Reloads the SMP's configuration.",
            max = 0
    )
    @CommandPermissions("smp.core.reload")
    public static void reload(final CommandContext args, final CommandSender sender) throws CommandException {
        SMP.getPlugin.reloadConfig();
        sender.sendMessage(ChatConstant.RELOADED_CONFIG.formatAsSuccess());
    }

    public static class SMPParentCommand {
        @Command(
                aliases = {"smp"},
                desc = "SMPCommand node command.",
                min = 1
        )
        @CommandPermissions("smp.core")
        @NestedCommand({SMPCommand.class})
        public static void smp(final CommandContext args, final CommandSender sender) throws CommandException {
        }
    }
}
