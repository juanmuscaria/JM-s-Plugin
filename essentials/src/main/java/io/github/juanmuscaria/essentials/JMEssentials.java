package io.github.juanmuscaria.essentials;


import io.github.juanmuscaria.core.utils.CommandRegister;
import io.github.juanmuscaria.essentials.comandos.Ajuda;
import io.github.juanmuscaria.essentials.data.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class JMEssentials extends JavaPlugin {
    public static Config globalPluginConfig;
    private static JMEssentials instance;

    public static JMEssentials getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        globalPluginConfig = new Config("config");
        globalPluginConfig.Reload();
        Config commandConfig = new Config("commands");
        commandConfig.Reload();
        CommandRegister.register("jm", "jm.cmd.player.jm", new Ajuda(), commandConfig.Get(), this, null);
    }

    @Override
    public void onDisable() {
    }
}
