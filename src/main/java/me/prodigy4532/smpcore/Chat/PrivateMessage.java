package me.prodigy4532.smpcore.Chat;

import me.prodigy4532.smpcore.SMP;
import me.prodigy4532.smpcore.Utils.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PrivateMessage {
    private Player sender;
    private Player receiver;
    private Message.Text text;
    private Sound pmSound = Sound.valueOf(SMP.getPlugin.getConfig().getString("chat.pm.sound.effect"));
    private int v = SMP.getPlugin.getConfig().getInt("chat.pm.sound.v");
    private int v1 = SMP.getPlugin.getConfig().getInt("chat.pm.sound.v1");
    private String prefix = SMP.getPlugin.getConfig().getString("chat.pm.prefix");
    private String pmToFormat = SMP.getPlugin.getConfig().getString("chat.pm.to");
    private String pmFromFormat = SMP.getPlugin.getConfig().getString("chat.pm.from");

    public PrivateMessage(Player sender, Player receiver, Message.Text text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public Message.Text getText() {
        return text;
    }

    public void setText(Message.Text text) {
        this.text = text;
    }

    public void send() {
        if (sender.hasPermission(Permissions.CHAT_COLOR.getText())) {
            sender.sendMessage(parsePMToFormat().replaceAll("%msg%", text.getColoredText()));
            receiver.sendMessage(parsePMFromFormat().replaceAll("%msg%", text.getColoredText()));
        } else {
            sender.sendMessage(parsePMToFormat().replaceAll("%msg%", text.getText()));
            receiver.sendMessage(parsePMFromFormat().replaceAll("%msg%", text.getText()));
        }
        receiver.playSound(receiver.getLocation(), pmSound, v, v1);
    }

    private String parsePMToFormat() {
        return ChatColor.translateAlternateColorCodes('&', pmToFormat
                .replaceAll("%pm_prefix%", prefix)
                .replaceAll("%player%", SMP.getChat().getPlayerPrefix(receiver) + receiver.getDisplayName() + SMP.getChat().getPlayerSuffix(sender))
        );
    }

    private String parsePMFromFormat() {
        return ChatColor.translateAlternateColorCodes('&', pmFromFormat
                .replaceAll("%pm_prefix%", prefix)
                .replaceAll("%player%", SMP.getChat().getPlayerPrefix(sender) + sender.getDisplayName() + SMP.getChat().getPlayerSuffix(sender))
        );
    }

    public static class ReplyQueue {
        HashMap<String, String> queue;

        public ReplyQueue(HashMap<String, String> queue) {
            this.queue = queue;
        }

        public HashMap<String, String> getReplyQueue() {
            return queue;
        }

        public void setReplyQueue(HashMap<String, String> replyQueue) {
            this.queue = replyQueue;
        }

        public String getPlayerReplier(Player sender) {
            return queue.get(sender.getUniqueId().toString());
        }

        public void setReplicantPair(Player sender, Player replyPlayer) {
            queue.put(sender.getUniqueId().toString(), replyPlayer.getUniqueId().toString());
            queue.put(replyPlayer.getUniqueId().toString(), sender.getUniqueId().toString());
        }

        public void removePair(Player sender) {
            queue.remove(sender.getUniqueId().toString());
        }
    }
}
