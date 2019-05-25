package io.github.juanmuscaria.core.utils.jsonchat;

import com.google.gson.JsonObject;

public class Event {
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
