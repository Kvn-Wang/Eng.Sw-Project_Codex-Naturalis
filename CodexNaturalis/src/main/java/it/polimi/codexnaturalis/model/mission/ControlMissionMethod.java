package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.player.PlayerScore;
import it.polimi.codexnaturalis.model.shop.card.Card;

public interface ControlMissionMethod {
    int ruleAlgorithmCheck(Card [][] mapArray);
    int ruleAlgorithmCheck(PlayerScore playerScore);
}
