package me.prodigy4532.smpcore.EventHandlers;

import me.prodigy4532.smpcore.SMP;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class BockEvents implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                Block block = event.getBlock();
                Material material = block.getType();
                if (material != Material.ICE) block.setType(Material.AIR);
                else block.setType(Material.WATER);
                this.cancel();
            }
        }.runTaskTimer(SMP.getPlugin, 0L, 0L);
    }
}
