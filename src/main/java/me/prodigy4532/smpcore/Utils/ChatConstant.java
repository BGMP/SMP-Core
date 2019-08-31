package me.prodigy4532.smpcore.Utils;

import org.bukkit.ChatColor;

public enum ChatConstant {
    /* Success constants */
    LOADED_CONFIG("Successfully loaded plugin configuration"),
    RELOADED_CONFIG("Successfully reloaded plugin configuration"),
    ENABLED_WHITELIST("Whitelist is now enabled."),
    DISABLE_WHITELIST("Whitelist is now disabled"),
    /* Exception constants */
    NO_PERMISSION("You do not have permission."),
    NUMBER_STRING_EXCEPTION("Expected a number, string received instead."),
    UNKNOWN_ERROR("An unknown error has occurred."),
    ALREADY_IN_WHITELIST("Player is already whitelisted."),
    NOT_WHITELISTED("Player is not whitelisted.");

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
