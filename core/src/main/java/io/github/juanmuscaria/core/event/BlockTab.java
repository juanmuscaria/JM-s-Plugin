package io.github.juanmuscaria.core.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import io.github.juanmuscaria.core.APIs;
import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.ChatColor;

@SuppressWarnings("ConstantConditions")
public class BlockTab extends PacketAdapter implements IEvent {

    public BlockTab() {
        super(JMCore.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Client.TAB_COMPLETE);
        APIs.protocolManager.addPacketListener(this);
        Logger.Debug("Evento " + ChatColor.RED + "BlockTab" + ChatColor.WHITE + " foi registrado.");
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        if ((event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE)
                && (!event.getPlayer().hasPermission("jm.admin.bypass"))
                && (event.getPacket().getStrings().read(0).startsWith("/"))
                && (event.getPacket().getStrings().read(0).split(" ").length == 1))
            event.setCancelled(true);
    }

    @Override
    public void reload() {/* Esse evento não precisa disso. */}

    @Override
    public void disable() {
        APIs.protocolManager.removePacketListener(this);
    }

    @Override
    public void save() {/* Esse evento não precisa disso. */}
}
