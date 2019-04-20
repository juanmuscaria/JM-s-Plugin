package io.github.juanmuscaria.core.task;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalTime;

//TODO:(re)Fazer os premios baseados no ontime(incompleto).
public class OnlineTime extends BukkitRunnable {
    @Override
    public void run() {
        Bukkit.getScheduler().runTask(JMCore.getInstance(), new Runnable() {
            @Override
            public void run() {
                Logger.Debug("Executando ontime");
                JMCore.getInstance().playerDataHashMap.forEach((key, value) -> {
                    if (!value.IsOfflineData()) {
                        if (LocalTime.now().isAfter(value.getTime().plusHours(1L))) {
                            try {
                                value.setTime(LocalTime.now());
                                YamlConfiguration con = JMCore.globalPluginConfig.Get();
                                String a = con.getString("ontime.command").replace("%player%", value.getPlayerObj().getName());
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), a);
                                String b = con.getString("ontime.msg").replace('&', '§');
                                value.getPlayerObj().sendMessage(b);
                            } catch (Exception ex) {
                                Logger.Error(ex.getMessage());
                                ex.printStackTrace();
                            }
                        } else {
                            Logger.Error("Foi encontrado um player offline na lista de jogadores online, isso poderá gerar erros graves no futuro.");
                        }
                    }
                });

            }
        });
    }
}
