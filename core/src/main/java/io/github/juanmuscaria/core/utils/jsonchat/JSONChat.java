package io.github.juanmuscaria.core.utils.jsonchat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.juanmuscaria.core.utils.nms.NMSUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class JSONChat {
    private JsonObject jsonObject = new JsonObject();

    public JSONChat() {
        jsonObject.addProperty("text", "");
        JsonArray text = new JsonArray();
        jsonObject.add("extra", text);
    }

    public JSONChat addText(JsonObject text) {
        jsonObject.getAsJsonArray("extra").add(text);
        return this;
    }

    public JSONChat addText(@NotNull Text text) {
        jsonObject.getAsJsonArray("extra").add(text.toJson());
        return this;
    }

    public void sendTo(Player p) {
        NMSUtil.nmsUtil.sendJsonChat(jsonObject,p);
    }
}

