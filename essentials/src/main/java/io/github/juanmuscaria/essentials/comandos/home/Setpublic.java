package io.github.juanmuscaria.essentials.comandos.home;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.comandos.SubCommand;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.essentials.data.HomeData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Setpublic extends SubCommand {

    public Setpublic(CommandExecutor executor, String command) {
        super(executor, command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Carrega os dados do jogador;
        Player p = (Player) sender;
        PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
        HashMap<String, HomeData> homes = (HashMap<String, HomeData>) data.getData().get("homes");

        HomeData homeData = homes.get(args[1]);
        if (homeData == null) p.sendMessage(ChatColor.RED + "Home inexistente.");
        else {
            homeData.setPublic(!homeData.getPublic());
            p.sendMessage(ChatColor.GREEN + "Agora essa home é " + (homeData.getPublic() ?  ChatColor.RED + "publica" : ChatColor.BLUE + "privada"));
        }


    }

    @Override
    public Boolean validateArguments(String[] args) {
        return args.length > 2;
    }
}
