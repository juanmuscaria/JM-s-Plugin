package io.github.juanmuscaria.essentials.data;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TpaData {
    private String p1;
    private String p2;


    public TpaData(String p1, String p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public String getP1() {
        return p1;
    }

    public String getP2() {
        return p2;
    }

    public boolean doTeleport(){
        Player p1O = Bukkit.getPlayer(p1);
        Player p2O = Bukkit.getPlayer(p2);
        if (p1O == null || p2O == null)return false;
        p1O.teleport(p2O, PlayerTeleportEvent.TeleportCause.COMMAND);
        p1O.sendMessage(ChatColor.GREEN + "Pedido de teleporte aceito!");
        return  true;
    }
}
