package io.github.juanmuscaria.core.comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {
    private CommandExecutor mainCommand;
    protected String cmd;

    public SubCommand(CommandExecutor executor, String command){
        this.mainCommand = executor;
        this.cmd = command;
    }

    public abstract void onCommand(CommandSender sender, Command command, String label, String[] args);

    public abstract Boolean validateArguments(String[] args);

    public Boolean valdateCommand(String command){
        return cmd.equalsIgnoreCase(command);
    }

    public void sendHelp(Player p, String command){
        p.sendMessage(ChatColor.RED + "Comando incorreto, digite /"+command+" help para ajuda.");
    }

    public CommandExecutor getMainCommand(){
        return this.mainCommand;
    }
}
