package io.github.juanmuscaria.core.utils.jsonchat;

import org.jetbrains.annotations.Contract;

public enum Colors {
    Black("black"),
    DarkBlue("dark_blue"),
    DarkGreen("dark_green"),
    DarkCyan("dark_aqua"),
    DarkRed("dark_red"),
    Purple("dark_purple"),
    Gold("gold"),
    Gray("gray"),
    DarkGray("dark_gray"),
    Blue("blue"),
    BrightGreen("green"),
    Cyan("aqua"),
    Red("red"),
    Pink("light_purple"),
    Yellow("yellow"),
    White("white"),
    @Deprecated Random("obfuscated"),
    @Deprecated Bold("bold"),
    @Deprecated Strikethrough("strikethrough"),
    @Deprecated Underlined("underline"),
    @Deprecated Italic("italic"),
    Reset("reset");

    private final String name;

    Colors(final String name) {
        this.name = name;
    }

    @Contract(pure = true)
    @Override
    public String toString() {
        return name;
    }
}
