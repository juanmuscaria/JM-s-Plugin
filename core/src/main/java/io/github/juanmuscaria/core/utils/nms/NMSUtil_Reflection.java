package io.github.juanmuscaria.core.utils.nms;

import com.google.gson.JsonObject;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.*;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.Arrays;

public class NMSUtil_Reflection extends NMSUtil {

    @Override
    public ItemStack deserializeItemStack(String data) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        ItemStack itemStack = null;
        try {
            Class<?> nbtTagCompoundClass = getNMSClass("NBTTagCompound");
            Class<?> nmsItemStackClass = getNMSClass("ItemStack");
            Object nbtTagCompound = getNMSClass("NBTCompressedStreamTools").getMethod("a", DataInputStream.class).invoke(null, dataInputStream);
            //Object nbtTagCompound = getNMSClass("NBTCompressedStreamTools").getMethod("a", DataInputStream.class).invoke(null, inputStream);
            Object craftItemStack = nmsItemStackClass.getMethod("createStack", nbtTagCompoundClass).invoke(null, nbtTagCompound);
            itemStack = (ItemStack) getOBClass("inventory.CraftItemStack").getMethod("asBukkitCopy", nmsItemStackClass).invoke(null, craftItemStack);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        if (itemStack == null) {
            ItemStack StackErro = new ItemStack(Material.FIRE);
            ItemMeta MetaErro = StackErro.getItemMeta();
            MetaErro.setDisplayName(ChatColor.RED + "UM ERRO ACONTECEU COM ESSE ITEM, CONTATE A STAFF!!!!");
            MetaErro.setLore(Arrays.asList(ChatColor.RED + "Item data:", data, ChatColor.RED + "Caso você perca esse item não terá devolução do item original."));
            StackErro.setItemMeta(MetaErro);
            return StackErro;
        } else {
            return itemStack;
        }
    }

    @Override
    public String serializeItemStack(ItemStack item) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);

        try {
            Class<?> nbtTagCompoundClass = getNMSClass("NBTTagCompound");
            Constructor<?> nbtTagCompoundConstructor = nbtTagCompoundClass.getConstructor();
            Object nbtTagCompound = nbtTagCompoundConstructor.newInstance();
            Object nmsItemStack = getOBClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, item);
            getNMSClass("ItemStack").getMethod("save", nbtTagCompoundClass).invoke(nmsItemStack, nbtTagCompound);
            getNMSClass("NBTCompressedStreamTools").getMethod("a", nbtTagCompoundClass, DataOutput.class).invoke(null, nbtTagCompound, dataOutput);

        } catch (ReflectiveOperationException e) {
            Logger.Error("Occoreu um erro ao tentar serializar uma item stack!");
            e.printStackTrace();
        }

        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }

    @Override
    public void sendJsonChat(JsonObject chatObj, Player player) {

    }
}
