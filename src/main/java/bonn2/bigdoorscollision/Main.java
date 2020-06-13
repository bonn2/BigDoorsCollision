package bonn2.bigdoorscollision;

import nl.pim16aap2.bigDoors.BigDoors;
import nl.pim16aap2.bigDoors.Commander;
import nl.pim16aap2.bigDoors.moveBlocks.BlockMover;
import nl.pim16aap2.bigDoors.moveBlocks.CylindricalMover;
import nl.pim16aap2.bigDoors.moveBlocks.SlidingMover;
import nl.pim16aap2.bigDoors.util.MyBlockData;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Main extends JavaPlugin {

    public List<CustomShulker> shulkers;

    public static Main plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        shulkers = new ArrayList<>();
        BukkitScheduler scheduler = getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            Commander commander = BigDoors.get().getCommander();
            List<BlockMover> blockMovers = commander.getBlockMovers().collect(Collectors.toList());
            for (BlockMover blockMover : blockMovers) {
                if (blockMover instanceof SlidingMover) {
                    SlidingMover slidingMover = (SlidingMover) blockMover;
                    Field SavedBlocks;
                    try {
                        SavedBlocks = SlidingMover.class.getDeclaredField("savedBlocks");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        return;
                    }
                    SavedBlocks.setAccessible(true);
                    List<MyBlockData> savedBlocks;
                    try {
                        savedBlocks = (List<MyBlockData>) SavedBlocks.get(slidingMover);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return;
                    }
                    for(MyBlockData blockData : savedBlocks) {
                        Location id = blockData.getStartLocation();
                        boolean exists = false;
                        for (CustomShulker shulker : shulkers) {
                            if (shulker.getStartingLocation().equals(id)) {
                                //shulker.updateLocation(blockData);
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            System.out.println(blockData.getStartLocation().toString());
                            shulkers.add(new CustomShulker(blockData));
                        }
                    }
                } else if (blockMover instanceof CylindricalMover) {
                    CylindricalMover cylindricalMover = (CylindricalMover) blockMover;
                    Field SavedBlocks;
                    try {
                        SavedBlocks = CylindricalMover.class.getDeclaredField("savedBlocks");
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        return;
                    }
                    SavedBlocks.setAccessible(true);
                    List<MyBlockData> savedBlocks;
                    try {
                        savedBlocks = (List<MyBlockData>) SavedBlocks.get(cylindricalMover);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return;
                    }
                    for(MyBlockData blockData : savedBlocks) {
                        Location id = blockData.getStartLocation();
                        boolean exists = false;
                        for (CustomShulker shulker : shulkers) {
                            if (shulker.getStartingLocation().equals(id)) {
                                shulker.updateLocation(blockData);
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            System.out.println(blockData.getStartLocation().toString());
                            shulkers.add(new CustomShulker(blockData));
                        }
                    }
                }
            }

        }, 0L, 4L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
