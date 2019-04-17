package io.github.juanmuscaria.core.data;


import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.utils.Logger;
import io.github.juanmuscaria.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

//TODO:(re)Fazer o sistema de player data (incompleto)
public class PlayerData implements IReload {
    private Player PlayerObj;
    private OfflinePlayer OfflinePlayerObj;
    private String Name;
    private String OfflineName;
    private Boolean Offline;
    private YamlConfiguration Data;
    private File DataFile;
    private Boolean Login;
    private LocalTime Time;

    @SuppressWarnings("deprecation")
    public PlayerData(String p, Boolean off, Boolean login) {
        Logger.Debug("Criando uma nova instancia do Player data para o player: " + ChatColor.GREEN + p);
        this.Login = login;
        if (off == null) this.Offline = false;
        else this.Offline = off;
        if (Bukkit.getPlayer(p) == null) Offline = true;
        if (!Offline) {
            this.PlayerObj = Bukkit.getPlayer(p);
            this.Name = this.PlayerObj.getName();
            this.OfflineName = this.PlayerObj.getName().toLowerCase();
            this.OfflinePlayerObj = Bukkit.getOfflinePlayer(p);
        } else {
            this.PlayerObj = null;
            this.OfflineName = p.toLowerCase();
            this.OfflinePlayerObj = Bukkit.getOfflinePlayer(p);
            this.Name = this.OfflinePlayerObj.getName();

        }
        this.Reload();
        if (!this.Offline && this.Login) {
            if (this.Data.getString("Player.FirstLogin") == null) {
                this.Data.set("Player.FirstLogin", LocalTime.now().toString());
                this.Data.set("Player.Login", LocalTime.now().toString());
                this.Data.set("Player.isOp", false);
            }
        }
        this.Time = LocalTime.now();
        Logger.Debug("Instancia criada!");
    }

    public void Save() {
        Logger.Debug("Salvando Player data do player: " + ChatColor.GREEN + Name);
        if (this.Data == null || this.DataFile == null) {
            return;
        }
        try {
            this.Data.save(this.DataFile);
        } catch (IOException ex) {
            Logger.Error("Não foi possivel salvar o PlayerData: " + this.DataFile.getPath() + ",do jogador:" + this.Name);
            ex.printStackTrace();
        }
    }

    public void Disable() {
        if (!this.Offline && this.Login) {
            this.Data.set("Player.Logoff", LocalTime.now().toString());
            Vector position = PlayerObj.getLocation().toVector();
            this.Data.set("Player.LogoffPosition", position);
        }
        Logger.Debug("Deletando uma instancia do Player data do player: " + ChatColor.GREEN + Name);
        this.Save();
    }

    public void Reload() {
        Utils.CreateFolder("PlayerData");
        if (this.DataFile == null) {
            this.DataFile = new File(JMCore.getInstance().getDataFolder() + File.separator + "PlayerData", this.OfflineName + ".yml");
        }
        this.Data = YamlConfiguration.loadConfiguration(DataFile);
        this.Data.set("Player.name", Name);
        this.Data.set("Player.offlineName", OfflineName);
        if (!Offline) {
            this.Data.set("Player.uuid", PlayerObj.getUniqueId().toString());
            this.Data.set("Player.ip", PlayerObj.getAddress().toString());
        }
        this.Save();
    }

    public boolean IsOfflineData() {
        return this.Offline;
    }

    public YamlConfiguration getData() {
        return this.Data;
    }

    public Player getPlayerObj() {
        return this.PlayerObj;
    }

    public OfflinePlayer getOfflinePlayerObj() {
        return this.OfflinePlayerObj;
    }

    public LocalTime getTime() {
        return this.Time;
    }

    public void setTime(LocalTime t) {
        this.Time = t;
    }

}
