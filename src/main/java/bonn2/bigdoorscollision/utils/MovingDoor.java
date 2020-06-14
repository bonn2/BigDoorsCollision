package bonn2.bigdoorscollision.utils;

import nl.pim16aap2.bigDoors.BigDoors;
import nl.pim16aap2.bigDoors.NMS.CustomCraftFallingBlock_Vall;
import nl.pim16aap2.bigDoors.moveBlocks.*;
import nl.pim16aap2.bigDoors.util.MyBlockData;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovingDoor {

    private List<MyBlockData> blocks;
    private List<CollisionShulker> shulkers;
    private Map<Location, CustomCraftFallingBlock_Vall> fallingBlocks;
    private Map<BigDoorsVector2D, Integer> highestPoint;
    private boolean useExtraShulkers;

    public MovingDoor(Long id) {
        // Use door id to get BlockMover from BigDoors Commander
        BlockMover blockMover = BigDoors.get().getCommander().getBlockMover(id);
        shulkers = new ArrayList<>();
        fallingBlocks = new HashMap<>();
        highestPoint = new HashMap<>();
        useExtraShulkers = true;

        // TODO: Level up at reflection and make this no longer necessary to be a if/else/if where only the class is different
        if (blockMover instanceof SlidingMover) {
            try {
                Field SavedBlocks = SlidingMover.class.getDeclaredField("savedBlocks");
                SavedBlocks.setAccessible(true);
                // Get private variable of MyBlockData List
                blocks = (List<MyBlockData>) SavedBlocks.get(blockMover);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        } else if (blockMover instanceof CylindricalMover) {
            try {
                Field SavedBlocks = CylindricalMover.class.getDeclaredField("savedBlocks");
                SavedBlocks.setAccessible(true);
                blocks = (List<MyBlockData>) SavedBlocks.get(blockMover);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        } else if (blockMover instanceof BridgeMover) {
            try {
                Field SavedBlocks = BridgeMover.class.getDeclaredField("savedBlocks");
                SavedBlocks.setAccessible(true);
                blocks = (List<MyBlockData>) SavedBlocks.get(blockMover);
                useExtraShulkers = false;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        } else if (blockMover instanceof VerticalMover) {
            try {
                Field SavedBlocks = VerticalMover.class.getDeclaredField("savedBlocks");
                SavedBlocks.setAccessible(true);
                blocks = (List<MyBlockData>) SavedBlocks.get(blockMover);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        } else {
            System.out.println(blockMover.toString());
            return;
        }
        spawnShulkers();
    }

    private void spawnShulkers() {
        // Create a shulker for each block
        for (MyBlockData myBlockData : blocks) {
            Location startingLocation = myBlockData.getStartLocation();
            BigDoorsVector2D vector2DLocation = new BigDoorsVector2D(
                    startingLocation.getWorld(),
                    startingLocation.getBlockX(),
                    startingLocation.getBlockZ(),
                    myBlockData.getFBlock());
            if (!highestPoint.containsKey(vector2DLocation)) {
                highestPoint.put(vector2DLocation, startingLocation.getBlockY());
            } else if (highestPoint.get(vector2DLocation) < startingLocation.getBlockY()) {
                highestPoint.put(vector2DLocation, startingLocation.getBlockY());
            }
            CollisionShulker collisionShulker = new CollisionShulker(startingLocation, false);
            fallingBlocks.put(startingLocation, myBlockData.getFBlock());
            shulkers.add(collisionShulker);
            ((CraftWorld) startingLocation.getWorld()).getHandle().addEntity(collisionShulker, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
        // Spawn extra shulker on top of the stack as crappy way to fix bug that the top shulkers always go to the bottom of the stack
        // TODO: Make below code unnecessary
        // TODO: Add special case for bridge as it does not seem to have this issue
        if (useExtraShulkers) {
            for (BigDoorsVector2D bigDoorsVector2D : highestPoint.keySet()) {
                Location startingLocation = new Location(
                        bigDoorsVector2D.getWorld(),
                        bigDoorsVector2D.getX(),
                        highestPoint.get(bigDoorsVector2D) + 1,
                        bigDoorsVector2D.getZ());
                CollisionShulker collisionShulker = new CollisionShulker(startingLocation, true);
                fallingBlocks.put(startingLocation, bigDoorsVector2D.getFBlock());
                shulkers.add(collisionShulker);
                ((CraftWorld) startingLocation.getWorld()).getHandle().addEntity(collisionShulker, CreatureSpawnEvent.SpawnReason.CUSTOM);
            }
        }
    }

    public void updateLocation() {
        // Teleport every tracked shulker to associated CustomFallingBlock location
        for (CollisionShulker shulker : shulkers) {
            CustomCraftFallingBlock_Vall fallingBlock = fallingBlocks.get(shulker.getStartingLocation());
            Location location = fallingBlock.getLocation();
            if (shulker.isHighest()) {
                location.add(0, 1, 0);
            }
            shulker.teleportAndSync(location.getX(), location.getY(), location.getZ());
        }
    }

    public void removeEntities() {
        for (CollisionShulker shulker : shulkers) {
            // Kill entities when they are in the void to remove partials, animation, and drops
            Location location = shulker.getStartingLocation();
            shulker.setHealth(0);
            shulker.teleportAndSync(location.getX(), -10, location.getZ());
        }
    }
}
