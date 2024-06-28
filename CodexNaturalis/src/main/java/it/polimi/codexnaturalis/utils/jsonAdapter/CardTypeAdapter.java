package it.polimi.codexnaturalis.utils.jsonAdapter;

import com.google.gson.*;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;

import java.lang.reflect.Type;

/**
 * The type Card type adapter.
 */
public class CardTypeAdapter implements JsonDeserializer<Card> {
    @Override
    public Card deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        // Check the presence of attributes specific to each type of card
        if (jsonObject.has("frontCardPoint")) {
            return context.deserialize(json, ResourceCard.class);
        } else if (jsonObject.has("pointPerCondition")) {
            return context.deserialize(json, ObjectiveCard.class);
        } else if (jsonObject.has("backNorthResource")) {
            return context.deserialize(json, StarterCard.class);
        } else {
            throw new JsonParseException("Unable to determine the type of card");
        }
    }
}