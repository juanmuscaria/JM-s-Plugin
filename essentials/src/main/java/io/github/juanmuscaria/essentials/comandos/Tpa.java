package io.github.juanmuscaria.essentials.comandos;

import io.github.juanmuscaria.core.utils.jsonchat.Colors;
import io.github.juanmuscaria.core.utils.jsonchat.Event;
import io.github.juanmuscaria.core.utils.jsonchat.JSONChat;
import io.github.juanmuscaria.core.utils.jsonchat.Text;
import io.github.juanmuscaria.essentials.JMEssentials;
import io.github.juanmuscaria.essentials.data.TpaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpa implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "Somente um jogador pode executar esse comando.");
            return true;
        }

        Player p = (Player) commandSender;
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Argumento invalido.");
            return true;
        }

        if (args[0].equalsIgnoreCase("aceitar") || args[0].equalsIgnoreCase("accept")){
            if (JMEssentials.tpaCache.containsKey(p.getName())){
                TpaData tpa = JMEssentials.tpaCache.get(p.getName());
                JMEssentials.tpaCache.invalidate(p.getName());
                if (tpa.doTeleport())p.sendMessage(ChatColor.GREEN + "Pedido de teleporte aceito!");
                else p.sendMessage(ChatColor.RED + "Você não tem nenhum pedido pendente!");
            }
            else p.sendMessage(ChatColor.RED + "Você não tem nenhum pedido pendente!");
            return true;
        }

        if (args[0].equalsIgnoreCase("recusar") || args[0].equalsIgnoreCase("deny")){
            if (JMEssentials.tpaCache.containsKey(p.getName())){
                JMEssentials.tpaCache.invalidate(p.getName());
                p.sendMessage(ChatColor.RED + "Pedido de teleporte reusado!");
            }
            else p.sendMessage(ChatColor.RED + "Você não tem nenhum pedido pendente!");
            return true;
        }

        Player p2 = Bukkit.getPlayer(args[0]);
        if (p2 == null){
            p.sendMessage(ChatColor.RED + "Jogador não encontrado.");
            return true;
        }
        TpaData a = new TpaData(p.getName(),p2.getName());
        JMEssentials.tpaCache.put(p2.getName(),a);
        p.sendMessage(ChatColor.GREEN + "Pedido enviado com sucesso.");
        new JSONChat().addText(new Text("Você recebeu um pedido de tpa do(a):" + p.getName()).setBold(true).setColor(Colors.Yellow)).sendTo(p2);
        new JSONChat().addText(new Text(">").setBold(true).setColor(Colors.Cyan)).addText(new Text("Aceitar").setColor(Colors.BrightGreen).setClickEvent((new Event("run_command", "/"+label+" aceitar").toJson()))).sendTo(p2);
        new JSONChat().addText(new Text(">").setBold(true).setColor(Colors.Cyan)).addText(new Text("Recusar").setColor(Colors.Red).setClickEvent((new Event("run_command", "/"+label+" recusar").toJson()))).sendTo(p2);
        return true;
    }
}
