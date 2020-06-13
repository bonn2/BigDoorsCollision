package bonn2.bigdoorscollision;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ShulkerDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Main.plugin.shulkers.removeIf(shulker -> shulker.getEntity().equals(event.getEntity()));
    }
}
