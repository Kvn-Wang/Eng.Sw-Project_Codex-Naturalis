package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

class PlayerScoreResourceTest {
    private PlayerScoreResource playerScoreResource;

    @BeforeEach
    public void setUp() {
        playerScoreResource = new PlayerScoreResource();
    }

    //Test aggiunta di risorsa per ogni tipo di risorsa
    @ParameterizedTest
    @EnumSource(ResourceType.class)
    public void testAddScore(ResourceType resourceType) {
        System.out.println("aggiungo risorsa tipo: " +resourceType);
        if(resourceType != ResourceType.NONE) {
            playerScoreResource.addScore(resourceType);
            assertEquals(1, playerScoreResource.getScore(resourceType));
            assertNotEquals(0, playerScoreResource.getScore(resourceType));
        }
    }

    @Test
    public void testSubtractScore() {
        playerScoreResource.addScore(ResourceType.ANIMAL);
        playerScoreResource.substractScore(ResourceType.ANIMAL);
        assertEquals(0, playerScoreResource.getScore(ResourceType.ANIMAL));
        playerScoreResource.substractScore(ResourceType.QUILL);
        assertEquals(0, playerScoreResource.getScore(ResourceType.QUILL));
    }

    @Test
    public void testGetScore() {
        assertEquals(0, playerScoreResource.getScore(ResourceType.ANIMAL));
        assertEquals(0, playerScoreResource.getScore(ResourceType.FUNGI));
        assertEquals(0, playerScoreResource.getScore(ResourceType.PLANT));
        assertEquals(0, playerScoreResource.getScore(ResourceType.INSECT));
        assertEquals(0, playerScoreResource.getScore(ResourceType.INKWELL));
        assertEquals(0, playerScoreResource.getScore(ResourceType.MANUSCRIPT));
        assertEquals(0, playerScoreResource.getScore(ResourceType.QUILL));

        playerScoreResource.addScore(ResourceType.ANIMAL);
        playerScoreResource.addScore(ResourceType.ANIMAL);
        playerScoreResource.addScore(ResourceType.ANIMAL);
        assertEquals(3, playerScoreResource.getScore(ResourceType.ANIMAL));
    }
}