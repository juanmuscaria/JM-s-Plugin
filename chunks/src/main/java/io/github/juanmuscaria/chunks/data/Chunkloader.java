package io.github.juanmuscaria.chunks.data;

import io.github.juanmuscaria.core.data.serializable.SerializableLocation;
import org.bukkit.Location;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public class Chunkloader implements Serializable {
    private UUID owner;
    private SerializableLocation location;
    private boolean alwaysOn = false;
    private boolean isPermanent = false;
    private LocalTime expireOn;
    private boolean isPlaced = false;

    public Chunkloader(UUID owner, boolean alwaysOn, boolean isPermanen, LocalTime expireOn) {
        this.owner = owner;
        this.alwaysOn = alwaysOn;
        this.isPermanent = isPermanen;
        this.expireOn = expireOn;
    }

    public boolean isExpired(){
        if (isPermanent)return false;
        return LocalTime.now().isAfter(expireOn);
    }
    public UUID getOwner(){
        return owner;
    }
    public Location getLocation(){
        return location.toLocation();
    }
    public void setLocation(Location location){
        this.location = new SerializableLocation(location);
    }
    public boolean isAlwaysOn(){
        return alwaysOn;
    }
    public boolean isPlaced(){
        return isPlaced;
    }
    public void setPlaced(boolean isPlaced){
        this.isPlaced = isPlaced;
    }
    public boolean isPermanent(){
        return this.isPermanent;
    }
}
