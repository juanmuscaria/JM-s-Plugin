package io.github.juanmuscaria.core.data.serializable;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public class SerializableInventory implements Serializable {
    private SerializableItemStack[] itemStacks;
    private int size;
    private String name;

    public SerializableInventory(@NotNull Inventory inventory) {
        this.size = inventory.getSize();
        this.itemStacks = new SerializableItemStack[size];
        this.name = inventory.getName();
        for (int i = 0; i <= size; i++) {
            itemStacks[i] = new SerializableItemStack(inventory.getItem(i));
        }
    }

    public int getSize() {
        return size;
    }

    public ItemStack getItemStack(int index) {
        return itemStacks[index].getItemStack();
    }

    public void setItemStack(ItemStack stack, int index) {
        itemStacks[index].setItemStack(stack);
    }

    public Inventory generateInventory(@Nullable InventoryHolder owner) {
        Inventory inventory = Bukkit.createInventory(owner, this.size, this.name);
        for (int i = 0; i <= size; i++) {
            inventory.setItem(i, itemStacks[i].getItemStack());
        }
        return inventory;
    }
}
