package io.github.juanmuscaria.core.utils;

import io.github.juanmuscaria.core.JMCore;
import io.github.juanmuscaria.core.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

//Baseado no "https://github.com/RUSHyoutuber/System"
public class CommandLoader {
    private static final Config Commands = new Config("comandos");
    private static final FileConfiguration CONFIG = Commands.Get();
    private CommandExecutor executor;
    private String command;
    private List<String> aliases;
    private String description;
    private String permissionMessage;
    private String permission;
    private PluginCommand pluginCommand;

    /**
     * Carrega dinamicamente um comando!
     *
     * @param command    O nome do comando que será executado apos o /.
     * @param permission A permissão que o jodador deverá ter para usar o comando.
     * @param cExecutor  Uma classe que estenda a interface CommandExecutor que será responsavel por executar o comando.
     */
    public CommandLoader(String command, String permission, CommandExecutor cExecutor) {
        boolean enable = CONFIG.getBoolean("comandos." + command + ".ativar-comando");

        if (enable) {
            Logger.Debug("Registrando o comando: " + ChatColor.GREEN + command);
            this.executor = cExecutor;
            this.command = command;
            this.aliases = CONFIG.getStringList("comandos." + command + ".aliases");
            this.description = CONFIG.getString("comandos." + command + ".descricao");
            this.permissionMessage = CONFIG.getString("comandos." + command + ".sem-permissao").replace('&', '§');
            this.permission = permission;
            this.pluginCommand = createPluginCommand();
            registerPluginCommand();
        }

    }

    private PluginCommand createPluginCommand() {
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);

            PluginCommand cmd = c.newInstance(command, JMCore.getInstance());
            cmd.setAliases(aliases);
            cmd.setPermission(permission);
            cmd.setPermissionMessage(permissionMessage);
            cmd.setDescription(description);
            cmd.setExecutor(executor);

            return cmd;
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void registerPluginCommand() {
        if (pluginCommand == null) return;

        try {
            Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);

            Object commandMapObject = f.get(Bukkit.getPluginManager());
            if (commandMapObject instanceof CommandMap) {
                CommandMap commandMap = (CommandMap) commandMapObject;
                commandMap.register(JMCore.getInstance().getName().toLowerCase(), pluginCommand);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
