package it.polimi.codexnaturalis.model.shop;
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
import it.polimi.codexnaturalis.network.util.networkMessage.MessageType;
import it.polimi.codexnaturalis.network.util.networkMessage.NetworkMessage;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import it.polimi.codexnaturalis.utils.observer.Observable;
import it.polimi.codexnaturalis.utils.observer.Observer;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Shop.
 */
public class Shop extends Observable {
    /**
     * The Shop type.
     */
    public final ShopType shopType;
    /**
     * The Cards file.
     */
    protected final String cardsFile;
    private ArrayList<Card> cardDeck;
    /**
     * The Top deck card.
     */
    protected Card topDeckCard;

    /**
     * Instantiates a new Shop.
     *
     * @param type     the type
     * @param observer the observer
     */
    public Shop(ShopType type, Observer observer){
        this.shopType = type;

        if(type.equals(ShopType.OBJECTIVE)) {
            cardsFile = "/it/polimi/codexnaturalis/matchCardFileInfo/objectiveCardsFile.json";
        } else if(type.equals(ShopType.RESOURCE)) {
            cardsFile = "/it/polimi/codexnaturalis/matchCardFileInfo/resourceCardsFile.json";
        } else if(type.equals(ShopType.STARTER)) {
            cardsFile = "/it/polimi/codexnaturalis/matchCardFileInfo/starterCardsFile.json";
        } else {
            throw new RuntimeException("Errore inizializzazione shop di tipo: "+ type);
        }

        initializeCardDeck();
        this.topDeckCard = cardDeck.remove(0);
        addObserver(observer);
    }

    private void initializeCardDeck() {
        cardDeck = new ArrayList<>();
        Card suppCard;

        // Usa getResourceAsStream per ottenere l'InputStream del file JSON
        InputStream inputStream = getClass().getResourceAsStream(cardsFile);
        if (inputStream == null) {
            throw new RuntimeException("File JSON Mission non trovato");
        }
        InputStreamReader reader = new InputStreamReader(inputStream);

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

        shuffleCardDeck();
    }

    private void shuffleCardDeck(){
        Collections.shuffle(cardDeck);
    }

    /**
     * Draw top deck card card.
     * dopo aver "pescato" la carta in cima al deck, lo rimpiazza con una carta nuova
     * questa funzione verrà chiamata solo da starterShop o durante la fase di setup quando si danno le carte iniziali
     * quindi non serve notificare alcun utente
     * le altre volte per cui viene chiamato, è da generalShop e gestisce già lui le notifiche a utente
     *
     * @return the card
     */
    public Card drawTopDeckCard(){
        return cardDeck.remove(0);
    }

    /**
     * Parse resource type resource type.
     * questo perchè .valueOf non può ritornare null quando ha null come argomento (comportamento desiderato), ma fa una throw
     *
     * @param input the input
     * @return the resource type
     */
    public static ResourceType parseResourceType(String input) {
        return (input != null) ? ResourceType.valueOf(input) : null;
    }

    /**
     * Gets top deck card.
     *
     * @return the top deck card
     */
    public Card getTopDeckCard() {
        return topDeckCard;
    }
}
