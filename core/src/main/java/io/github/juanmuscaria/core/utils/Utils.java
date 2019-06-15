package io.github.juanmuscaria.core.utils;

import io.github.juanmuscaria.core.JMCore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;


public class Utils {
    public static void createFolder(String Name) {
        try {
            File Folder = new File(JMCore.getInstance().getDataFolder() + File.separator + Name);
            if (!Folder.exists())
                if (!Folder.mkdirs())
                    Logger.Error("Não foi possivel criar a pasta:" + Name);

        } catch (SecurityException ex) {
            Logger.Error("Não foi possivel criar a pasta:" + Name);
            ex.printStackTrace();
        }
    }

    public static boolean doesClassExist(String name) {
        try {
            Class c = Class.forName(name);
            if (c != null)
                return true;
        } catch (NoClassDefFoundError | ClassNotFoundException e) {
            Logger.Debug("Não foi possivel encontrar a class: " + ChatColor.RED + name);
        } catch (Exception e) {
            StringWriter a = new StringWriter();
            PrintWriter b = new PrintWriter(a);
            e.printStackTrace(b);
            Logger.Debug("Um erro inesperado ocorreu ao tentar encontrar a class: " + ChatColor.RED + name);
            Logger.Debug(ChatColor.RED + a.toString());
        }
        return false;
    }

    public static int getUserPermissionInteger(@NotNull String node, @NotNull Player p) {
        int nodeInt = 0;
        int len = node.split("\\.").length;
        for (PermissionAttachmentInfo perm : p.getEffectivePermissions()) {
            String pn = perm.getPermission();
            if (pn.startsWith(node + ".")) {
                int foundInt = Integer.parseInt(pn.split("\\.")[len]);
                if (foundInt > nodeInt) {
                    nodeInt = foundInt;
                }
            }
        }
        Logger.Debug(p.getName() + nodeInt);
        return nodeInt;
    }

    public static boolean validateStringForMap(String vString) {
        return vString.matches("[a-zA-Z]*");

    }

}
