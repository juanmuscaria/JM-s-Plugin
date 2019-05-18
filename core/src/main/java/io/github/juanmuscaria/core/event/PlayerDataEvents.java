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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        PlayerData data = new PlayerData(event.getPlayer().getName().toLowerCase(), event.getPlayer());
        Logger.Debug("Colocando instancia no hashmap");
        JMCore.getInstance().playerDataHashMap.put(event.getPlayer().getName().toLowerCase(), data);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent event) {
        String player = event.getPlayer().getName();
        Logger.Debug("Tirando instancia do hashmap!");
        JMCore.getInstance().playerDataHashMap.get(event.getPlayer().getName().toLowerCase()).disable();
        JMCore.getInstance().playerDataHashMap.remove(event.getPlayer().getName().toLowerCase());

    }

    @Override
    public void reload() {

    }

    @Override
    public void disable() {

    }

    @Override
    public void save() {
    }

}
