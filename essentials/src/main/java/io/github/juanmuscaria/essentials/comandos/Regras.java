package io.github.juanmuscaria.essentials.comandos;

import io.github.juanmuscaria.essentials.JMEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Regras implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String head = JMEssentials.globalPluginConfig.Get().getString("regras.head").replace('&', '§');
        List<String> lines = JMEssentials.globalPluginConfig.Get().getStringList("regras.lines");
        StringBuilder sb = new StringBuilder();
        sb.append(head).append("\n");
        for (String s : lines) sb.append(s.replace('&', '§')).append("\n");
        sender.sendMessage(sb.toString());
        return true;
    }
}
