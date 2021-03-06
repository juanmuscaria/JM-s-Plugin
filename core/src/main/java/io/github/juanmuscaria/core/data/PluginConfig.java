package io.github.juanmuscaria.core.data;

import io.github.juanmuscaria.core.utils.Logger;
import net.cubespace.Yamler.Config.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;


public class PluginConfig extends YamlConfig {
    public static PluginConfig pluginConfig;
    @Comment("Serve para ativar o modo de depuração, bom para achar bugs.")
    public Boolean debug = false;

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
