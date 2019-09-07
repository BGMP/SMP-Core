package me.prodigy4532.smpcore.Chat;

import me.prodigy4532.smpcore.Utils.Permissions;

public enum Channel {
    ADMIN("admin", "&f[&6A&f]&r ", Permissions.ADMIN_CHAT),
    GLOBAL("global", "", Permissions.GLOBAL_CHAT);

    private String name;
    private String prefix;
    private Permissions permissionParent;

    Channel(String name, String prefix, Permissions permissionParent) {
        this.name = name;
        this.prefix = prefix;
        this.permissionParent = permissionParent;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public Permissions getPermissionParent() {
        return permissionParent;
    }
}
