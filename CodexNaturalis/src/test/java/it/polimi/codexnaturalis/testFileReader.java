package it.polimi.codexnaturalis;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.enumeration.ShopType;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;

public class testFileReader {
    @Test
    public void testFileReader() {
        String  path ="C:/Users/cipol/IdeaProjects/IS24-AM47/CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/resourceCardsFile.json";

        String relativePath = "src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/resourceCardsFile.json";
        String cardsFile = "/CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/matchCardFileInfo/resourceCardsFile.json";
        try {
            FileReader reader = new FileReader(relativePath);

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(reader).getAsJsonArray();


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("File JSON: "+ cardsFile +" non trovato");
        }
    }
}
