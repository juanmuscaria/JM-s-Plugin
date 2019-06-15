package io.github.juanmuscaria.core.utils.nms;

import com.google.gson.JsonObject;
import io.github.juanmuscaria.core.utils.Logger;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.NBTCompressedStreamTools;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;

public class NMSUtil_v1_7_R4 extends NMSUtil {
    //Baseado nisso aqui:https://www.spigotmc.org/threads/how-to-serialize-itemstack-inventory-with-attributestorage.152931/

    @Override
    public ItemStack deserializeItemStack(String data) {
        if (data.isEmpty() || data.equals("AIR")) return null;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());
        DataInputStream dataInputStream = new DataInputStream(inputStream);

        ItemStack itemStack = null;
        try {
            NBTTagCompound nbtTagCompound = NBTCompressedStreamTools.a(dataInputStream);
            net.minecraft.server.v1_7_R4.ItemStack craftItemStack = net.minecraft.server.v1_7_R4.ItemStack.createStack(nbtTagCompound);
            itemStack = CraftItemStack.asBukkitCopy(craftItemStack);
        } catch (Exception e) {
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
    @Override
    public String serializeItemStack(ItemStack item) {
        if (item == null || item.isSimilar(new ItemStack(Material.AIR))) return "AIR";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);
        try {
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            net.minecraft.server.v1_7_R4.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);
            nmsItemStack.save(nbtTagCompound);
            NBTCompressedStreamTools.a(nbtTagCompound, (DataOutput) dataOutput);

        } catch (NullPointerException e) {
            Logger.Error("Occoreu um erro ao tentar serializar uma item stack!");
            e.printStackTrace();
        }
        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }

    @Override
    public void sendJsonChat(JsonObject chatObj, Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutChat(ChatSerializer.a(chatObj.toString()), true));
    }
}
