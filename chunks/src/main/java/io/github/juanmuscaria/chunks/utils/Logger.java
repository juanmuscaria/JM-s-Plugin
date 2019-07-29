package io.github.juanmuscaria.chunks.utils;

import io.github.juanmuscaria.core.data.PluginConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Collections;
import java.util.List;

public class Logger {
    public static ConsoleCommandSender CCS;

    //Log com cores + o prefix :P
    public static void cLog(String text) {
        CCS.sendMessage(ChatColor.AQUA + "[" + ChatColor.GREEN + "JMChunks" + ChatColor.AQUA + "]" + ChatColor.WHITE + text);
    }

    //debug
    public static void Debug(String text) {
        if (PluginConfig.pluginConfig.debug)
            CCS.sendMessage(ChatColor.DARK_AQUA + "[" + ChatColor.GOLD + "JMChunks" + ChatColor.DARK_AQUA + "]" + ChatColor.WHITE + text);
    }

    //Log normal.
    public static void Log(String text) {
        CCS.sendMessage(text);
    }

    public static void Warn(String text) {
        List<String> a = Collections.singletonList(text);
        Warn(a);
    }

    public static void Warn(List<String> text) {
        cLog(ChatColor.GOLD + "AVISO:");
        text.forEach((txt -> cLog(ChatColor.GOLD + txt)));
    }

    public static void Error(String text) {
        List<String> a = Collections.singletonList(text);
        Error(a);
    }

    public static void Error(List<String> text) {
        cLog(ChatColor.GOLD + "---------------------------------------");
        cLog(ChatColor.RED + "Woops, algo de errado aconteceu @.@");
        text.forEach((txt -> cLog(ChatColor.RED + txt)));
        cLog(ChatColor.GOLD + "---------------------------------------");
    }

    public static void Fatal(String text) {
        List<String> a = Collections.singletonList(text);
        Fatal(a);
    }

    public static void Fatal(List<String> text) {
        cLog(ChatColor.GOLD + "---------------------------------------");
        cLog(ChatColor.RED + "Woops, um erro FATAL aconteceu @.@");
        text.forEach((txt -> cLog(ChatColor.RED + txt)));
        cLog(ChatColor.RED + "Recomendo fortemente que reinicie seu servidor para evitar uma avalanche de erros!! Caso o erro persistir abra uma issue no github.");
        cLog(ChatColor.GOLD + "---------------------------------------");
    }
}
