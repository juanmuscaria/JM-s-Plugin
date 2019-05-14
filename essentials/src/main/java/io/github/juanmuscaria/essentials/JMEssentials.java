package io.github.juanmuscaria.essentials;


import io.github.juanmuscaria.core.event.IEvent;
import io.github.juanmuscaria.core.utils.CommandRegister;
import io.github.juanmuscaria.essentials.comandos.Ajuda;
import io.github.juanmuscaria.essentials.comandos.JMcofh;
import io.github.juanmuscaria.essentials.comandos.Regras;
import io.github.juanmuscaria.essentials.data.OldConfig;
import io.github.juanmuscaria.essentials.data.PluginConfig;
import io.github.juanmuscaria.essentials.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class JMEssentials extends JavaPlugin {
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
        new PluginConfig(this);
        OldConfig commandConfig = new OldConfig("commands");
        commandConfig.Reload();
        CommandRegister.register("ajuda", "jm.cmd.player.ajuda", new Ajuda(), commandConfig.Get(), this, null);
        CommandRegister.register("regras", "jm.cmd.player.regras", new Regras(), commandConfig.Get(), this, null);
        CommandRegister.register("jmcofh", "jm.cmd.player.jmcofh", new JMcofh(), commandConfig.Get(), this, null);
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
}
