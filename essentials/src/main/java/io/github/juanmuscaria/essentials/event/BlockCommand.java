package io.github.juanmuscaria.essentials.event;

import io.github.juanmuscaria.core.event.IEvent;
import io.github.juanmuscaria.essentials.JMEssentials;
import io.github.juanmuscaria.essentials.data.PluginConfig;
import io.github.juanmuscaria.essentials.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class BlockCommand implements Listener, IEvent {

    private final List<String> CmdPlugins = Arrays.asList("/plugins", "/pl", "/bukkit:plugins", "/bukkit:pl", "/bukkit:?", "/?");

    public BlockCommand() {
        JMEssentials.getInstance().getServer().getPluginManager().registerEvents(this, JMEssentials.getInstance());
        Logger.Debug("Evento " + ChatColor.RED + "BlockCommand" + ChatColor.WHITE + " foi registrado.");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(final PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("jm.admin.bypass")) return;
        if ((event.getMessage().toLowerCase().startsWith("/help") || event.getMessage().equalsIgnoreCase("/help"))) {
            event.setCancelled(true);
            event.getPlayer().performCommand("/ajuda");
        }

        for (String str : CmdPlugins)
            if (event.getMessage().toLowerCase().startsWith(str) || event.getMessage().equalsIgnoreCase(str)) {
                //Receita de como fazer um server.
                event.getPlayer().sendMessage(ChatColor.AQUA + "Bata as claras em neve e reserve.\nMisture as gemas, a" +
                        " margarina e o açúcar até obter uma massa homogênea.\nAcrescente o leite e a farinha de trigo aos" +
                        " poucos, sem parar de bater.\nPor último, adicione as claras em neve e o fermento.\nDespeje a " +
                        "massa em uma forma grande de furo central untada e enfarinhada.\nAsse em forno médio 180 °C, " +
                        "preaquecido, por aproximadamente 40 minutos ou ao furar o bolo com um garfo, este saia limpo");
                event.setCancelled(true);
            }

        for (String str : PluginConfig.pluginConfig.blockedcmds_cmdlist)
            if (event.getMessage().toLowerCase().startsWith(str) || event.getMessage().equalsIgnoreCase(str)) {
                event.getPlayer().sendMessage(PluginConfig.pluginConfig.blockedcmds_msg.replace('&', '§'));
                event.setCancelled(true);
            }

    }

    @Override
    public void reload() {/* Esse evento n precisa disso. */}

    @Override
    public void disable() {/* Esse evento n precisa disso.*/}

    @Override
    public void save() {/* Esse evento n precisa disso.*/}

}
