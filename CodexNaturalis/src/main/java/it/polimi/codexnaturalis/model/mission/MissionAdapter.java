package it.polimi.codexnaturalis.model.mission;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MissionAdapter implements JsonDeserializer<Mission> {
    @Override
    public Mission deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Check for attributes that distinguish between mission types
        if (jsonObject.has("numberOfSymbols")) {
            return context.deserialize(json, ResourceMission.class);
        } else if (jsonObject.has("isLeftToRight")) {
            return context.deserialize(json, DiagonalMission.class);
        } else if (jsonObject.has("pillarResource")) {
            return context.deserialize(json, BendMission.class);
        } else {
            throw new JsonParseException("Unable to determine the type of mission");
        }
    }
}
