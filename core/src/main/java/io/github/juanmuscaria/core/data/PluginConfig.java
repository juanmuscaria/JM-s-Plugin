package io.github.juanmuscaria.core.data;

import io.github.juanmuscaria.core.utils.Logger;
import net.cubespace.Yamler.Config.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class PluginConfig extends YamlConfig {
    public static PluginConfig pluginConfig;
    @Comment("Serve para ativar o modo de depuração, bom para achar bugs.")
    public Boolean debug = false;
    @Comment("Defina se o módulo blocktab está ativo, ele irá bloquear o tab complet para jogadores sem a permissão 'jm.admin.bypass'.")
    public Boolean blocktab_enable = false;

    //Opções do arquivo de configuração
    @Comment("Defina se o módulo de comandos bloqueados está ativo.")
    public Boolean blockedcmds_enable = false;
    @Comment("Lista de comandos bloqueados no servidor, um jogador se a permissão 'jm.admin.bypass' não poderá usar esses comandos.")
    public List<String> blockedcmds_cmdlist = Arrays.asList("/pex", "/lp", "/manuadd", "/stop",
            "/restart", "//calculate", "/ver", "/version",
            "/about", "/icanhasbukkit", "/bukkit", "//calc",
            "/gc", "/op", "/give", "/minecraft",
            "//eval", "//solve", "//evaluate");
    @Comments({"Mensagem que irá aparecer quando o jogador executar o comando. Recomendo botar a mesma mensagem de comando desconhecido do servidor.",
            "Tem suporte a cores."})
    public String blockedcmds_msg = "Comando desconhecido!";
    @Comment("Ativa o módulo online time prizes (Beta), ele executa 1 comando a cada 1 hr online.")
    public Boolean ontime_eable = false;
    @Comments({"Mensagem que aparece para o jogador quando ele ganha o premio.",
            "Tem suporte a cores"})
    public String ontime_msg = "&7[&9OnTime&7]&3Parabéns, você ficou 1 hora online e ganhou &a[OnTime key]";
    @Comments({"Comando que executa para o jogador quando ele ganha o premio (não use /).",
            "placehole %player% = nick do jogador que ganhou o premio."})
    public String ontime_command = "crate key %player% OnTime";

    //Carrega a configuração do plugin.
    public PluginConfig(@NotNull Plugin plugin) {
        //Define o header da config.
        //TODO:Trocar para uma list de strings.
        CONFIG_HEADER = new String[]{"########################################################################################################################\n#                                      Arquivo de configuração do JM-Core.                                             #\n#                   Caso precisar de ajuda para configurar o plugin é só abrir uma issue no github:                    #\n#                                   https://github.com/juanmuscaria/JM-s-Plugin                                        #\n########################################################################################################################"};
        CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml"); //Define o arquivo de configuração
        CONFIG_MODE = ConfigMode.PATH_BY_UNDERSCORE; //Define que o _ seja o path da config (path_to_config = path.to.config).
        pluginConfig = this;
        try {
            this.init(); //Usa o proprio sistema da api para carregar as coisas
        } catch (InvalidConfigurationException e) {
            Logger.Error("Não foi possivel carregar o arquivo de configuração!");
            e.printStackTrace();
        }
    }

    @Override
    public void update(ConfigSection config) {
        //Pore enquanto não irei fazer um conversor de configurações antigas.
    }
}
