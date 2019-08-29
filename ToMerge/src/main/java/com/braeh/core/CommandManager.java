package com.braeh.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import com.braeh.commands.MaintenanceCommand;
import com.braeh.commands.playerAdderAndRemover;
import com.braeh.commands.reload;
import com.braeh.commands.toggle;
import com.braeh.commands.toggledelay;

public class CommandManager
        implements CommandExecutor
{
    private ArrayList<MaintenanceCommand> cmds = new ArrayList();

    public CommandManager()
    {
        this.cmds.add(new toggle());
        this.cmds.add(new toggledelay());
        this.cmds.add(new reload());
        this.cmds.add(new playerAdderAndRemover());
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.AQUA + "============== " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Server Maintenance Help " + ChatColor.AQUA + "==============");
            label212:
            for (Iterator localIterator1 = this.cmds.iterator(); localIterator1.hasNext(); playerAdderCommand(sender))
            {
                MaintenanceCommand c = (MaintenanceCommand)localIterator1.next();
                sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance " + c.getName() + (c.getArgs() == null ? " " : new StringBuilder(" ").append(c.getArgs()).append(" ").toString()) + ChatColor.DARK_AQUA + c.getDescription());
                if ((c.getName() == null) || (!c.getName().equalsIgnoreCase("player"))) {
                    break label212;
                }
            }
            sender.sendMessage(ChatColor.AQUA + "=====================================================");
            return true;
        }
        ArrayList<String> a = new ArrayList(Arrays.asList(args));
        a.remove(0);
        for (MaintenanceCommand c : this.cmds) {
            if ((c.getName().equalsIgnoreCase(args[0])) || (c.getAlias().equalsIgnoreCase(args[0])))
            {
                try
                {
                    c.run(sender, (String[])a.toArray(new String[a.size()]));
                }
                catch (Exception e)
                {
                    sender.sendMessage(ChatColor.RED + "An error has occurred.");e.printStackTrace();
                }
                return true;
            }
        }
        return true;
    }

    private void playerAdderCommand(CommandSender sender)
    {
        sender.sendMessage(ChatColor.DARK_RED + "   -   " + ChatColor.AQUA + "/maintenance player remove <player> " + ChatColor.DARK_AQUA + "Removes a player from the allowed-players.txt file");
    }
}