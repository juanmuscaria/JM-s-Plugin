package io.github.juanmuscaria.essentials.data;

import io.github.juanmuscaria.core.data.IPlayerDataHandler;
import io.github.juanmuscaria.core.data.PlayerData;

import java.util.HashMap;

public class PlayerDataHandler implements IPlayerDataHandler {
    public PlayerDataHandler(){
        PlayerData.addHandler(this);
    }

    @Override
    public void onEnable(PlayerData data) {
        data.getData().computeIfAbsent("homes", K -> new HashMap<String, HomeData>());
    }

    @Override
    public void onSave(PlayerData data) {

    }

    @Override
    public void onDisable(PlayerData data) {

    }
}
