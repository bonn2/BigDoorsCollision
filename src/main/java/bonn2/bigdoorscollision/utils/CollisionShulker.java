package bonn2.bigdoorscollision.utils;

import net.minecraft.server.v1_15_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftShulker;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class CollisionShulker extends EntityShulker {

    private final Location startingLocation;
    private final boolean highest;
    private final LivingEntity livingEntity;

    public CollisionShulker(Location startingLocation, boolean highest) {
        super(EntityTypes.SHULKER, ((CraftWorld) startingLocation.getWorld()).getHandle());
        this.startingLocation = startingLocation;
        this.highest = highest;
        this.setPosition(startingLocation.getX(), startingLocation.getY(), startingLocation.getZ());
        this.setGoalTarget(null);
        this.setCustomNameVisible(false);
        this.setCanPickupLoot(false);
        this.setInvulnerable(true);
        this.setNoAI(true);
        this.setNoGravity(true);
        this.setSilent(true);
        this.livingEntity = new CraftShulker((CraftServer) Bukkit.getServer(), this);
        this.livingEntity.addPotionEffect(new PotionEffect(
                PotionEffectType.INVISIBILITY,
                99999,
                0,
                true));
        this.livingEntity.setCustomName("BigDoorsCollision");
        BlockPosition blockPosition = new BlockPosition(startingLocation.getBlockX(), startingLocation.getBlockY(), startingLocation.getBlockZ());
        this.g(blockPosition);
    }

    public Location getStartingLocation() {
        return startingLocation;
    }

    public boolean isHighest() {
        return highest;
    }

    // Redefine the shulker pick teleport block location size to always 0, (May be unnecessary)
    public float aV() {
        return 0F;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }
}
