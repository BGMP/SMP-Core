package com.braeh.commands;

import org.bukkit.command.CommandSender;

public abstract class MaintenanceCommand
{
    private String name;
    private String desc;
    private String args;
    private String alias;

    public MaintenanceCommand(String name, String desc, String args, String alias)
    {
        this.name = name;
        this.desc = desc;
        this.args = args;
        this.alias = alias;
    }

    public String getName()
    {
        return this.name;
    }

    public String getDescription()
    {
        return this.desc;
    }

    public String getArgs()
    {
        return this.args;
    }

    public String getAlias()
    {
        return this.alias;
    }

    public abstract void run(CommandSender paramCommandSender, String[] paramArrayOfString);
}

