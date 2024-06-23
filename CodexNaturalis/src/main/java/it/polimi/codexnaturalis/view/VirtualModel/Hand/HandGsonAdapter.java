package it.polimi.codexnaturalis.view.VirtualModel.Hand;

import com.google.gson.*;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.utils.PersonalizedException;

import java.lang.reflect.Type;

public class HandGsonAdapter implements JsonDeserializer<Hand> {

    @Override
    public Hand deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        Hand hand = new Hand();
        JsonArray jsonCards = jsonObject.getAsJsonArray("cards");
        for (JsonElement jsonCard : jsonCards) {
            // Deserialize each card element in the array
            Card card = context.deserialize(jsonCard, Card.class);
            hand.addCard(card); // Add the deserialized card to the hand
        }
        return hand;
    }
}