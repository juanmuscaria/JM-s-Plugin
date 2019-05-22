package io.github.juanmuscaria.essentials.comandos;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.core.data.serializable.SerializableLocation;
import io.github.juanmuscaria.core.utils.Utils;
import io.github.juanmuscaria.core.utils.jsonchat.Colors;
import io.github.juanmuscaria.core.utils.jsonchat.Event;
import io.github.juanmuscaria.core.utils.jsonchat.JSONChat;
import io.github.juanmuscaria.core.utils.jsonchat.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Home implements CommandExecutor {
    @Override
    @SuppressWarnings("unchecked")
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && !args[0].equalsIgnoreCase("homes") && args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Você precisa ser um jogador para executar esse comando.");
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "homes": {
                if (args.length == 1) {
                    Player p = (Player) sender;
                    PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
                    HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");

                    p.sendMessage("Lista de homes:\n");
                    JSONChat msg = new JSONChat();
                    Text arrow = new Text("  >", Colors.Blue);
                    arrow.setBold(true);
                    homes.forEach((K, V) -> {
                        msg.addText(arrow);
                        Text home = new Text(K + "\n");
                        if (V.getPublic()) home.setColor(Colors.Red);
                        else home.setColor(Colors.Blue);
                        Event commandEvent = new Event("run_command", "/home tp " + K);
                        home.setClickEvent(commandEvent.toJson());
                        msg.addText(home);
                    });
                    Text end = new Text("Você pode clicar em uma home para se teleportar.", Colors.Gray);
                    msg.addText(end);
                    msg.sendTo(p);
                }
                else {
                }
                //Todo: Listar homes publicas de outros jogadores.
                break;
            }
            case "set": {
                Player p = (Player) sender;
                if (args.length < 2) {
                    p.sendMessage("Numero de argumentos invalido.");
                    sendHelpMenssage(p,label);
                    return true;
                }
                PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
                HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");
                if (Utils.getUserPermissionInteger("jm.homes",p) <= homes.size() ||!(p.hasPermission("jm.home.unlimited") || p.hasPermission("jm.admin.home"))) p.sendMessage(ChatColor.RED + "Limite de homes atingido! (" + homes.size() + "/" + Utils.getUserPermissionInteger("jm.homes",p) + ")");
                else {
                    if(Utils.validateStringForMap(args[1])){
                        SerializableLocation location = new SerializableLocation(p.getLocation());
                        io.github.juanmuscaria.essentials.data.Home home = new io.github.juanmuscaria.essentials.data.Home(location,false);
                        homes.put(args[1],home);
                        data.save();
                        p.sendMessage(ChatColor.GREEN + "Home "+args[1]+ " definida com sucesso.");
                    }
                    else p.sendMessage(ChatColor.RED + "Só é permitido caracteres de aA-zZ!");
                }
                break;
            }
            case "tp": {
                Player p = (Player) sender;
                if (args.length == 2){
                    PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
                    HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");
                    io.github.juanmuscaria.essentials.data.Home home = homes.get(args[1]);
                    if(home == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
                    else {
                        home.doTeleport(p);
                    }
                }
                if (args.length > 2){
                    String target = args[2];
                    if(Bukkit.getPlayer(target) == null){
                        PlayerData data = new PlayerData(target,null);
                        try {
                            HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");
                            io.github.juanmuscaria.essentials.data.Home home = homes.get(args[1]);
                            if(home == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
                            else {
                                if (home.getPublic() || p.hasPermission("jm.admin.home"))home.doTeleport(p);
                                else p.sendMessage(ChatColor.RED + "Home não encontrada.");
                            }
                            data.disable();
                        }
                        catch (Exception e) {p.sendMessage(ChatColor.RED + "Jogador não encontrado.");}
                    }
                    else {
                        PlayerData data = JMCore.getInstance().playerDataHashMap.get(target);
                        HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");
                        io.github.juanmuscaria.essentials.data.Home home = homes.get(args[1]);
                        if(home == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
                        else {
                            if (home.getPublic() || p.hasPermission("jm.admin.home"))home.doTeleport(p);
                            else p.sendMessage(ChatColor.RED + "Home não encontrada.");
                        }
                    }
                }
                else {
                    p.sendMessage("Numero de argumentos invalido.");
                    sendHelpMenssage(p,label);
                    return true;
                }
                break;
            }
            case "del": {
                Player p = (Player) sender;
                if (args.length == 2){
                    PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
                    HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");
                    io.github.juanmuscaria.essentials.data.Home home = homes.get(args[1]);
                    if(home == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
                    else {
                        homes.remove(args[1]);
                    }
                }
                if (args.length > 2){
                    String target = args[2];
                    if(Bukkit.getPlayer(target) == null){
                        PlayerData data = new PlayerData(target,null);
                        try {
                            HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");
                            io.github.juanmuscaria.essentials.data.Home home = homes.get(args[1]);
                            if(home == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
                            else {
                                if (p.hasPermission("jm.admin.home"))homes.remove(args[1]);
                                else p.sendMessage(ChatColor.RED + "Home não encontrada.");
                            }
                            data.disable();
                        }
                        catch (Exception e) {p.sendMessage(ChatColor.RED + "Jogador não encontrado.");}
                    }
                    else {
                        PlayerData data = JMCore.getInstance().playerDataHashMap.get(target);
                        HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");
                        io.github.juanmuscaria.essentials.data.Home home = homes.get(args[1]);
                        if(home == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
                        else {
                            if (p.hasPermission("jm.admin.home"))homes.remove(args[1]);
                            else p.sendMessage(ChatColor.RED + "Home não encontrada.");
                        }
                    }
                }
                else {
                    p.sendMessage("Numero de argumentos invalido.");
                    sendHelpMenssage(p,label);
                    return true;
                }
                break;
            }
            case "setpublic": {
                Player p = (Player) sender;
                if (args.length < 2) {
                    p.sendMessage("Numero de argumentos invalido.");
                    sendHelpMenssage(p,label);
                }
                else {

                }
                break;
            }
            default: {
                Player p = (Player) sender;
                sendHelpMenssage(p,label);
                break;
            }

        }
        return true;
    }

    private void sendHelpMenssage(Player p,String command){
        JSONChat msg = new JSONChat();
        Text lines = new Text("----", Colors.Gray).setBold(true);
        Text name = new Text("Home", Colors.Blue).setHoverEvent(
                new Event("show_text","Módulo homes do JMEssentials by:juanmuscaria.").toJson());
        msg.addText(lines).addText(name).addText(lines).addText(new Text("\n"));
        msg.addText(new Text("Lista de comandos [Opcional] <Obrigatório>:\n",Colors.White));
        msg.addText(new Text("  >/"+command+" homes [jogador]",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" homes [jogador]").toJson()));
        msg.addText(new Text("\nLista as suas homes públicas e privadas. Caso for dado o nome de um jogador irá listar as homes publicas do mesmo.",Colors.Gray));

        msg.addText(new Text("  >/"+command+" set <Nome>",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" set <Nome>").toJson()));
        msg.addText(new Text("\nDefine uma home no local que você está",Colors.Gray));

        msg.addText(new Text("  >/"+command+" tp <Nome> [jogador]",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" tp <Nome> [jogador]").toJson()));
        msg.addText(new Text("\nTeleporta para uma home.",Colors.Gray));

        msg.addText(new Text("  >/"+command+" del <Nome> [jogador]",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" del <Nome> [jogador]").toJson()));
        msg.addText(new Text("\nDeleta uma home.",Colors.Gray));

        msg.addText(new Text("  >/"+command+" setpublic <Nome>",Colors.Cyan).setBold(true).setClickEvent(
                new Event("suggest_command","/"+command+" setpublic <Nome>").toJson()));
        msg.addText(new Text("\nPermite deixar uma home publica ou privada.",Colors.Gray));

        msg.addText(lines).addText(name).addText(lines).addText(new Text("\n"));
        msg.sendTo(p);
    }
}

