package bonn2.bigdoorscollision;

import bonn2.bigdoorscollision.listeners.DoorToggleEnd;
import bonn2.bigdoorscollision.listeners.DoorToggleStart;
import bonn2.bigdoorscollision.utils.Metrics;
import bonn2.bigdoorscollision.utils.MovingDoor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    public static Map<Long, MovingDoor> movingDoors;

    public static Main plugin;

    // Register and init variables, and start tick() repeating task
    @Override
    public void onEnable() {
        plugin = this;
        movingDoors = new HashMap<>();

        getServer().getPluginManager().registerEvents(new DoorToggleStart(), this);
        getServer().getPluginManager().registerEvents(new DoorToggleEnd(), this);

        int pluginId = 7860; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        tick();
    }

    // Attempt to remove all collision shulkers
    @Override
    public void onDisable() {
        int count = 0;
        for (Long id : movingDoors.keySet()) {
            movingDoors.get(id).removeEntities();
            count++;
        }
        getLogger().info("Attempted to remove collision entities for " + count + " doors!");
    }

    // Update all collision shulker's locations every tick
    private void tick() {
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            for (Long id : movingDoors.keySet()) {
                movingDoors.get(id).updateLocation();
            }
        }, 0L, 1L);
    }
}
