package io.github.juanmuscaria.essentials.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import io.github.juanmuscaria.core.APIs;
import io.github.juanmuscaria.core.event.IEvent;
import io.github.juanmuscaria.essentials.JMEssentials;
import io.github.juanmuscaria.essentials.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

//TODO: Refazer do 0 pq a droga do forge ferra totalmente com o pacote do server ping.
public class PAServerPing extends PacketAdapter implements IEvent {
    public PAServerPing() {
        super(JMEssentials.getInstance(), ListenerPriority.NORMAL, Collections.singletonList(PacketType.Status.Server.OUT_SERVER_INFO));
        APIs.protocolManager.addPacketListener(this);
        Logger.Debug("Evento " + ChatColor.RED + "BlockTab" + ChatColor.WHITE + " foi registrado.");
    }

    @Override
    public void onPacketSending(PacketEvent event) {

        WrappedServerPing ping = event.getPacket().getServerPings().read(0);
        List<WrappedGameProfile> list = Arrays.asList(
                new WrappedGameProfile("1", "┌──────────────────────┐"),
                new WrappedGameProfile("2", "├      FeatherTech     ┤"),
                new WrappedGameProfile("3", "└──────────────────────┘"),
                new WrappedGameProfile("4", "Jogadores online:"));
        AtomicInteger index = new AtomicInteger(5);
        if (Bukkit.getOnlinePlayers().isEmpty())
            list.add(new WrappedGameProfile(String.valueOf(index.get()), "Nenhum jogador online!"));

        else {
            Bukkit.getOnlinePlayers().forEach(P -> {
                list.add(new WrappedGameProfile(String.valueOf(index.get()), P.getName() + "  ♥ " + P.getHealth()));
                index.getAndIncrement();
            });
        }
        ping.setPlayersVisible(true);
        ping.setPlayers(list);
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
