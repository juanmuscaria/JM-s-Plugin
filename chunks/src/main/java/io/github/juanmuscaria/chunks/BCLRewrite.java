package io.github.juanmuscaria.chunks;

import io.github.juanmuscaria.chunks.data.ChunkloaderDataHandler;
import io.github.juanmuscaria.chunks.data.PluginConfig;
import io.github.juanmuscaria.chunks.utils.Logger;
import io.github.juanmuscaria.core.event.IEvent;
import io.github.juanmuscaria.core.utils.Utils;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class BCLRewrite extends JavaPlugin {
    private static BCLRewrite instanse;
    private static boolean isEnabled = false;
    private List<IEvent> eventos = new ArrayList<>(); //Arraylist dos eventos.
    private List<BukkitTask> tasks = new ArrayList<>(); //Arraylist das tasks.
    public static ChunkloaderDataHandler chunkloaderDataHandler;
    @Override
    public void onEnable() {
        Logger.CCS = this.getServer().getConsoleSender();
        if (!Utils.doesClassExist("net.kaikk.bcl.forgelib.BCLForgeLib")) {
            Logger.Fatal("Esse plugin precisa do BCLForgeLib para funcionar!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Iniciando....");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        isEnabled = true;
        instanse = this;
    }

    @Override
    public void onDisable(){
        if (!isEnabled)return;
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Finalizando...Bye.");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.Debug("Finalizando os eventos.");
        eventos.forEach((E) -> { if (!(E == null)) E.disable(); });
        tasks.forEach((T) -> { if (!(T == null)) T.cancel(); });
        chunkloaderDataHandler.disable();

    }

    public void onReload(){
        Logger.Debug("Iniciando reloading.");
        eventos.forEach((E) -> {
            if (!(E == null)) E.reload();
        });
        try {
            PluginConfig.pluginConfig.reload();
        } catch (InvalidConfigurationException e) {
            Logger.Error("Erro ao recarregar o arquivo de configuração");
            e.printStackTrace();
        }
        tasks.forEach((T) -> {
            if (!(T == null)) T.cancel();
        });
        tasks.clear();
        loadTasks();
        Logger.Debug("Reloading completo.");
    }

    private void loadEvents() {
    }

    private void loadTasks() {
    }

    @Contract(pure = true)
    public static BCLRewrite getInstanse() {
        return instanse;
    }

}
