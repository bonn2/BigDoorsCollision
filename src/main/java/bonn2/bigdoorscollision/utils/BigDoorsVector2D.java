package bonn2.bigdoorscollision.utils;

import nl.pim16aap2.bigDoors.NMS.CustomCraftFallingBlock_Vall;
import org.bukkit.World;

public class BigDoorsVector2D {

    private int x, z;
    private World world;
    private CustomCraftFallingBlock_Vall fBlock;

    public BigDoorsVector2D(int x, int z) {
        this.x = x;
        this.z = z;
        this.world = null;
        this.fBlock = null;
    }

    public BigDoorsVector2D(World world, int x, int z) {
        this.x = x;
        this.z = z;
        this.world = world;
        this.fBlock = null;
    }

    public BigDoorsVector2D(World world, int x, int z, CustomCraftFallingBlock_Vall fBlock) {
        this.x = x;
        this.z = z;
        this.world = world;
        this.fBlock = fBlock;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public World getWorld() {
        return world;
    }

    public CustomCraftFallingBlock_Vall getFBlock() {
        return fBlock;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setFBlock(CustomCraftFallingBlock_Vall fBlock) {
        this.fBlock = fBlock;
    }

    public BigDoorsVector2D add(int x, int z) {
        this.x += x;
        this.z += z;
        return this;
    }

    public BigDoorsVector2D subtract(int x, int z) {
        this.x -= x;
        this.z -= z;
        return this;
    }
}
