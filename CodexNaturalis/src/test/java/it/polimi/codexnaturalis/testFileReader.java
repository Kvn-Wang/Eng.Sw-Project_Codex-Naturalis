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
import static org.junit.jupiter.api.Assertions.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class testFileReader {
    @Test
    public void testFileReader() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL testFileUrl = getClass().getClassLoader().getResource(UtilCostantValue.pathToResourceJson);
        System.out.println(testFileUrl);
        try {
            FileReader reader = new FileReader(UtilCostantValue.pathToResourceJson);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    ObjectiveCard thirdTestCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});

    @Test
    public void test() throws IOException {
        int num = 3;
        /*try {
            URL name = getClass().getResource("/it/polimi/codexnaturalis/graphics/Pawn_Black.png");
            //BufferedImage image = ImageIO.read(getClass().getResource(".CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/graphics/Pawn_Black.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        URL name = getClass().getResource("/it/polimi/codexnaturalis/graphics/Pawn_Black.png");
        BufferedImage image = ImageIO.read(name);
        System.out.println(name);
        String imageName = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/3.png";
        String imageName2 = "/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/" + num + ".png";
        assertEquals(true , imageName.equals(imageName2));
        URL name2 = getClass().getResource(imageName2);
        BufferedImage image4 = ImageIO.read(name2);
    }

}
