package com.braeh.commands;

import com.braeh.core.SERVER;
import com.braeh.util.SettingsManager;
import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StaffListCMD {

    @Command(aliases = {"stafflist", "staff", "sl"}, desc = "Staff List.")
    @CommandPermissions("braeh.stafflist")
    public static void scaffold(final CommandContext cmd, CommandSender sender) throws CommandException {
        if (!(sender instanceof Player)) {
            throw new CommandException(ChatColor.RED + "This command can only be used by players!");
        }
        SERVER.count += 1;
        Player guy = (Player) sender;
        List Websites = SettingsManager.getInstance().getConfig().getList("stafflist");
        for (Object Site : Websites.toArray()) {
            guy.sendMessage(Color(SettingsManager.getInstance().getConfig().getString("BROADCAST.STRING")));
            guy.sendMessage(ChatColor.translateAlternateColorCodes('&', (String)Site));
            guy.sendMessage(Color(SettingsManager.getInstance().getConfig().getString("BROADCAST.STRING")));
        }
    }

    private static String Color(String s)
    {
        return com.sk89q.minecraft.util.commands.ChatColor.translateAlternateColorCodes('&', s);
    }
}
