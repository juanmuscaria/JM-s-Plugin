package io.github.juanmuscaria.core.data.serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class SerializableLocation implements Serializable, Cloneable {
    private String world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public SerializableLocation(String world, double x, double y, double z) {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    public SerializableLocation(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public SerializableLocation(@NotNull World world, double x, double y, double z) {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    public SerializableLocation(@NotNull World world, double x, double y, double z, float yaw, float pitch) {
        this.world = world.getName();
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public SerializableLocation(@NotNull Location location) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
    }

    public double getX() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Vector getDirection() {
        Vector vector = new Vector();
        double rotX = (double) this.getYaw();
        double rotY = (double) this.getPitch();
        vector.setY(-Math.sin(Math.toRadians(rotY)));
        double xz = Math.cos(Math.toRadians(rotY));
        vector.setX(-xz * Math.sin(Math.toRadians(rotX)));
        vector.setZ(xz * Math.cos(Math.toRadians(rotX)));
        return vector;
    }

    public SerializableLocation setDirection(Vector vector) {
        double _2PI = 6.283185307179586D;
        double x = vector.getX();
        double z = vector.getZ();
        if (x == 0.0D && z == 0.0D) {
            this.pitch = vector.getY() > 0.0D ? -90.0F : 90.0F;
            return this;
        } else {
            double theta = Math.atan2(-x, z);
            this.yaw = (float) Math.toDegrees((theta + 6.283185307179586D) % 6.283185307179586D);
            double x2 = NumberConversions.square(x);
            double z2 = NumberConversions.square(z);
            double xz = Math.sqrt(x2 + z2);
            this.pitch = (float) Math.toDegrees(Math.atan(-vector.getY() / xz));
            return this;
        }
    }

    public Location toLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
    }

    public String getWorldName() {
        return world;
    }

    @Override
    public String toString() {
        return "X:" + x + " Y:" + y + " Z:" + z + " Mundo:" + world;
    }

}
