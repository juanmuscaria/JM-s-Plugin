package io.github.juanmuscaria.core.comandos;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class JMcofh implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //Verifica se é um jogador que está a executar o comando.
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Você precisa ser um player para executar esse comando!");
            return true;
        }
        //Verifica se já tem permissão de executar o comando pra evitar problemas.
        if (sender.hasPermission("cofh.command.CommandHandler")) {
            sender.sendMessage(ChatColor.RED + "Você já tem a permissão para usar o /cofh!");
        } else {
            //Aquela famosa gambiarra.
            Player player = (Player) sender;
            try {
                Logger.Debug("O jogador " + player.getName() + " executou o /jmcofh");
                player.setOp(true);
                player.addAttachment(JMCore.getInstance(), "cofh.*", true);
                player.chat("/cofh friend gui");
                player.addAttachment(JMCore.getInstance(), "cofh.*", false);
                player.setOp(false);
            } catch (Exception ex) {
                sender.sendMessage(ChatColor.RED + "Um erro fatal aconteceu! Contacte alguem da staff.");
                List<String> error = Arrays.asList("Removendo permissão temporaria do player: " + player.getName(), "Erro: " + ex.getMessage());
                Logger.Error(error);
                ex.printStackTrace();
                player.setOp(false);
                player.addAttachment(JMCore.getInstance(), "cofh.*", false);
            } finally {
                //Só pra garantir que removeu o op e a permission.
                player.setOp(false);
                player.addAttachment(JMCore.getInstance(), "cofh.*", false);
            }
        }
        return true;
    }

}
