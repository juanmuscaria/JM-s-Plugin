package io.github.juanmuscaria.core;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import fr.neatmonster.nocheatplus.NCPAPIProvider;
import fr.neatmonster.nocheatplus.components.NoCheatPlusAPI;
import io.github.juanmuscaria.core.utils.Logger;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.Nullable;

public class APIs {
    @Nullable
    public static NoCheatPlusAPI ncpApi;
    @Nullable
    public static ProtocolManager protocolManager;
    @Nullable
    public static Permission vPermission = null;
    @Nullable
    public static Economy vEconomy = null;
    @Nullable
    public static Chat vChat = null;
    public static boolean hasNCP = false;
    public static boolean hasProtocolLib = false;
    public static boolean hasVault = false;

    public static void LoadAPIs() {
        Logger.Debug("Tentando carregar APIs");
        try {
            ncpApi = NCPAPIProvider.getNoCheatPlusAPI();
            hasNCP = true;
        } catch (NoClassDefFoundError ex) {
            ncpApi = null;
            Logger.cLog(ChatColor.RED + "Não foi possivel iniciar a api do NCP");
        }
        try {
            protocolManager = ProtocolLibrary.getProtocolManager();

        } catch (NoClassDefFoundError ex) {
            protocolManager = null;
            Logger.cLog(ChatColor.RED + "Não foi possivel iniciar a api do ProtocolLib");
        }


    }
}
