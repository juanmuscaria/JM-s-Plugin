package io.github.juanmuscaria.core;

import io.github.juanmuscaria.core.comandos.Ajuda;
import io.github.juanmuscaria.core.comandos.JM;
import io.github.juanmuscaria.core.comandos.JMcofh;
import io.github.juanmuscaria.core.comandos.Regras;
import io.github.juanmuscaria.core.data.Config;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.core.event.BlockCommand;
import io.github.juanmuscaria.core.event.BlockTab;
import io.github.juanmuscaria.core.event.IEvent;
import io.github.juanmuscaria.core.event.PlayerDataEvents;
import io.github.juanmuscaria.core.task.OnlineTime;
import io.github.juanmuscaria.core.utils.CommandLoader;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.List;

public class JMCore extends JavaPlugin {
    public static Config GlobalPluginConfig;
    private static JMCore instance;
    public volatile HashMap<String, PlayerData> Players = new HashMap<>();
    private ConsoleCommandSender Console = this.getServer().getConsoleSender();
    private List<IEvent> Eventos;
    private List<BukkitTask> Tasks;

    //A instance da classe.
    public static JMCore getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Logger.CCS = Console;
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Iniciando....");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        instance = this;
        GlobalPluginConfig = new Config("config");
        GlobalPluginConfig.Reload();
        //Registra os comandos
        new CommandLoader("jmcofh", "jm.player.jmcofh", new JMcofh());
        new CommandLoader("jm", "jm.player.jm", new JM());
        new CommandLoader("ajuda", "jm.player.ajuda", new Ajuda());
        new CommandLoader("regras", "jm.player.regras", new Regras());
        APIs.LoadAPIs();

        //Registra eventos e tals
        Eventos.add(new PlayerDataEvents());
        if ((!(APIs.protocolManager == null)) && GlobalPluginConfig.Get().getBoolean("blocktab.enable"))
            Eventos.add(new BlockTab());
        if (GlobalPluginConfig.Get().getBoolean("blockedcmds.enable")) Eventos.add(new BlockCommand());


        //task
        Tasks.add(new io.github.juanmuscaria.core.task.Core().runTaskTimer(this, 0L, 100L));
        if (GlobalPluginConfig.Get().getBoolean("ontime.active")) {
            Logger.Debug("Criando Asynchronous task ontime");
            Tasks.add(new OnlineTime().runTaskTimerAsynchronously(this, 0L, 1000L));
        }
        for (Player p : getServer().getOnlinePlayers()) {
            PlayerData data = new PlayerData(p.getName().toLowerCase(), false, true);
            Logger.Warn(ChatColor.RED + "AVISO:Colocando uma instancia de um player data na inicialização. o plugin fara isso caso tenha player online na inicialização, mas poderá ocorrer erros inesperados, por favor reinicie seu servidor se possivel.");
            JMCore.getInstance().Players.put(p.getName().toLowerCase(), data);
        }


    }

    //Finaliza
    @Override
    public void onDisable() {
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Finalizando...Bye.");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.Debug("Finalizando os eventos.");
        Eventos.forEach((E) -> {
            if (!(E == null)) E.Disable();
        });
        Logger.Debug("Finalizando o player data.");
        Players.forEach((a, b) -> b.Disable());

    }

    public void onReload() {
        Logger.Debug("Dando reloading no plugin.");
        Eventos.forEach((E) -> {
            if (!(E == null)) E.Reload();
        });
        GlobalPluginConfig.Reload();
    }
}
