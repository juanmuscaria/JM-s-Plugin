package io.github.juanmuscaria.core;

import io.github.juanmuscaria.core.comandos.JM;
import io.github.juanmuscaria.core.data.Config;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.core.event.BlockCommand;
import io.github.juanmuscaria.core.event.BlockTab;
import io.github.juanmuscaria.core.event.IEvent;
import io.github.juanmuscaria.core.event.PlayerDataEvents;
import io.github.juanmuscaria.core.task.OnlineTime;
import io.github.juanmuscaria.core.utils.CommandRegister;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JMCore extends JavaPlugin {
    public static Config globalPluginConfig;
    private static JMCore instance;
    public volatile HashMap<String, PlayerData> playerDataHashMap = new HashMap<>();
    private ConsoleCommandSender consoleCommandSender = this.getServer().getConsoleSender();
    private List<IEvent> eventos = new ArrayList<>();
    private List<BukkitTask> tasks = new ArrayList<>();

    //A instance da classe.
    public static JMCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Logger.CCS = consoleCommandSender;
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Iniciando....");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        instance = this;
        globalPluginConfig = new Config("config");
        globalPluginConfig.Reload();
        Config commandConfig = new Config("commands");
        commandConfig.Reload();
        //Registra os comandos
        CommandRegister.register("jm", "jm.cmd.player.jm", new JM(), commandConfig, null);
        APIs.loadAPIs();

        //Registra eventos e tals
        eventos.add(new PlayerDataEvents());
        if ((!(APIs.protocolManager == null)) && globalPluginConfig.Get().getBoolean("blocktab.enable"))
            eventos.add(new BlockTab());
        if (globalPluginConfig.Get().getBoolean("blockedcmds.enable")) eventos.add(new BlockCommand());


        //task
        tasks.add(new io.github.juanmuscaria.core.task.Core().runTaskTimer(this, 0L, 100L));
        if (globalPluginConfig.Get().getBoolean("ontime.active")) {
            Logger.Debug("Criando Asynchronous task ontime");
            tasks.add(new OnlineTime().runTaskTimerAsynchronously(this, 0L, 1000L));
        }
        for (Player p : getServer().getOnlinePlayers()) {
            PlayerData data = new PlayerData(p.getName().toLowerCase(), false, true);
            Logger.Warn(ChatColor.RED + "AVISO:Colocando uma instancia de um player data na inicialização. o plugin fara isso caso tenha player online na inicialização, mas poderá ocorrer erros inesperados, por favor reinicie seu servidor se possivel.");
            JMCore.getInstance().playerDataHashMap.put(p.getName().toLowerCase(), data);
        }


    }

    //Finaliza
    @Override
    public void onDisable() {
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Finalizando...Bye.");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.Debug("Finalizando os eventos.");
        eventos.forEach((E) -> {
            if (!(E == null)) E.disable();
        });
        tasks.forEach((T) -> {
            if (!(T == null)) T.cancel();
        });
        Logger.Debug("Finalizando o player data.");
        playerDataHashMap.forEach((a, b) -> b.disable());

    }

    public void onReload() {
        Logger.Debug("Dando reloading no plugin.");
        eventos.forEach((E) -> {
            if (!(E == null)) E.reload();
        });
        globalPluginConfig.Reload();
        tasks.forEach((T) -> {
            if (!(T == null)) T.cancel();
        });
        tasks.clear();
        tasks.add(new io.github.juanmuscaria.core.task.Core().runTaskTimer(this, 0L, 100L));
        if (globalPluginConfig.Get().getBoolean("ontime.active")) {
            Logger.Debug("Criando Asynchronous task ontime");
            tasks.add(new OnlineTime().runTaskTimerAsynchronously(this, 0L, 1000L));
        }
        Logger.Debug("Reloading completo.");
    }
}
