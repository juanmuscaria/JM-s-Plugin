package io.github.juanmuscaria.essentials;

import io.github.juanmuscaria.core.data.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class JMEssentials extends JavaPlugin {
    public static Config globalPluginConfig;
    private static JMEssentials instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
    }

    public static JMEssentials getInstance() {
        return instance;
    }
}
