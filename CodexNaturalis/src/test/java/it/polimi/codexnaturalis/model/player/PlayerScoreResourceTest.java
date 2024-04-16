package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Test Add")
    @EnumSource(ResourceType.class)
    public void testAddScore(ResourceType resourceType) {
        if(resourceType == ResourceType.NONE) {
            assertThrows(IllegalArgumentException.class, () -> {playerScoreResource.addScore(ResourceType.NONE);});
        } else {
            playerScoreResource.addScore(resourceType);
            assertEquals(1, playerScoreResource.getScore(resourceType));

            ResourceType[] values = ResourceType.values();
            for(ResourceType i : values) {
                if(resourceType != i && i != ResourceType.NONE) {
                    assertEquals(0, playerScoreResource.getScore(i));
                }
            }

            playerScoreResource.addScore(resourceType);
            playerScoreResource.addScore(resourceType);
            playerScoreResource.addScore(resourceType);
            playerScoreResource.addScore(resourceType);
            assertEquals(5, playerScoreResource.getScore(resourceType));
        }
    }

    @Test
    @DisplayName("Test Subtract")
    public void testSubtractScore() {
        assertThrows(IllegalArgumentException.class, () -> {playerScoreResource.substractScore(ResourceType.NONE);});
        playerScoreResource.addScore(ResourceType.ANIMAL);
        playerScoreResource.substractScore(ResourceType.ANIMAL);
        assertEquals(0, playerScoreResource.getScore(ResourceType.ANIMAL));
        playerScoreResource.substractScore(ResourceType.QUILL);
        assertEquals(0, playerScoreResource.getScore(ResourceType.QUILL));

        assertThrows(IllegalArgumentException.class, () -> {playerScoreResource.substractScore(ResourceType.NONE);});

        playerScoreResource.addScore(ResourceType.FUNGI);
        playerScoreResource.substractScore(ResourceType.FUNGI);
        assertEquals(0, playerScoreResource.getScore(ResourceType.ANIMAL));
        playerScoreResource.addScore(ResourceType.FUNGI);
        playerScoreResource.addScore(ResourceType.FUNGI);
        playerScoreResource.addScore(ResourceType.FUNGI);
        playerScoreResource.substractScore(ResourceType.FUNGI);
        assertEquals(2, playerScoreResource.getScore(ResourceType.FUNGI));
    }

    @Test
    @DisplayName("Test Inizializzazione")
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