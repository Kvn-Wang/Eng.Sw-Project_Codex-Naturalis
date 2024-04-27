package it.polimi.codexnaturalis.model.shop;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Shop {
    public final ShopType shopType;
    protected final String cardsFile;
    private ArrayList<Card> cardDeck;
    protected Card topDeckCard;

    public Shop(ShopType type){
        this.shopType = type;

        if(type.equals(ShopType.OBJECTIVE)) {
            cardsFile = UtilCostantValue.pathToObjectiveJson;
        } else if(type.equals(ShopType.RESOURCE)) {
            cardsFile = UtilCostantValue.pathToResourceJson;
        } else if(type.equals(ShopType.STARTER)) {
            cardsFile = UtilCostantValue.pathToStarterJson;
        } else {
            throw new RuntimeException("Errore inizializzazione shop di tipo: "+ type);
        }

        initializeCardDeck();
        this.topDeckCard = cardDeck.remove(0);
    }

    private void initializeCardDeck() {
        cardDeck = new ArrayList<>();
        Card suppCard;

        try {
            FileReader reader = new FileReader(cardsFile);

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(reader).getAsJsonArray();

            if(shopType.equals(ShopType.OBJECTIVE)) {
                for(JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();

                    int pngNumber = jsonObject.get("png").getAsInt();
                    ResourceType frontNorthResource = parseResourceType(jsonObject.get("frontNorthResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontNorthResource").getAsString()) : null;
                    ResourceType frontSouthResource = parseResourceType(jsonObject.get("frontSouthResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontSouthResource").getAsString()) : null;
                    ResourceType frontEastResource = parseResourceType(jsonObject.get("frontEastResource").getAsString());
                    ResourceType frontWestResource = parseResourceType(jsonObject.get("frontWestResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontWestResource").getAsString()) : null;
                    ResourceType backCentralResource = parseResourceType(jsonObject.get("backCentralResource").getAsString());
                    ConditionResourceType pointPerConditionResource = ConditionResourceType.valueOf(jsonObject.get("pointPerConditionResource").getAsString());
                    int pointPerCondition = jsonObject.get("pointPerCondition").getAsInt();

                    JsonArray supp = jsonObject.getAsJsonArray("conditionResource");
                    ResourceType[] conditionResource = new ResourceType[supp.size()];
                    for (int i = 0; i < supp.size(); i++) {
                        conditionResource[i] = ResourceType.valueOf(supp.get(i).getAsString());
                    }

                    suppCard = new ObjectiveCard(pngNumber, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource, backCentralResource,
                            pointPerConditionResource, pointPerCondition, conditionResource);
                    cardDeck.add(suppCard);
                }
            } else if(shopType.equals(ShopType.RESOURCE)) {
                for(JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();

                    int pngNumber = jsonObject.get("png").getAsInt();
                    ResourceType frontNorthResource = parseResourceType(jsonObject.get("frontNorthResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontNorthResource").getAsString()) : null;
                    ResourceType frontSouthResource = parseResourceType(jsonObject.get("frontSouthResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontSouthResource").getAsString()) : null;
                    ResourceType frontEastResource = parseResourceType(jsonObject.get("frontEastResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontEastResource").getAsString()) : null;
                    ResourceType frontWestResource = parseResourceType(jsonObject.get("frontWestResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontWestResource").getAsString()) : null;
                    ResourceType backCentralResource = parseResourceType(jsonObject.get("backCentralResource").getAsString());
                    int frontCardPoint = jsonObject.get("frontCardPoint").getAsInt();

                    suppCard = new ResourceCard(pngNumber, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource, backCentralResource, frontCardPoint);
                    cardDeck.add(suppCard);
                }
            } else if(shopType.equals(ShopType.STARTER)) {
                for(JsonElement element : jsonArray) {
                    JsonObject jsonObject = element.getAsJsonObject();

                    int pngNumber = jsonObject.get("png").getAsInt();
                    ResourceType frontNorthResource = parseResourceType(jsonObject.get("frontNorthResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontNorthResource").getAsString()) : null;
                    ResourceType frontSouthResource = parseResourceType(jsonObject.get("frontSouthResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontSouthResource").getAsString()) : null;
                    ResourceType frontEastResource = parseResourceType(jsonObject.get("frontEastResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontEastResource").getAsString()) : null;
                    ResourceType frontWestResource = parseResourceType(jsonObject.get("frontWestResource").getAsString()) != null ? parseResourceType(jsonObject.get("frontWestResource").getAsString()) : null;

                    JsonArray supp = jsonObject.getAsJsonArray("backCentralResource");
                    ResourceType[] backCentralResource = new ResourceType[supp.size()];
                    for (int i = 0; i < supp.size(); i++) {
                        backCentralResource[i] = ResourceType.valueOf(supp.get(i).getAsString());
                    }

                    ResourceType backNorthResource = parseResourceType(jsonObject.get("backNorthResource").getAsString()) != null ? parseResourceType(jsonObject.get("backNorthResource").getAsString()) : null;
                    ResourceType backSouthResource = parseResourceType(jsonObject.get("backSouthResource").getAsString()) != null ? parseResourceType(jsonObject.get("backSouthResource").getAsString()) : null;
                    ResourceType backEastResource = parseResourceType(jsonObject.get("backEastResource").getAsString()) != null ? parseResourceType(jsonObject.get("backEastResource").getAsString()) : null;
                    ResourceType backWestResource = parseResourceType(jsonObject.get("backWestResource").getAsString()) != null ? parseResourceType(jsonObject.get("backWestResource").getAsString()) : null;

                    suppCard = new StarterCard(pngNumber, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource, backCentralResource,
                            backNorthResource, backSouthResource, backEastResource, backWestResource);
                    cardDeck.add(suppCard);
                }
            }
        } catch (IOException e) {
            System.err.println("File JSON Card non trovato");
            e.printStackTrace();
            throw new RuntimeException("File JSON"+ cardsFile +" non trovato");
        }

        shuffleCardDeck();
    }

    private void shuffleCardDeck(){
        Collections.shuffle(cardDeck);
    }

    // dopo aver "pescato" la carta in cima al deck, lo rimpiazza con una carta nuova
    public Card drawTopDeckCard(){
        Card supp = topDeckCard;
        if(!cardDeck.isEmpty()) {
            topDeckCard = cardDeck.remove(0);
        } else {
            topDeckCard = null;
        }
        return supp;
    }

    // questo perchè .valueOf non può ritornare null quando ha null come argomento (comportamento desiderato), ma fa una throw
    public static ResourceType parseResourceType(String input) {
        return (input != null) ? ResourceType.valueOf(input) : null;
    }
}
