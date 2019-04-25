package io.github.juanmuscaria.core.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum FakePlayers {
    MEKANIMS("[Mekanism]"),
    COFHCORE("[CoFH]"),
    THEWRMAL("[CoFH]"),
    MINECRAFT("[Minecraft]"),
    MINEFACTORY("[MineFactory]"),
    BUILDCRAFT("[Buildcraft]"),
    FORESTRY("[Forestry]"),
    COMPUTERCRAFT("ComputerCraft"),
    STEVESCARTS("[Stevescart]"),
    OPENCOMPUTERS("[OpenComputers]"),
    EIOKILLERJOE("[EioKillera]"),
    EIOFARMER("[EioFarmer]");


    private final String name;

    FakePlayers(final String name) {
        this.name = name;
    }

    @Contract(pure = true)
    @Override
    public String toString() {
        return name;
    }

    @NotNull
    public Boolean isFakePlayer(String player) {
        player = player.toLowerCase();
        for (int i = 0; i <= FakePlayers.values().length; i++) {
            if (player.contains(FakePlayers.values()[i].toString().toLowerCase())) return true;
        }
        return false;
    }

}
