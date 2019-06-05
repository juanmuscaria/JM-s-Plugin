package io.github.juanmuscaria.essentials.comandos.home;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.comandos.SubCommand;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.core.utils.jsonchat.Colors;
import io.github.juanmuscaria.core.utils.jsonchat.Event;
import io.github.juanmuscaria.core.utils.jsonchat.JSONChat;
import io.github.juanmuscaria.core.utils.jsonchat.Text;
import io.github.juanmuscaria.essentials.data.HomeData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class Homes extends SubCommand {
    public Homes(CommandExecutor executor, String command) {
        super(executor, command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Carrega os dados do jogador;
        Player p = (Player) sender;
        PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
        HashMap<String, HomeData> homes = (HashMap<String, HomeData>) data.getData().get("homes");

        if (args.length == 1) {
            p.sendMessage(ChatColor.GRAY + "Lista de homes:\n");
            Text arrow = new Text(" >>", Colors.BrightGreen);
            arrow.setBold(true);
            homes.forEach((K, V) -> {
                JSONChat msg = new JSONChat();
                Text home = new Text(K).setClickEvent(new Event("run_command", "/home tp " + K).toJson()).setHoverEvent(new Event("show_text","Local:"+V.getLocation().toLocation()+" É pública:" + (V.getPublic() ? "sim." : "não.")).toJson());
                if (V.getPublic()) home.setColor(Colors.Red);
                else home.setColor(Colors.Blue);
                msg.addText(arrow).addText(home).sendTo(p);
            });
            new JSONChat().addText( new Text(">Info<", Colors.Gray).setHoverEvent(new Event("show_text","Dica: Você pode clicar em uma home para teleportar-se.").toJson())).sendTo(p);
        }
        else {
            String target = args[1];
            try {

                PlayerData targetData = (Bukkit.getPlayer(target) == null) ? new PlayerData(target, null) : JMCore.getInstance().playerDataHashMap.get(target);

                HashMap<String, HomeData> targetHomes = (HashMap<String, HomeData>) data.getData().get("homes");

                p.sendMessage(ChatColor.GRAY + "Lista de homes:\n");
                targetHomes.forEach((K, V) -> {
                    if (!( p.hasPermission("jm.admin.home") || p.isOp())){
                        if (V.getPublic()){
                            JSONChat msg = new JSONChat();
                            Text home = new Text(K).setClickEvent(new Event("run_command", "/home tp " + K + " " + target).toJson()).setHoverEvent(new Event("show_text","Local:"+V.getLocation().toLocation()+" É pública:" + (V.getPublic() ? "sim." : "não.")).toJson());
                            home.setColor(Colors.Blue);
                            msg.addText(new Text(" >>", Colors.BrightGreen).setBold(true)).addText(home).sendTo(p);
                        }
                    }
                    else {
                        JSONChat msg = new JSONChat();
                        Text home = new Text(K).setClickEvent(new Event("run_command", "/home tp " + K + " " + target).toJson()).setHoverEvent(new Event("show_text","Local:"+V.getLocation().toLocation()+" É pública:" + (V.getPublic() ? "sim." : "não.")).toJson());
                        home.setColor(Colors.Blue);
                        msg.addText(new Text(" >>", Colors.BrightGreen).setBold(true)).addText(home).sendTo(p);
                    }

                });
                new JSONChat().addText( new Text(">Info<", Colors.Gray).setHoverEvent(new Event("show_text","Dica: Você pode clicar em uma home para teleportar-se.").toJson())).sendTo(p);
                if (targetData.isOfflineData())targetData.disable();
            }
            catch (Exception e) {
                p.sendMessage(ChatColor.RED + "Jogador não encontrado.");
            }
        }
    }

    @Override
    public Boolean validateArguments(String[] args) {
        if (args.length == 0)return false;
        return args.length <= 2;
    }

    @Override
    public Boolean valdateCommand(String command){

        return cmd.equalsIgnoreCase(command);
    }

}
