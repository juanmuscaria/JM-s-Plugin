package io.github.juanmuscaria.core.utils.jsonchat;

import com.google.gson.JsonObject;

public class Text {

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

    public Text setBold(Boolean bold) {
        this.bold = bold;
        return this;
    }

    public Boolean getItalic() {
        return italic;
    }

    public Text setItalic(Boolean italic) {
        this.italic = italic;
        return this;
    }

    public Boolean getUnderlined() {
        return underlined;
    }

    public Text setUnderlined(Boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public Boolean getStrikethrough() {
        return strikethrough;
    }

    public Text setStrikethrough(Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public Boolean getObfuscated() {
        return obfuscated;
    }

    public Text setObfuscated(Boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    public Colors getColor() {
        return color;
    }

    public Text setColor(Colors color) {
        this.color = color;
        return this;
    }

    public JsonObject getClickEvent() {
        return clickEvent;
    }

    public Text setClickEvent(JsonObject clickEvent) {
        this.clickEvent = clickEvent;
        return this;
    }

    public JsonObject getHoverEvent() {
        return hoverEvent;
    }

    public Text setHoverEvent(JsonObject hoverEvent) {
        this.hoverEvent = hoverEvent;
        return this;
    }

    public String getText() {
        return text;
    }

    public Text setText(String text) {
        this.text = text;
        return this;
    }
}
