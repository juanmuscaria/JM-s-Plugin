package io.github.juanmuscaria.core.data;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class PlayerData implements IReload {
    private static List<IPlayerDataHandler> handlers = new ArrayList<>();
    private Boolean offlineData = false;
    private String playerName;
    private File dataFile;
    private LocalTime onTimeCache;
    private HashMap<String, Object> dataHashMap;
    private Boolean isDisabled = false;

    public PlayerData(String player, Player playerObj) {
        Logger.Debug("Criando uma nova instancia do PlayerData para o player: " + ChatColor.GREEN + player);
        this.playerName = player;
        if (playerObj == null) offlineData = true;
        this.dataFile = new File(JMCore.getInstance().getDataFolder() + File.separator + "PlayerData", this.playerName + ".ser");
        if (offlineData) {
            load();
            this.onTimeCache = LocalTime.now();
        } else {
            load();
            dataHashMap.put("player.ip", Bukkit.getPlayer(player).getAddress().getHostString());
            dataHashMap.put("player.uuid", Bukkit.getPlayer(player).getUniqueId());
            this.onTimeCache = LocalTime.now();
            dataHashMap.computeIfAbsent("player.firstlogin", k -> onTimeCache);

        }
        handlers.forEach((H) -> {
            if (!(H == null)) H.onEnable(this);
        });

        Logger.Debug("Instancia criada!");
    }

    public static void addHandler(IPlayerDataHandler handler) {
        handlers.add(handler);
    }

    public static void removeHandler(IPlayerDataHandler handler) {
        handlers.remove(handler);
    }

    private void load() {
        if (dataFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(dataFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.dataHashMap = (HashMap<String, Object>) ois.readObject();
                ois.close();
                fis.close();
            } catch (IOException | ClassCastException | ClassNotFoundException e) {
                Logger.Error("Falha ao carregar playerdata de: " + playerName + ". Criando uma nova.");
                e.printStackTrace();
                this.dataHashMap = new HashMap<>();
            }
        } else {
            Logger.Debug("Arquivo de playerdata não existente, criando um novo.");
            this.dataHashMap = new HashMap<>();
            this.save();
        }
    }

    public void save() {
        Logger.Debug("Salvando Player data do player: " + ChatColor.GREEN + playerName);
        try {
            handlers.forEach((H) -> {
                if (!(H == null)) H.onSave(this);
            });
            FileOutputStream fos = new FileOutputStream(this.dataFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.dataHashMap);
            oos.close();
            fos.close();
        } catch (IOException e) {
            Logger.Error("Falha ao salvar playerdata de: " + playerName);
            e.printStackTrace();
        }
    }

    public void disable() {

        handlers.forEach((H) -> {
            if (!(H == null)) H.onDisable(this);
        });
        this.save();
        this.isDisabled = true;
        Logger.Debug("Deletando uma instancia do Player data do player: " + ChatColor.GREEN + playerName);
    }

    public void reload() {
        if (dataFile.exists()) {
            try {
                FileInputStream fis = new FileInputStream(dataFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                this.dataHashMap = (HashMap<String, Object>) ois.readObject();
                ois.close();
                fis.close();
            } catch (IOException | ClassCastException | ClassNotFoundException e) {
                Logger.Error("Falha ao recarregar playerdata de: " + playerName);
                e.printStackTrace();
            }
        }
    }


    protected void finalize() {
        try {
            if (!isDisabled){
                this.disable();
                Logger.Warn("Um player data não foi finalizado corretamente e está sendo limpo pelo gc, salvando playerdata atual. Isso com certeza é um bug.");
                new  Throwable().printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isOfflineData() {
        return this.offlineData;
    }

    @Contract
    public Player getPlayer() {
        return Bukkit.getPlayer(playerName);
    }

    @SuppressWarnings("deprecation")
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(playerName);
    }

    public LocalTime getOnTimeCache() {
        return this.onTimeCache;
    }

    public void setOnTimeCache(LocalTime t) {
        this.onTimeCache = t;
    }

    public Object getValue(String key) {
        return dataHashMap.get(key);
    }

    public HashMap<String, Object> getData() {
        return dataHashMap;
    }
}
