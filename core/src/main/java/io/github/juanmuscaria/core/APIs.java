package io.github.juanmuscaria.core;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import fr.neatmonster.nocheatplus.NCPAPIProvider;
import fr.neatmonster.nocheatplus.components.NoCheatPlusAPI;
import io.github.juanmuscaria.core.utils.Logger;
import io.github.juanmuscaria.core.utils.Utils;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.Nullable;

public class APIs {
    @Nullable
    public static NoCheatPlusAPI ncpApi;
    @Nullable
    public static ProtocolManager protocolManager;
    @Nullable
    public static Permission PermissionAPI;
    @Nullable
    public static Economy EconomyAPI;
    @Nullable
    public static Chat ChatAPI;
    public static Boolean isThermos = false;
    public static Boolean hasJMEssentials = false;
    public static Boolean hasJMChunks = false;

    static void loadAPIs() {
        Logger.Debug("Tentando carregar APIs");

        //TODO:Fazer o fix do ncp com o forge.
        try {
            ncpApi = NCPAPIProvider.getNoCheatPlusAPI();
        } catch (NoClassDefFoundError ignore) {
            Logger.Warn("Não foi possivel iniciar a api do NCP");
        }

        try {
            protocolManager = ProtocolLibrary.getProtocolManager();
        } catch (NoClassDefFoundError ignore) {
            Logger.Warn("Não foi possivel iniciar a api do ProtocolLib");
        }
        try {
            RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
            PermissionAPI = permissionProvider.getProvider();
            RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
            ChatAPI = chatProvider.getProvider();
            RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        } catch (NoClassDefFoundError ignore) {
            Logger.Warn("Não foi possivel iniciar os hooks do Vault.");
        }

        if (Utils.doesClassExist("thermos.Thermos") || Utils.doesClassExist("net.minecraftforge.caudron.CaudronUtils") || Utils.doesClassExist("net.minecraftforge.common.ForgeHooks")) {
            isThermos = true;
            Logger.cLog("Forge detectado!");
        }
    }
}
