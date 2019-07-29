package io.github.juanmuscaria.essentials.task;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.essentials.JMEssentials;
import io.github.juanmuscaria.essentials.data.PluginConfig;
import io.github.juanmuscaria.essentials.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;
import java.util.Arrays;

//TODO:(re)Fazer os premios baseados no ontime(incompleto).
public class OnlineTime extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getScheduler().runTask(JMEssentials.getInstance(), new Runnable() {
            @Override
            public void run() {
                Logger.Debug("Executando ontime");
                JMCore.getInstance().playerDataHashMap.forEach((key, value) -> {
                    if (!value.isOfflineData()) {
                        if (LocalTime.now().isAfter(value.getOnTimeCache().plusHours(1L))) {
                            try {
                                value.setOnTimeCache(LocalTime.now());

                                String a = PluginConfig.pluginConfig.ontime_command.replace("%player%", value.getPlayer().getName());
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), a);
                                String b = PluginConfig.pluginConfig.ontime_msg.replace('&', '§');
                                value.getPlayer().sendMessage(b);
                            } catch (Exception ex) {
                                Logger.Error(ex.getMessage());
                                ex.printStackTrace();
                            }
                        } else {
                            Logger.Error(Arrays.asList("Foi encontrado um player offline na lista de jogadores online, isso poderá gerar erros graves no futuro.", value.getOfflinePlayer().getName()));
                        }
                    }
                });

            }
        });
    }
}
