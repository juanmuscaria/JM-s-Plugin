package io.github.juanmuscaria.essentials.comandos;

import io.github.juanmuscaria.essentials.data.PluginConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Ajuda implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : PluginConfig.pluginConfig.ajuda) sb.append(s.replace('&', '§')).append("\n");
        sender.sendMessage(sb.toString());
        return true;
    }
}
