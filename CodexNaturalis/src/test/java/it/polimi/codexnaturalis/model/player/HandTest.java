package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ConditionResourceType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ObjectiveCard;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import it.polimi.codexnaturalis.utils.PersonalizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/*class HandTest {
    StarterCard starterCard = new StarterCard(81, ResourceType.NONE, ResourceType.NONE, ResourceType.PLANT, ResourceType.INSECT, new ResourceType[]{ResourceType.INSECT}, ResourceType.FUNGI, ResourceType.ANIMAL, ResourceType.PLANT, ResourceType.INSECT);
    ResourceCard firstTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);
    ObjectiveCard thirdTestCard = new ObjectiveCard(41, null, ResourceType.QUILL, ResourceType.NONE, ResourceType.NONE, ResourceType.FUNGI, ConditionResourceType.QUILL, 1, new ResourceType[]{ResourceType.FUNGI, ResourceType.FUNGI, ResourceType.ANIMAL});
    ResourceCard secondTestCard = new ResourceCard(1, ResourceType.FUNGI, null, ResourceType.NONE, ResourceType.FUNGI, ResourceType.FUNGI, 0);

    @BeforeEach

    @Test
    void testHand(){
        Hand testHand = new Hand();
        for(int i=0; i<3; i++){
            Card card = null;
            int pos=i;
            assertThrows(PersonalizedException.InvalidPopCardException.class, ()-> testHand.popCard(pos));
        }
    }
    @Test
    public void testAddCard() throws PersonalizedException.InvalidAddCardException {
        Hand testHand = new Hand();
        testHand.addCard(starterCard);
        testHand.addCard(firstTestCard);
        testHand.addCard(thirdTestCard);
        assertThrows(PersonalizedException.InvalidAddCardException.class, ()->     testHand.addCard(secondTestCard));
        ArrayList<Card> testCards = testHand.getCards();
        for(int i=0; i<3; i++){
            int pos = i;
            //System.out.println(testCards[pos]);
        }
    }

    @Test
    public void testPopCard() throws PersonalizedException.InvalidAddCardException, PersonalizedException.InvalidPopCardException, PersonalizedException.InvalidNumPopCardException {
        Hand testHand = new Hand();
        testHand.addCard(starterCard);
        testHand.addCard(firstTestCard);
        testHand.addCard(thirdTestCard);
        Card[] testCards = testHand.getCards();
        for(int i=0; i<3; i++){
            int pos = i;
            System.out.println(testCards[pos]);
        }
        System.out.println("\n");
        testHand.popCard(0);
        for(int i=0; i<3; i++){
            int pos = i;
            System.out.println(testCards[pos]);
        }
        assertThrows(PersonalizedException.InvalidNumPopCardException.class, ()-> testHand.popCard(4));
        assertThrows(PersonalizedException.InvalidPopCardException.class, ()-> testHand.popCard(0));
    }
}*/