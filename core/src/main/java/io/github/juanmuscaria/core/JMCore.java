package io.github.juanmuscaria.core;

import io.github.juanmuscaria.core.comandos.JM;
import io.github.juanmuscaria.core.data.OldConfig;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.core.data.PluginConfig;
import io.github.juanmuscaria.core.event.IEvent;
import io.github.juanmuscaria.core.event.PlayerDataEvents;
import io.github.juanmuscaria.core.utils.CommandRegister;
import io.github.juanmuscaria.core.utils.Logger;
import io.github.juanmuscaria.core.utils.Utils;
import io.github.juanmuscaria.core.utils.nms.NMSUtil;
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
    private static JMCore instance; //A instancia do plugin, ela pode ser estatica já que só terá 1 instancia por vez.
    public volatile HashMap<String, PlayerData> playerDataHashMap = new HashMap<>(); //Hashmap que guarda os dados dos jogadores, é bom mudar isso no futuro pois causa perda de dados caso um addon for removido.
    private ConsoleCommandSender consoleCommandSender = this.getServer().getConsoleSender(); //Pega o console, usado no logger.
    private List<IEvent> eventos = new ArrayList<>(); //Arraylist dos eventos.
    private List<BukkitTask> tasks = new ArrayList<>(); //Arraylist das tasks.

    @Contract(pure = true) //Define o contrato desse metodo, como ele nunca muda seu return ele é pure.
    public static JMCore getInstance() { //Um getter da instancia do plugin, as pessoas falam que é mais seguro fazer um getter do que deixar o field acessivel por qualquer um.
        return instance;
    }

    @Override //Lógica executada quando o plugin é desabilitado, melhorar isso caso for usado o plugman para fazer isso.
    public void onDisable() {
        //TODO:Refazer esse sistema, fazer um command unregister para resolver conflitos.
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.cLog(ChatColor.GREEN + "Finalizando...Bye.");
        Logger.cLog(ChatColor.GOLD + "---------------------------------------");
        Logger.Debug("Finalizando os eventos.");
        eventos.forEach((E) -> { if (!(E == null)) E.disable(); });
        tasks.forEach((T) -> { if (!(T == null)) T.cancel(); });
        Logger.Debug("Finalizando o player data.");
        playerDataHashMap.forEach((a, b) -> b.disable());

    }

    public void onReload() { //Lógica de reload, melhor que usar o reload via plugman que pode quebrar tudo.
        //TODO:Refazer esse sistema.
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
        commandConfig.Reload(); //(Re)carrega o arquivo de configurações, nesse caso o commands.yml
        CommandRegister.register("jm", "jm.cmd.player.jm", new JM(), commandConfig.Get(), this, null); //Registra o comando /jm.
        APIs.loadAPIs(); //Inicia os hooks e as apis, por ser estático ele precisa ser iniciado por um method call.
        NMSUtil.init(); //Inicia o NMSUtil, por ele ser static e ele usar instancias ele precisa ser iniciado por um method call.

        loadEvents(); //Carrega os eventos.
        loadTasks(); //Carrega as tasks

        for (Player p : getServer().getOnlinePlayers()) { //Verifica se tem jogadores online, assim evita problema com o player data.
            PlayerData data = new PlayerData(p.getName().toLowerCase(), p);
            Logger.Warn(ChatColor.RED + "AVISO:Colocando uma instancia de um player data na inicialização, isso ainda não está 100% implementado e poderá causar bugs.");
            JMCore.getInstance().playerDataHashMap.put(p.getName().toLowerCase(), data);
        }
        new Testes();


    }

    private void loadEvents() {
        eventos.add(new PlayerDataEvents()); //Evento essencial para manipular os dados dos jogadores.
    }

    private void loadTasks() {

    }
}
