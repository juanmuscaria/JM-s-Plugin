package io.github.juanmuscaria.core.comandos;


import io.github.juanmuscaria.core.JMCore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

//TODO:Configurar modulos do plugin por esse comando
public class JM implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (args[0].toLowerCase()) {
            case "help": {
                sender.sendMessage("a");
                break;
            }
            case "gui": {
                break;
            }
            case "ec": {
                break;
            }
            case "shop": {
                break;
            }
            case "reload": {
                if (sender.hasPermission("jm.admin.reload")) {
                    sender.sendMessage(ChatColor.RED + "Recarregando o plugin. Esse comando ainda está em testes e poderá causar erros.");
                    JMCore.getInstance().onReload();
                } else {
                    sender.sendMessage(ChatColor.RED + "Você não tem permissão para executar esse comando.");
                }
                break;
            }
            default: {
                break;
            }
        }
        return true;

    }
}