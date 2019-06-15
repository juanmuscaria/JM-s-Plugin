package io.github.juanmuscaria.essentials.comandos.home;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.comandos.SubCommand;
import io.github.juanmuscaria.core.data.PlayerData;
import io.github.juanmuscaria.core.data.serializable.SerializableLocation;
import io.github.juanmuscaria.core.utils.Utils;
import io.github.juanmuscaria.essentials.data.HomeData;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Set extends SubCommand {
    public Set(CommandExecutor executor, String command) {
        super(executor, command);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Carrega os dados do jogador;
        Player p = (Player) sender;
        PlayerData data = JMCore.getInstance().playerDataHashMap.get(p.getName());
        HashMap<String, HomeData> homes = (HashMap<String, HomeData>) data.getData().get("homes");

        if ((Utils.getUserPermissionInteger("jm.home.max", p) <= homes.size()) && !(p.hasPermission("jm.home.unlimited") || p.isOp()))
            p.sendMessage(ChatColor.RED + "Limite de homes atingido! (" + homes.size() + "/" + Utils.getUserPermissionInteger("jm.homes", p) + ")");
        else {
            if (Utils.validateStringForMap(args[1])) {
                SerializableLocation location = new SerializableLocation(p.getLocation());
                HomeData homeData = new HomeData(location, false);
                homes.put(args[1], homeData);
                data.save();
                p.sendMessage(ChatColor.GREEN + "Home " + args[1] + " definida com sucesso.");
            } else p.sendMessage(ChatColor.RED + "Só é permitido caracteres de aA-zZ!");
        }
    }

    @Override
    public Boolean validateArguments(String[] args) {
        return args.length >= 2;
    }
}
