package me.prodigy4532.smpcore.EventHandlers;

import me.prodigy4532.smpcore.Chat.Channel;
import me.prodigy4532.smpcore.Chat.Message;
import me.prodigy4532.smpcore.SMP;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        Message.Text messageText = new Message.Text(event.getMessage());
        Channel channel = SMP.getChannelRegistry.getPlayerChannel(event.getPlayer());
        Message message = new Message(messageText, channel, event.getPlayer());
        message.send();
    }
}
