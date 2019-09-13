package me.prodigy4532.smpcore.Chat;

import me.prodigy4532.smpcore.SMP;
import me.prodigy4532.smpcore.Utils.Permissions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Message {
    private Channel channel;
    private Text text;
    private Player sender;

    public Message(Text text, Channel channel, Player sender) {
        this.text = text;
        this.channel = channel;
        this.sender = sender;
    }

    public void send() {
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (onlinePlayer.hasPermission(channel.getPermissionParent().getText())) {
                if (sender.hasPermission(Permissions.CHAT_COLOR.getText())) onlinePlayer.sendMessage(buildColoredMessage());
                else onlinePlayer.sendMessage(buildRawMessage());
            }
        });
    }

    public void broadcast() {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(SMP.getPlugin.getConfig().getString("chat.broadcast"))) + text.getColoredText());
    }

    private String buildColoredMessage() {
        return parseChatFormat().replaceAll("%msg%", text.getColoredText());
    }

    private String buildRawMessage() {
        return parseChatFormat().replaceAll("%msg%", text.getText());
    }

    private String parseChatFormat() {
        return ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(SMP.getPlugin.getConfig().getString("chat.format"))
                .replaceAll("%channel_prefix%", channel.getPrefix())
                .replaceAll("%player%", SMP.getChat().getPlayerPrefix(sender) + sender.getDisplayName() + SMP.getChat().getPlayerSuffix(sender)
                )
        );
    }

    public static class Text {
        private char colorChar = '&';
        private String text;

        public Text(String text) {
            this.text = text;
        }

        String getText() {
            return text;
        }

        public void setColorChar(char colorChar) {
            this.colorChar = colorChar;
        }

        String getColoredText() {
            return ChatColor.translateAlternateColorCodes(colorChar, text);
        }
    }
}
