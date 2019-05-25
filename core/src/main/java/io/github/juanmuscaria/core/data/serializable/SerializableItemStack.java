package io.github.juanmuscaria.core.data.serializable;

import io.github.juanmuscaria.core.utils.nms.NMSUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class SerializableItemStack implements Serializable {
    private String itemStack;

    public SerializableItemStack(ItemStack stack) {
        this.itemStack = NMSUtil.nmsUtil.serializeItemStack(stack);
    }

    public SerializableItemStack() {
        this.itemStack = NMSUtil.nmsUtil.serializeItemStack(new ItemStack(Material.AIR));
    }

    public ItemStack getItemStack() {
        return NMSUtil.nmsUtil.deserializeItemStack(itemStack);
    }

    public void setItemStack(ItemStack stack) {
        this.itemStack = NMSUtil.nmsUtil.serializeItemStack(stack);
    }

    @Override
    public String toString() {
        return itemStack;
    }
}
