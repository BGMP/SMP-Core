package me.prodigy4532.smpcore.Utils;

public enum Permissions {
    ADMIN_CHAT("smp.chat.admin", "Allows access to the admin chat."),
    GLOBAL_CHAT("smp.chat.global", "Allows access to chat globally."),
    CHAT_COLOR("smp.chat.color", "Chat color permission."),
    CHAT_PM("smp.chat.message", "Allows permission to send private messages"),
    CHAT_REPLY("smp.chat.reply", "Allows permission to reply the last private message received."),
    CHAT_BROADCAST("smp.core.broadcast", "Allows access to broadcast messages."),
    SMP_CORE("smp.core", "Allows permission for the smp's node command & sub-commands."),
    SMP_ADD("smp.core.add", "Adds a player to the smp whitelist."),
    SMP_REMOVE("smp.core.remove", "Removes a player from the smp whitelist."),
    SMP_TOGGLE("smp.core.toggle", "Toggles the smp whitelist on and off."),
    SMP_KICK("smp.core.kick", "Kicks all players off the whitelist");

    private String permission;
    private String description;

    Permissions(String permission, String description) {
        this.permission = permission;
        this.description = description;
    }

    public String getText() {
        return permission;
    }

    public String getDescription() {
        return description;
    }
}
