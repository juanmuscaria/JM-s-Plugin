package io.github.juanmuscaria.core.data;

public interface IPlayerDataHandler {

    void onEnable(PlayerData data);

    void onSave(PlayerData data);

    void onDisable(PlayerData data);
}
