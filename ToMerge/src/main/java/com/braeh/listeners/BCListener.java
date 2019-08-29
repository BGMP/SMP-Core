package com.braeh.listeners;

import com.braeh.core.SERVER;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class BCListener
        extends BukkitRunnable
{
    private final SERVER plugin;

    public BCListener(SERVER plugin)
    {
        this.plugin = plugin;
    }

    private int bcnumber = 0;


    @Override
    public void run()
    {
        if (this.plugin.getConfig().getBoolean("AB.Enabled"))
        {
            List<String> bcm = this.plugin.message();
            if ((bcm != null) && (!bcm.isEmpty()))
            {
                this.plugin.bc((String)bcm.get(this.bcnumber));
                if (this.bcnumber < bcm.size() - 1) {
                    this.bcnumber += 1;
                } else {
                    this.bcnumber = 0;
                }
            }
        }
    }
}
