package me.prodigy4532.smpcore.Utils;

public enum Permissions {
    ADMIN_CHAT("smp.chat.admin", "Allows access to the admin chat."),
    GLOBAL_CHAT("smp.chat.global", "Allows access to chat globally."),
    CHAT_COLOR("smp.chat.color", "Chat color permission."),
    CHAT_BROADCAST("smp.core.broadcast", "Allows access to broadcast messages.");

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
