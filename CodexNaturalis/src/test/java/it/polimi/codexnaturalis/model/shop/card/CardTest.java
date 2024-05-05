package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @BeforeEach
    void setUp() {
    }
    Card testCard;
    @Test
    public void testCard(){
        int png = 1;
        ResourceType north = ResourceType.NONE;
        ResourceType south = ResourceType.ANIMAL;
        ResourceType east = ResourceType.FUNGI;
        ResourceType west = null;
        Card testCard = new Card(png, north,south,east,west) {
            @Override
            public ResourceType getColor() {
                return null;
            }

            @Override
            public boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard) {
                return false;
            }

            @Override
            public ArrayList<ResourceType> getCardResources() {
                return null;
            }

            @Override
            public int getCardPoints(PlayerScoreResource scoreCard, int neightbouringCard) {
                return 0;
            }

            @Override
            protected ResourceType getBackNorthResource() {
                return null;
            }

            @Override
            protected ResourceType getBackSouthResource() {
                return null;
            }

            @Override
            protected ResourceType getBackEastResource() {return null;}

            @Override
            protected ResourceType getBackWestResource() {
                return null;
            }

            @Override
            public CardType getCardType() {
                return null;
            }
        };
    }
}