package io.github.juanmuscaria.chunks.data;

import io.github.juanmuscaria.chunks.BCLRewrite;
import io.github.juanmuscaria.core.data.IReload;
import io.github.juanmuscaria.core.utils.Cache;
import io.github.juanmuscaria.core.utils.Function;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class ChunkloaderDataHandler implements IReload {
    private File folder;
    public Cache<UUID,PlayerData> dataCache = new Cache<>(120000L,
            new Function<PlayerData>() {@Override public boolean run(PlayerData value) {
                Player p = Bukkit.getPlayer(value.getPlayer());
                AtomicBoolean hasAlwaysOn = new AtomicBoolean(false);
                value.getActiveChunkloaers().forEach( (k,v) ->{
                    if (v.isAlwaysOn())hasAlwaysOn.set(true);
                });
                return !hasAlwaysOn.get() && (p == null);
            }},
            new Function<PlayerData>() {@Override public boolean run(PlayerData value) {
                savePlayerData(value);
                return false;
            }
            });
    public ChunkloaderDataHandler(){
        File Folder = new File(BCLRewrite.getInstanse().getDataFolder() + File.separator + "ChunkloaderData");
        this.folder = Folder;
        if (!Folder.exists())
            if (!Folder.mkdirs())
                Logger.Error("Não foi possivel criar a pasta: ChunkloaderData");

        try (Stream<Path> paths = Files.walk(Paths.get(folder.getAbsolutePath()))) {
            paths.forEach(F ->{
                PlayerData data = loadPlayerData(F.toFile().getName());
                if (data != null)dataCache.put(UUID.fromString(F.toFile().getName()),data);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void reload() {
        //TODO:Fazer uma lógica de reload.
    }

    @Override
    public void disable() {
        dataCache.invalidateAll();
    }

    @Override
    public void save() {
        Map<UUID,PlayerData> internal = dataCache.getInternal();
        internal.forEach((K,V) -> {
            savePlayerData(V);
        });
    }

    private void savePlayerData(PlayerData data){
        try {
            FileOutputStream fos = new FileOutputStream(new File( BCLRewrite.getInstanse().getDataFolder() + File.separator + "ChunkloaderData", data.getPlayer().toString()));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            fos.close();
        } catch (IOException e) {
            Logger.Error("Falha ao salvar chunkloaderdta de: " + Bukkit.getOfflinePlayer(data.getPlayer()).getName());
            e.printStackTrace();
        }
    }

    @Nullable
    private PlayerData loadPlayerData(String uuid){
        PlayerData data = null;
        try {
            FileInputStream fis = new FileInputStream(new File(BCLRewrite.getInstanse().getDataFolder() + File.separator + "ChunkloaderData", uuid));
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = (PlayerData) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            Logger.Error("Falha ao carregar playerdata:" + uuid);
            e.printStackTrace();
        }
        return data;
    }
}
