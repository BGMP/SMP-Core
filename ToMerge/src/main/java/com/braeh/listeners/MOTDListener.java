package com.braeh.listeners;

import java.util.List;
import java.util.Random;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import com.braeh.util.SettingsManager;

public class MOTDListener
        implements Listener
{
    @EventHandler(priority=EventPriority.MONITOR)
    public void onServerListPing(ServerListPingEvent event)
    {
        if (!SettingsManager.getInstance().getConfig().getBoolean("enabled"))
        {
            List<String> wm = SettingsManager.getInstance().getConfig().getStringList("WelcomeMOTD");
            int wr = new Random().nextInt(wm.size());
            String dm = (String)wm.get(wr);
            event.setMotd(dm.replace("&", "\u00A7").replace("NEWLINE", "\n"));
        }
        else
        {
            List<String> mm = SettingsManager.getInstance().getConfig().getStringList("MaintenanceMOTD");
            int mr = new Random().nextInt(mm.size());
            String dmm = (String)mm.get(mr);
            event.setMotd(dmm.replace("&", "\u00A7").replace("NEWLINE", "\n"));
        }
    }
}