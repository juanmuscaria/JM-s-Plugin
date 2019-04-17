package io.github.juanmuscaria.core.event;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDataEvents implements Listener, IEvent {
    public PlayerDataEvents() {
        JMCore.getInstance().getServer().getPluginManager().registerEvents(this, JMCore.getInstance());
        Logger.Debug("Evento " + ChatColor.RED + "PlayerDataEvents" + ChatColor.WHITE + " foi registrado.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnJoin(PlayerJoinEvent event) {
        PlayerData data = new PlayerData(event.getPlayer().getName().toLowerCase(), false, true);
        Logger.Debug("Colocando instancia no hashmap");
        JMCore.getInstance().Players.put(event.getPlayer().getName().toLowerCase(), data);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void OnQuit(PlayerQuitEvent event) {
        String player = event.getPlayer().getName();
        Logger.Debug("Tirando instancia do hashmap!");
        try {
            JMCore.getInstance().Players.get(event.getPlayer().getName().toLowerCase()).Disable();
            JMCore.getInstance().Players.remove(event.getPlayer().getName().toLowerCase());
        } catch (NullPointerException e) {
            //TODO: Fazer algo caso ocorra esse erro
            Logger.Error("Ocorreu um erro ao tentar tirar uma instancia do hashmap");
            e.printStackTrace();
        }

    }

    @Override
    public void Reload() {

    }

    @Override
    public void Disable() {

    }

    @Override
    public void Save() {
    }

}
