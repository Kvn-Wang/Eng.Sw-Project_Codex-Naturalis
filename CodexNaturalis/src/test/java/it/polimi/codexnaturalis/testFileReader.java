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
    public void test(){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
