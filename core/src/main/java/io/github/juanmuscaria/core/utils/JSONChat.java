package io.github.juanmuscaria.core.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;

enum Colors {
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

public class JSONChat {
    private JsonObject jsonObject = new JsonObject();

    public JSONChat() {
        jsonObject.addProperty("text", "");
        JsonArray text = new JsonArray();
        jsonObject.add("extra", text);
    }

    public void addText(JsonObject text) {
        jsonObject.getAsJsonArray("extra").add(text);
    }

    public void sendTo(Player p) {

    }
}

class Text {

    private Boolean bold = false;
    private Boolean italic = false;
    private Boolean underlined = false;
    private Boolean strikethrough = false;
    private Boolean obfuscated = false;
    private Colors color;
    private JsonObject clickEvent;
    private JsonObject hoverEvent;
    private String text = "";

    public Text(String text) {
        this.text = text;
    }

    public Text(String text, Colors color) {
        this.text = text;
        this.color = color;
    }

    public Text(String text, Colors color, Boolean bold, Boolean italic, Boolean underlined, Boolean strikethrough, Boolean obfuscated) {
        this.text = text;
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;

    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("text", text);
        json.addProperty("bold", bold);
        json.addProperty("italic", italic);
        json.addProperty("underlined", underlined);
        json.addProperty("strikethrough", strikethrough);
        json.addProperty("obfuscated", obfuscated);

        if (!(color == null)) json.addProperty("color", color.toString());
        if (!(clickEvent == null)) json.add("clickEvent", clickEvent);
        if (!(hoverEvent == null)) json.add("hoverEvent", hoverEvent);

        return json;
    }

    public Boolean getBold() {
        return bold;
    }

    public void setBold(Boolean bold) {
        this.bold = bold;
    }

    public Boolean getItalic() {
        return italic;
    }

    public void setItalic(Boolean italic) {
        this.italic = italic;
    }

    public Boolean getUnderlined() {
        return underlined;
    }

    public void setUnderlined(Boolean underlined) {
        this.underlined = underlined;
    }

    public Boolean getStrikethrough() {
        return strikethrough;
    }

    public void setStrikethrough(Boolean strikethrough) {
        this.strikethrough = strikethrough;
    }

    public Boolean getObfuscated() {
        return obfuscated;
    }

    public void setObfuscated(Boolean obfuscated) {
        this.obfuscated = obfuscated;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public JsonObject getClickEvent() {
        return clickEvent;
    }

    public void setClickEvent(JsonObject clickEvent) {
        this.clickEvent = clickEvent;
    }

    public JsonObject getHoverEvent() {
        return hoverEvent;
    }

    public void setHoverEvent(JsonObject hoverEvent) {
        this.hoverEvent = hoverEvent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

class Event {
    private String action = "";
    private JsonObject value = new JsonObject();
    private String valueString = "";
    private Boolean asString = false;

    public Event(String action, String value) {
        this.action = action;
        this.valueString = value;
        this.asString = true;
    }

    public Event(String action, JsonObject value) {
        this.action = action;
        this.value = value;
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("action", action);
        if (asString) json.addProperty("value", valueString);
        else json.add("value", value);
        return json;
    }
}
