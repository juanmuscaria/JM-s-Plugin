package io.github.juanmuscaria.essentials.data;

import io.github.juanmuscaria.core.data.serializable.SerializableLocation;
import io.github.juanmuscaria.core.utils.Utils;
import io.github.juanmuscaria.essentials.JMEssentials;
import io.github.juanmuscaria.essentials.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalTime;

public class HomeData implements Serializable {
    private SerializableLocation location;
    private Boolean isPublic;
    private LocalTime cooldown;

    public HomeData(SerializableLocation location, Boolean isPublic){
        this.location = location;
        this.isPublic = isPublic;
        cooldown = LocalTime.now();
    }

    public SerializableLocation getLocation() {
        return location;
    }

    public void setLocation(SerializableLocation location) {
        this.location = location;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void doTeleport(Player p){
        if (LocalTime.now().isBefore(cooldown.plusSeconds(Utils.getUserPermissionInteger("jm.home.cooldown",p)))&& !p.isOp() && !p.hasPermission("jm.admin.home.bypasscooldown")){
            p.sendMessage(ChatColor.RED + "Espere 3 para poder teleportar-se para essa home novamente.");
            return;
        }
        cooldown = LocalTime.now();
       try {
           p.sendMessage(ChatColor.GREEN + "Teleportando-se em 3 segundos, aguarde.");
           Bukkit.getScheduler().runTaskLater(JMEssentials.getInstance(), () -> {
               Location location = this.location.toLocation();
               location.getWorld().loadChunk(location.getChunk());
               p.teleport(location, PlayerTeleportEvent.TeleportCause.COMMAND);
           }, 60L);
           }
       catch (Exception e){
           p.sendMessage(ChatColor.RED + "Não foi possivel teleportar para a home: " + location.getWorldName()+":"+location.toString());
           Logger.Warn("O jogador " + p.getName() + " tentou ir para uma home invalida!\n" + "HomeData: " + location.getWorldName()+":"+location.toString());
           e.printStackTrace();
       }
   }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        in.defaultReadObject();
        cooldown = LocalTime.now();
    }
}
