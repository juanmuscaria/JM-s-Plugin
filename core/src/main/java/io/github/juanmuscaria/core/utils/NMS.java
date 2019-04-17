package io.github.juanmuscaria.core.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

public class NMS {
    /**
     * Uma função para pegar a versão do NMS do servidor!
     *
     * @return Retorna a versão no NMS.
     */
    public static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    /**
     * Uma função para te ajudar com o reflection com as classes do NMS.
     *
     * @param name Nome da classe do nms que você está tentando pegar.
     * @return Retorna a classe caso consiga achar, caso contrario retorna um valor null.
     */
    @Nullable
    public static Class<?> getNMSClass(String name) {
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
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
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
