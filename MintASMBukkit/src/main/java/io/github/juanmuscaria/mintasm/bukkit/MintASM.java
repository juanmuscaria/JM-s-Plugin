package io.github.juanmuscaria.mintasm.bukkit;

import io.github.juanmuscaria.mintasm.bukkit.event.IEvent;
import io.github.juanmuscaria.mintasm.bukkit.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class MintASM extends JavaPlugin {
    public static MintASM MintASM;
    private ConsoleCommandSender consoleCommandSender = this.getServer().getConsoleSender(); //Pega o console, usado no logger.
    private List<IEvent> eventos = new ArrayList<>(); //Arraylist dos eventos.
    private List<BukkitTask> tasks = new ArrayList<>(); //Arraylist das tasks.

    @Override
    public void onEnable(){
        Logger.CCS = consoleCommandSender; //Pega o console sender e manda para a classe logger para ela funcionar corretamente.
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Iniciando....");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        MintASM = this;
        new PluginConfig(this);
    }

    @Override
    public void onDisable(){
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Finalizando...Bye.");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.Debug("Finalizando os eventos.");
        eventos.forEach((E) -> { if (!(E == null)) E.disable(); });
        tasks.forEach((T) -> { if (!(T == null)) T.cancel(); });
    }
}
