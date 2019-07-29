package io.github.juanmuscaria.chunks.data;

import io.github.juanmuscaria.core.data.serializable.SerializableLocation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class PlayerData implements Serializable {
    private UUID player;
    private HashMap<UUID,Chunkloader> avaliableChunkloaders = new HashMap<>();
    private HashMap<SerializableLocation,Chunkloader> activeChunkloaersAlwaysOn = new HashMap<>();
    private HashMap<SerializableLocation,Chunkloader> activeChunkloaers = new HashMap<>();

    public PlayerData(UUID player){
        this.player = player;
    }

    public UUID getPlayer() {
        return player;
    }

    public HashMap<UUID, Chunkloader> getAvaliableChunkloaders(){
        return avaliableChunkloaders;
    }
    public HashMap<SerializableLocation,Chunkloader> getActiveChunkloaers(){
        return activeChunkloaers;
    }
    public HashMap<SerializableLocation,Chunkloader> getActiveChunkloaersAlwaysOn() {
        return activeChunkloaersAlwaysOn;
    }
}
