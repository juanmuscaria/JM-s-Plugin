package io.github.juanmuscaria.core.comandos;

import io.github.juanmuscaria.core.JMCore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Regras implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String head = JMCore.GlobalPluginConfig.Get().getString("regras.head").replace('&', '§');
        List<String> lines = JMCore.GlobalPluginConfig.Get().getStringList("regras.lines");
        StringBuilder sb = new StringBuilder();
        sb.append(head).append("\n");
        for (String s : lines) sb.append(s.replace('&', '§')).append("\n");
        sender.sendMessage(sb.toString());
        return true;
    }
}
