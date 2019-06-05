package io.github.juanmuscaria.essentials.comandos;

import io.github.juanmuscaria.core.comandos.SubCommand;
import io.github.juanmuscaria.core.utils.jsonchat.Colors;
import io.github.juanmuscaria.core.utils.jsonchat.Event;
import io.github.juanmuscaria.core.utils.jsonchat.JSONChat;
import io.github.juanmuscaria.core.utils.jsonchat.Text;
import io.github.juanmuscaria.essentials.comandos.home.Del;
import io.github.juanmuscaria.essentials.comandos.home.Homes;
import io.github.juanmuscaria.essentials.comandos.home.Set;
import io.github.juanmuscaria.essentials.comandos.home.Setpublic;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class Home implements CommandExecutor {
    private List<SubCommand> subCommands = new ArrayList<>();

    public Home() {
        subCommands.add(new Homes(this, "homes"));
        subCommands.add(new Set(this, "set"));
        subCommands.add(new Setpublic(this, "setpublic"));
        subCommands.add(new Del(this, "set"));
        subCommands.add(new Set(this, "tp"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if ((args.length == 0) || !(Objects.equals(args[0], "homes")))
                sender.sendMessage(ChatColor.RED + "Somente o sub comando homes é disponivel no console!.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0) {
            sendHelpMenssage(p, label);
            return true;
        }
        AtomicBoolean showhelp = new AtomicBoolean(true);
        subCommands.forEach(subCommand -> {
            if (subCommand.valdateCommand(args[0])) {
                showhelp.set(false);
                if (subCommand.validateArguments(args)) subCommand.onCommand(sender, command, label, args);
                else subCommand.sendHelp(p, label);
            }
        });
        if (showhelp.get())sendHelpMenssage(p, label);
        return true;
    }

    private void sendHelpMenssage(Player p,String command){

        Text lines = new Text("------", Colors.Gray).setBold(true);
        Text name = new Text("HomeData", Colors.Blue).setHoverEvent(
                new Event("show_text","Módulo homes do JMEssentials by:juanmuscaria.").toJson());
        new JSONChat().addText(lines).addText(name).addText(lines).sendTo(p);
        new JSONChat().addText(new Text("Lista de comandos [Opcional] <Obrigatório>:",Colors.White)).sendTo(p);
        new JSONChat().addText(new Text("  >/"+command+" homes [jogador]",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" homes [jogador]").toJson())).sendTo(p);
        new JSONChat().addText(new Text("Lista as suas homes públicas e privadas. Caso for dado o nome de um jogador irá listar as homes publicas do mesmo.",Colors.Gray)).sendTo(p);
        new JSONChat().addText(new Text("  >/"+command+" set <Nome>",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" set <Nome>").toJson())).sendTo(p);
        new JSONChat().addText(new Text("Define uma home no local que você está",Colors.Gray)).sendTo(p);
        new JSONChat().addText(new Text("  >/"+command+" tp <Nome> [jogador]",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" tp <Nome> [jogador]").toJson())).sendTo(p);
        new JSONChat().addText(new Text("Teleporta para uma home.",Colors.Gray)).sendTo(p);
        new JSONChat().addText(new Text("  >/"+command+" del <Nome> [jogador]",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" del <Nome> [jogador]").toJson())).sendTo(p);
        new JSONChat().addText(new Text("Deleta uma home.",Colors.Gray)).sendTo(p);
        new JSONChat().addText(new Text("  >/"+command+" setpublic <Nome>",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" setpublic <Nome>").toJson())).sendTo(p);
        new JSONChat().addText(new Text("Permite deixar uma home publica ou privada.",Colors.Gray)).sendTo(p);
        new JSONChat().addText(lines).addText(name).addText(lines).sendTo(p);
    }

}