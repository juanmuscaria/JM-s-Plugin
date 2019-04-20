package io.github.juanmuscaria.core.event;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class BlockCommand implements Listener, IEvent {

    private final List<String> CmdPlugins = Arrays.asList("/plugins", "/pl", "/bukkit:plugins", "/bukkit:pl", "/bukkit:?", "/?");

    public BlockCommand() {
        JMCore.getInstance().getServer().getPluginManager().registerEvents(this, JMCore.getInstance());
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
        YamlConfiguration config = JMCore.globalPluginConfig.Get();

        for (String str : config.getStringList("blockedcmds.cmdlist"))
            if (event.getMessage().toLowerCase().startsWith(str) || event.getMessage().equalsIgnoreCase(str)) {
                event.getPlayer().sendMessage(config.getString("blockedcmds.msg").replace('&', '§'));
                event.setCancelled(true);
            }

    }

    @Override
    public void reload() {/* Esse evento n precisa disso. */}

    @Override
    public void disable() {/* Esse evento n precisa disso.*/}

    @Override
    public void save() {/* Esse evento n precisa disso*/}

}
