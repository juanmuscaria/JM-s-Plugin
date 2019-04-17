package io.github.juanmuscaria.core.utils;

import io.github.juanmuscaria.core.JMCore;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.Arrays;

import static io.github.juanmuscaria.core.utils.NMS.getNMSClass;
import static io.github.juanmuscaria.core.utils.NMS.getOBClass;


public class Utils {
    public static void CreateFolder(String Name) {
        try {
            File Folder = new File(JMCore.getInstance().getDataFolder() + File.separator + Name);
            if (!Folder.exists())
                if (!Folder.mkdirs())
                    Logger.Error("Não foi possivel criar a pasta:" + Name);

        } catch (SecurityException ex) {
            Logger.Error("Não foi possivel criar a pasta:" + Name);
            ex.printStackTrace();
        }
    }

    public static boolean doesClassExist(String name) {
        try {
            Class c = Class.forName(name);
            if (c != null)
                return true;
        } catch (NoClassDefFoundError | ClassNotFoundException e) {
            Logger.Debug("Não foi possivel encontrar a class: " + ChatColor.RED + name);
        } catch (Exception e) {
            StringWriter a = new StringWriter();
            PrintWriter b = new PrintWriter(a);
            e.printStackTrace(b);
            Logger.Debug("Um erro inesperado ocorreu ao tentar encontrar a class: " + ChatColor.RED + name);
            Logger.Debug(ChatColor.RED + a.toString());
        }
        return false;
    }

    //Tudo copiado daki:https://www.spigotmc.org/threads/how-to-serialize-itemstack-inventory-with-attributestorage.152931/
    @NotNull
    public static ItemStack deserializeItemStack(String data) {
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

    @NotNull
    public static String serializeItemStack(ItemStack item) {
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
            e.printStackTrace();
        }

        //return BaseEncoding.base64().encode(outputStream.toByteArray());
        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }


}
