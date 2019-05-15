package io.github.juanmuscaria.core;

import io.github.juanmuscaria.core.comandos.JM;
import io.github.juanmuscaria.core.data.OldConfig;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.core.data.PluginConfig;
import io.github.juanmuscaria.core.event.BlockCommand;
import io.github.juanmuscaria.core.event.BlockTab;
import io.github.juanmuscaria.core.event.IEvent;
import io.github.juanmuscaria.core.event.PlayerDataEvents;
import io.github.juanmuscaria.core.task.OnlineTime;
import io.github.juanmuscaria.core.utils.CommandRegister;
import io.github.juanmuscaria.core.utils.Logger;
import io.github.juanmuscaria.core.utils.Utils;
import net.cubespace.Yamler.Config.InvalidConfigurationException;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JMCore extends JavaPlugin {
    private static JMCore instance;
    public volatile HashMap<String, PlayerData> playerDataHashMap = new HashMap<>();
    private ConsoleCommandSender consoleCommandSender = this.getServer().getConsoleSender();
    private List<IEvent> eventos = new ArrayList<>();
    private List<BukkitTask> tasks = new ArrayList<>();

    @Contract(pure = true) //Define o contrato desse metodo, como ele nunca muda seu return ele é pure.
    public static JMCore getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        //TODO:Refazer esse sistema.
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
        //TODO:Refazer esse sistema.
        Logger.Debug("Dando reloading no plugin.");
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
        tasks.add(new io.github.juanmuscaria.core.task.Core().runTaskTimer(this, 0L, 100L));
        if (PluginConfig.pluginConfig.ontime_eable) {
            Logger.Debug("Criando Asynchronous task ontime");
            tasks.add(new OnlineTime().runTaskTimerAsynchronously(this, 0L, 1000L));
        }
        Logger.Debug("Reloading completo.");
    }

    @Override
    public void onEnable() {
        Logger.CCS = consoleCommandSender; //Pega o console sender e manda para a classe logger para ela funcionar corretamente.
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Iniciando....");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        instance = this; //Serve para pegar a instancia do plugin em um contexto estático ou fora dele.
        Utils.createFolder("PlayerData"); //Cria a pasta que irá armazenar os dados dos jogadores.
        new PluginConfig(this); //Inicia a api de configurações.

        OldConfig commandConfig = new OldConfig("commands"); //Antigo sistema de configurações, é usado pelo command register.
        commandConfig.Reload();
        CommandRegister.register("jm", "jm.cmd.player.jm", new JM(), commandConfig.Get(), this, null); //Registra o comando /jm.
        APIs.loadAPIs();

        eventos.add(new PlayerDataEvents()); //Evento essencial para manipular os dados dos jogadores.
        if ((!(APIs.protocolManager == null)) && PluginConfig.pluginConfig.blocktab_enable) //Verifica se o protocollib stá instalado e se está ativado para registrar o evento.
            eventos.add(new BlockTab());
        if (PluginConfig.pluginConfig.blockedcmds_enable) eventos.add(new BlockCommand()); //Registra o BlockedCmds


        for (Player p : getServer().getOnlinePlayers()) { //Verifica se tem jogadores online, assim evita problema com o player data.
            PlayerData data = new PlayerData(p.getName().toLowerCase(), p);
            Logger.Warn(ChatColor.RED + "AVISO:Colocando uma instancia de um player data na inicialização, isso ainda não está 100% implementado e poderá causar bugs.");
            JMCore.getInstance().playerDataHashMap.put(p.getName().toLowerCase(), data);
        }
        new Testes();


    }

    private void loadEvents() {

    }

    private void loadTaks() {
        tasks.add(new io.github.juanmuscaria.core.task.Core().runTaskTimer(this, 0L, 100L)); //TODO:Remover isso?
        if (PluginConfig.pluginConfig.ontime_eable) {
            Logger.Debug("Criando Asynchronous task ontime");
            tasks.add(new OnlineTime().runTaskTimerAsynchronously(this, 0L, 1000L)); //Cria a task do ontime
        }
    }
}
