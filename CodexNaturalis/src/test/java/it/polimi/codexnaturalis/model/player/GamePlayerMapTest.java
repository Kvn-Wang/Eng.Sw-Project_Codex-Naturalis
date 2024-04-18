package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import it.polimi.codexnaturalis.model.shop.card.ResourceCard;
import it.polimi.codexnaturalis.model.shop.card.StarterCard;
import org.junit.jupiter.api.BeforeEach;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class GamePlayerMapTest {

    @BeforeEach
    void setUp() {
    }
    public static void main(String args[]){
        Scanner scanner = new Scanner(System.in);
        String inputString;
        new StarterCard(1,ResourceType.ANIMAL,ResourceType.PLANT,ResourceType.NONE, null, new ResourceType[]{ResourceType.ANIMAL, ResourceType.PLANT},ResourceType.PLANT,ResourceType.FUNGI,ResourceType.INSECT,ResourceType.ANIMAL);

    }
}