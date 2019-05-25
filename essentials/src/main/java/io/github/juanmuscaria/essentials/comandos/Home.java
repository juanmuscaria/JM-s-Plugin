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
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Você precisa ser um jogador para executar esse comando.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length == 0){
            sendHelpMenssage(p,label);
            return true;
        }
        PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
        HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");

        switch (args[0].toLowerCase()) {
            case "homes": {
                if (args.length == 1) {
                    p.sendMessage(ChatColor.GRAY + "Lista de homes:\n");
                    Text arrow = new Text(" >>", Colors.BrightGreen);
                    arrow.setBold(true);
                    homes.forEach((K, V) -> {
                        JSONChat msg = new JSONChat();
                        Text home = new Text(K).setClickEvent(new Event("run_command", "/home tp " + K).toJson()).setHoverEvent(new Event("show_text","Local:"+V.getLocation().toLocation()+" É pública:" + (V.getPublic() ? "sim." : "não.")).toJson()+);
                        if (V.getPublic()) home.setColor(Colors.Red);
                        else home.setColor(Colors.Blue);
                        msg.addText(arrow).addText(home).sendTo(p);
                    });
                    new JSONChat().addText( new Text(">Info<", Colors.Gray).setHoverEvent(new Event("show_text","Dica: Você pode clicar em uma home para teleportar-se.").toJson())).sendTo(p);
                    break;
                }
                else {
                    String target = args[1];
                    try {

                        PlayerData targetData = (Bukkit.getPlayer(target) == null) ? new PlayerData(target, null) : JMCore.getInstance().playerDataHashMap.get(target);

                        HashMap<String, io.github.juanmuscaria.essentials.data.Home> targetHomes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");

                        p.sendMessage(ChatColor.GRAY + "Lista de homes:\n");
                        targetHomes.forEach((K, V) -> {
                            if (!isAdmin(p) && !V.getPublic()){}
                            else {
                                JSONChat msg = new JSONChat();
                                Text home = new Text(K).setClickEvent(new Event("run_command", "/home tp " + K + " " + target).toJson()).setHoverEvent(new Event("show_text","Local:"+V.getLocation().toLocation()+" É pública:" + (V.getPublic() ? "sim." : "não.")).toJson()+);
                                home.setColor(Colors.Blue);
                                msg.addText(new Text(" >>", Colors.BrightGreen).setBold(true)).addText(home).sendTo(p);
                            }

                        });
                        new JSONChat().addText( new Text(">Info<", Colors.Gray).setHoverEvent(new Event("show_text","Dica: Você pode clicar em uma home para teleportar-se.").toJson())).sendTo(p);
                        if (targetData.isOfflineData())targetData.disable();
                        }
                        catch (Exception e) {
                        p.sendMessage(ChatColor.RED + "Jogador não encontrado.");
                        break;
                    }
                }
                break;
            }
            case "set": {
                if (args.length < 2) {
                    p.sendMessage("Número de argumentos invalido.");
                    sendHelpMenssage(p,label);
                    return true;
                }
                PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
                HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");
                if ((Utils.getUserPermissionInteger("jm.homes",p) <= homes.size()) && !(p.hasPermission("jm.home.unlimited") && )) p.sendMessage(ChatColor.RED + "Limite de homes atingido! (" + homes.size() + "/" + Utils.getUserPermissionInteger("jm.homes",p) + ")");
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
                        break;
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
                    PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
                    HashMap<String, io.github.juanmuscaria.essentials.data.Home> homes = (HashMap<String, io.github.juanmuscaria.essentials.data.Home>) data.getData().get("homes");
                    io.github.juanmuscaria.essentials.data.Home home = homes.get(args[1]);
                    if(home == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
                    else home.setPublic(!home.getPublic());
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

        Text lines = new Text("------", Colors.Gray).setBold(true);
        Text name = new Text("Home", Colors.Blue).setHoverEvent(
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

    private Boolean isAdmin(Player p){
        return p.hasPermission("jm.admin.home") || p.isOp();
    }
}