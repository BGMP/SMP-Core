package me.prodigy4532.smpcore.Utils;

import org.bukkit.ChatColor;

public enum ChatConstant {
    /* Success constants */
    LOADED_CONFIG("Successfully loaded plugin configuration"),
    RELOADED_CONFIG("Successfully reloaded plugin configuration"),
    ENABLED_WHITELIST("Whitelist is now enabled."),
    DISABLE_WHITELIST("Whitelist is now disabled"),
    KICKED_NON_WHITELISTED("Everyone not in the whitelist has been kicked!"),
    CHANNEL_SET_ADMIN("Chat mode now set to admin."),
    CHANNEL_SET_GLOBAL("Chat mode now set to global."),
    /* Exception constants */
    NO_PERMISSION("You do not have permission."),
    NUMBER_STRING_EXCEPTION("Expected a number, string received instead."),
    UNKNOWN_ERROR("An unknown error has occurred."),
    ALREADY_IN_WHITELIST("Player is already whitelisted."),
    NOT_WHITELISTED("Player is not whitelisted."),
    NO_CONSOLE("You must be a player to execute this command."),
    CHANNEL_ALREADY_ADMIN("Chat mode already set to admin."),
    CHANNEL_ALREADY_GLOBAL("Chat mode already set to global."),
    PLAYER_IS_OFFLINE("Player is currently offline."),
    NOBODY_TO_REPLY_TO("You have nobody to reply to.");

    private String message;

    ChatConstant(String message) {
        this.message = message;
    }

    public String formatAsSuccess() {
        return ChatColor.GREEN + message;
    }

    public String formatAsException() {
        return ChatColor.YELLOW + "⚠ " + ChatColor.RED + message;
    }
}
