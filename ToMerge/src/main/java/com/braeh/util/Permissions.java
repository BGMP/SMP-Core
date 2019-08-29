package com.braeh.util;

import org.bukkit.permissions.Permission;

public class Permissions
{
    public Permission all = new Permission("servermaintenance.*");
    public Permission toggle = new Permission("server.toggle");
    public Permission bypass = new Permission("server.bypass");
    public Permission playerlist = new Permission("server.player.list");
    public Permission playeralll = new Permission("server.player.*");
    public Permission playeradd = new Permission("server.player.add");
    public Permission playerremove = new Permission("server.player.remove");
    public Permission reloadconfig = new Permission("server.reload");
    public Permission update = new Permission("server.update");
    public Permission stop = new Permission("server.stop");
}

