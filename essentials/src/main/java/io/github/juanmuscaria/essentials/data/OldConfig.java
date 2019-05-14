package io.github.juanmuscaria.essentials.data;

import io.github.juanmuscaria.core.utils.Logger;
import io.github.juanmuscaria.essentials.JMEssentials;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

//Usado somente pelo CommandRegister.
public class OldConfig {
    private String config;
    private File File = null;
    private YamlConfiguration Config = null;

    public OldConfig(String pconfig) {
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

    public File getFile() {
        return File;
    }

    @SuppressWarnings("ConstantConditions")
    public void Reload() {
        if (File == null) {
            File = new File(JMEssentials.getInstance().getDataFolder(), config + ".yml");
        }
        Config = YamlConfiguration.loadConfiguration(File);

        Reader defConfigStream = new InputStreamReader(JMEssentials.getInstance().getResource(config + ".yml"), StandardCharsets.UTF_8);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            Config.setDefaults(defConfig);
        }
        save();
    }

    public void save() {
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
            File = new File(JMEssentials.getInstance().getDataFolder(), config + ".yml");
        }
        if (!File.exists()) {
            JMEssentials.getInstance().saveResource(config + ".yml", false);
        }
    }
}
