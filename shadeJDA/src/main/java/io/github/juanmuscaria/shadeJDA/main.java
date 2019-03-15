package io.github.juanmuscaria.shadeJDA;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    public ConsoleCommandSender console = this.getServer().getConsoleSender();
    public void cLog(String text) { console.sendMessage(ChatColor.WHITE + "[" + ChatColor.AQUA + "JDA" + ChatColor.WHITE + "] "  + text); }
    public static String VersionJDA = "3.8.3_460";
    @Override
    public void onEnable() {
        cLog("Current JDA version:" + VersionJDA);

    }

    @Override
    public void onDisable() { }

}
