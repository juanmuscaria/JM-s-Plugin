package io.github.juanmuscaria.core;

import io.github.juanmuscaria.core.data.SerializableItemStack;
import io.github.juanmuscaria.core.utils.Logger;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Testes {
    Testes() {
        ItemStack stack = new ItemStack(Material.getMaterial("ENDERIO_BLOCKSTIRLINGGENERATOR"));
        Logger.Log("Item stack normal:" + stack.getItemMeta().getDisplayName() + "  " + stack.toString());
        SerializableItemStack stack2 = new SerializableItemStack(stack);
        Logger.Log("Item stack serialized:" + stack2.getItemStack().getItemMeta().getDisplayName() + "  " + stack2.getItemStack().toString());
    }
}
