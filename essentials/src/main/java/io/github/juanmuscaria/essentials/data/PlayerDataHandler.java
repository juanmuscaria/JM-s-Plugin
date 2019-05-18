package io.github.juanmuscaria.essentials.data;

import io.github.juanmuscaria.core.data.IPlayerDataHandler;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.core.data.serializable.SerializableInventory;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerDataHandler implements IPlayerDataHandler {
    PlayerDataHandler(){
        PlayerData.addHandler(this);
    }
    @Override
    public void onEnable(PlayerData data) {
        HashMap<String,Object> dataHashMap = data.getData();
        if (!(dataHashMap.get("player.inventory") == null)&&!(dataHashMap.get("player.enderchest") == null)&&!data.isOfflineData()){
            Player p = data.getPlayer();
            SerializableInventory inventory = (SerializableInventory) dataHashMap.get("player.inventory");
            SerializableInventory enderchest = (SerializableInventory) dataHashMap.get("player.enderchest");
            p.getInventory().setContents(inventory.generateInventory(null).getContents());
            p.getEnderChest().setContents(enderchest.generateInventory(null).getContents());
        }
    }

    @Override
    public void onSave(PlayerData data) {

    }

    @Override
    public void onDisable(PlayerData data) {
        if (!data.isOfflineData()){
            Player p = data.getPlayer();
            SerializableInventory inventory = new SerializableInventory(p.getInventory());
            SerializableInventory enderchest = new SerializableInventory(p.getEnderChest());
        }
    }
}
