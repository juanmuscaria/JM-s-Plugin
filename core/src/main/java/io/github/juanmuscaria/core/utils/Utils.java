package io.github.juanmuscaria.core.utils;

import io.github.juanmuscaria.core.JMCore;
import org.bukkit.ChatColor;

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

}
