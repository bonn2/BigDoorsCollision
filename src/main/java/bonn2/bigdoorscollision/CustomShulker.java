package bonn2.bigdoorscollision;

import nl.pim16aap2.bigDoors.util.MyBlockData;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class CustomShulker {

    private Entity armorStand, shulker;
    private Location location;
    private World world;
    private Vector velocity;
    private Location startingLocation;

    public CustomShulker(MyBlockData blockData) {
        startingLocation = blockData.getStartLocation();
        location = blockData.getFBlock().getLocation();
        world = location.getWorld();
        velocity = blockData.getFBlock().getVelocity();
        assert world != null;
        Entity mineacrt = world.spawnEntity(location, EntityType.MINECART);
        //((Minecart) mineacrt).off
//        armorStand = world.spawnEntity(location.subtract(0, 1.48125, 0), EntityType.ARMOR_STAND);
//        armorStand.setGravity(false);
//        armorStand.setInvulnerable(true);
//        ((LivingEntity) armorStand).setCollidable(false);
//        ((ArmorStand) armorStand).setVisible(false);
        shulker = world.spawnEntity(location, EntityType.SHULKER);
        shulker.setInvulnerable(true);
        shulker.setGravity(false);
        shulker.setSilent(true);
        ((LivingEntity) shulker).setAI(false);
        ((LivingEntity) shulker).setCollidable(false);
        ((LivingEntity) shulker).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,2147483647, 0));
        //armorStand.addPassenger(shulker);
    }

    public Location getStartingLocation() {
        return startingLocation;
    }

    public Entity getEntity() {
        return shulker;
    }

    public void updateLocation(MyBlockData blockData) {
        Location sandLocation = blockData.getFBlock().getLocation();
        if (!shulker.getLocation().equals(sandLocation)) {
            shulker.setVelocity(blockData.getFBlock().getVelocity().multiply(10));
        }
    }


}
