package io.github.juanmuscaria.core.data;

import io.github.juanmuscaria.core.utils.Utils;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;

public class SerializableItemStack implements Serializable {
    private String itemStack;

    public SerializableItemStack(ItemStack stack) {
        this.itemStack = Utils.serializeItemStack(stack);
    }

    public SerializableItemStack() {
    }

    public ItemStack getItemStack() {
        if (itemStack.isEmpty()) return null;
        else return Utils.deserializeItemStack(itemStack);
    }

    public void setItemStack(ItemStack stack) {
        if (stack == null) itemStack = "";
        else this.itemStack = Utils.serializeItemStack(stack);
    }
}
