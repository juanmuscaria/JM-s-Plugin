package io.github.juanmuscaria.core.utils.nms;

import io.github.juanmuscaria.core.APIs;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class NMSUtil {
    public static NMSUtil nmsUtil;

    public static void init() {
        if (APIs.isThermos) {
            nmsUtil = new NMSUtil_v1_7_R4();
            Logger.Debug("NMS > Usando modo de compatibilidade com o thermos");
        } else {
            nmsUtil = new NMSUtil_Reflection();
            Logger.Debug("NMS > Usando modo de reflection");
        }
    }

    /**
     * Uma função para pegar a versão do nms do servidor!
     *
     * @return Retorna a versão no nms.
     */
    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    }

    /**
     * Uma função para te ajudar com o reflection com as classes do nms.
     *
     * @param name Nome da classe do nms que você está tentando pegar.
     * @return Retorna a classe caso consiga achar, caso contrario retorna um valor null.
     */
    @Nullable
    public static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Uma função para te ajudar com o reflection com as classes do CraftBukkit.
     *
     * @param name Nome da classe do nms que você está tentando pegar.
     * @return Retorna a classe caso consiga achar, caso contrario retorna um valor null.
     */
    @Nullable
    public static Class<?> getOBClass(String name) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    public abstract ItemStack deserializeItemStack(String data);

    @NotNull
    public abstract String serializeItemStack(ItemStack item);
}
