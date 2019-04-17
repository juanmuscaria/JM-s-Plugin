package io.github.juanmuscaria.core.data;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class Config {
    private String config;
    private File File = null;
    private YamlConfiguration Config = null;

    public Config(String pconfig) {
        config = pconfig;
        saveDefaultConfig();
        Reload();
    }

    public YamlConfiguration Get() {
        if (Config == null) {
            Reload();
        }
        return Config;

    }

    public File GetFile() {
        return File;
    }

    @SuppressWarnings("ConstantConditions")
    public void Reload() {
        if (File == null) {
            File = new File(JMCore.getInstance().getDataFolder(), config + ".yml");
        }
        Config = YamlConfiguration.loadConfiguration(File);

        Reader defConfigStream = new InputStreamReader(JMCore.getInstance().getResource(config + ".yml"), StandardCharsets.UTF_8);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            Config.setDefaults(defConfig);
        }
        Save();
    }

    public void Save() {
        if (Config == null || File == null) {
            return;
        }
        try {
            Config.save(File);
        } catch (IOException ex) {
            Logger.Error("Não foi possivel salvar o arquivo de configuração:" + File.getPath());
            ex.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (File == null) {
            File = new File(JMCore.getInstance().getDataFolder(), config + ".yml");
        }
        if (!File.exists()) {
            JMCore.getInstance().saveResource(config + ".yml", false);
        }
    }
}
