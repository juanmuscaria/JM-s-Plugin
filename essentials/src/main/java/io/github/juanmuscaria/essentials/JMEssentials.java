package io.github.juanmuscaria.essentials;

import org.bukkit.plugin.java.JavaPlugin;

public class JMEssentials extends JavaPlugin {
    public static JMEssentials instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {
    }
}
