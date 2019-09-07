package me.prodigy4532.smpcore.Chat;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ChannelRegistry {
    private HashMap<String, Channel> registry;

    public HashMap<String, Channel> getRegistry() {
        return registry;
    }

    public void setRegistry(HashMap<String, Channel> registry) {
        this.registry = registry;
    }

    public ChannelRegistry(HashMap<String, Channel> registry) {
        this.registry = registry;
    }

    public Channel getPlayerChannel(Player player) {
        return registry.get(player.getUniqueId().toString());
    }

    public void registerPlayer(Player player) {
        registry.put(player.getUniqueId().toString(), Channel.GLOBAL);
    }

    public void setPlayerChannel(Player player, Channel channel) {
        registry.put(player.getUniqueId().toString(), channel);
    }

    public void destroyPlayerRegistry(Player player) {
        registry.remove(player.getUniqueId().toString());
    }
}
