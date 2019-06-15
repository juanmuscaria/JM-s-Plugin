package io.github.juanmuscaria.essentials;


import io.github.juanmuscaria.core.APIs;
import io.github.juanmuscaria.core.event.IEvent;
import io.github.juanmuscaria.core.utils.Cache;
import io.github.juanmuscaria.core.utils.CommandRegister;
import io.github.juanmuscaria.core.utils.Function;
import io.github.juanmuscaria.essentials.comandos.*;
import io.github.juanmuscaria.essentials.data.OldConfig;
import io.github.juanmuscaria.essentials.data.PlayerDataHandler;
import io.github.juanmuscaria.essentials.data.PluginConfig;
import io.github.juanmuscaria.essentials.data.TpaData;
import io.github.juanmuscaria.essentials.event.BlockCommand;
import io.github.juanmuscaria.essentials.event.BlockTab;
import io.github.juanmuscaria.essentials.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class JMEssentials extends JavaPlugin {
    public static Cache<String, TpaData> tpaCache = new Cache<>(120000L, new Function<TpaData>() {
        @Override
        public boolean run(TpaData value) {
            //Player p1 = Bukkit.getPlayer(value.getP1());
            //Player p2 = Bukkit.getPlayer(value.getP2());
            //return (p1 != null && p2 != null);
            return false;
        }
    }, new Function<TpaData>() {
        @Override
        public boolean run(TpaData value) {
            Player p1 = Bukkit.getPlayer(value.getP1());
            if (p1 != null) p1.sendMessage(ChatColor.RED + "Pedido de tpa expirou.");
            Player p2 = Bukkit.getPlayer(value.getP2());
            if (p2 != null) p2.sendMessage(ChatColor.RED + "Pedido de tpa expirou.");
            return false;
        }
    });
    private static JMEssentials instance;
    private ConsoleCommandSender consoleCommandSender = this.getServer().getConsoleSender();
    private List<IEvent> eventos = new ArrayList<>();
    private List<BukkitTask> tasks = new ArrayList<>();

    public static JMEssentials getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        Logger.CCS = consoleCommandSender; //Pega o console sender e manda para a classe logger para ela funcionar corretamente.
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Iniciando....");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        instance = this;
        new PlayerDataHandler();
        new PluginConfig(this);
        APIs.hasJMEssentials = true;
        OldConfig commandConfig = new OldConfig("commands");
        commandConfig.Reload();
        CommandRegister.register("ajuda", "jm.cmd.player.ajuda", new Ajuda(), commandConfig.Get(), this, null);
        CommandRegister.register("regras", "jm.cmd.player.regras", new Regras(), commandConfig.Get(), this, null);
        CommandRegister.register("jmcofh", "jm.cmd.player.jmcofh", new JMcofh(), commandConfig.Get(), this, null);
        CommandRegister.register("home", "jm.cmd.player.home", new Home(), commandConfig.Get(), this, null);
        CommandRegister.register("tpa", "jm.cmd.player.tpa", new Tpa(), commandConfig.Get(), this, null);
        loadEvents();
    }

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
    }

    private void loadEvents(){
        if ((!(APIs.protocolManager == null)) && io.github.juanmuscaria.core.data.PluginConfig.pluginConfig.blocktab_enable) //Verifica se o protocollib stá instalado e se está ativado para registrar o evento.
            eventos.add(new BlockTab());
        if (io.github.juanmuscaria.core.data.PluginConfig.pluginConfig.blockedcmds_enable) eventos.add(new BlockCommand()); //Registra o BlockedCmds
        //if ((!(APIs.protocolManager == null))) eventos.add(new PAServerPing()); //Sad isso não funciona no thermos, terei que fazer uma gambiarra.
    }

    private void loadTasks(){

    }
}
