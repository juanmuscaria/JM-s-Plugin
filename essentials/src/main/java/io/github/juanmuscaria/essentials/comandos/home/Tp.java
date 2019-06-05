package io.github.juanmuscaria.essentials.comandos.home;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.comandos.SubCommand;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.essentials.data.HomeData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Tp extends SubCommand {
    public Tp(CommandExecutor executor, String command) {
        super(executor, command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Carrega os dados do jogador;
        Player p = (Player) sender;
        PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
        HashMap<String, HomeData> homes = (HashMap<String, HomeData>) data.getData().get("homes");

        if (args.length == 2){
            HomeData homeData = homes.get(args[1]);
            if(homeData == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
            else {
                homeData.doTeleport(p);
            }
        }
        else{
            String target = args[2];
            if(Bukkit.getPlayer(target) == null){
                PlayerData tdata = new PlayerData(target,null);
                try {
                    HashMap<String, HomeData> thomes = (HashMap<String, HomeData>) tdata.getData().get("homes");
                    HomeData homeData = thomes.get(args[1]);
                    if(homeData == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
                    else {
                        if (homeData.getPublic() || p.hasPermission("jm.admin.homeData")) homeData.doTeleport(p);
                        else p.sendMessage(ChatColor.RED + "Home não encontrada.");
                    }
                    data.disable();
                }
                catch (Exception e) {p.sendMessage(ChatColor.RED + "Jogador não encontrado.");}
            }
            else {
                PlayerData tdata = JMCore.getInstance().playerDataHashMap.get(target);
                HashMap<String, HomeData> thomes = (HashMap<String, HomeData>) tdata.getData().get("homes");
                HomeData homeData = thomes.get(args[1]);
                if(homeData == null)p.sendMessage(ChatColor.RED + "Home não encontrada.");
                else {
                    if (homeData.getPublic() || p.hasPermission("jm.admin.homeData")) homeData.doTeleport(p);
                    else p.sendMessage(ChatColor.RED + "Home não encontrada.");
                }
            }
        }
    }

    @Override
    public Boolean validateArguments(String[] args) {
        return args.length >1;
    }
}
