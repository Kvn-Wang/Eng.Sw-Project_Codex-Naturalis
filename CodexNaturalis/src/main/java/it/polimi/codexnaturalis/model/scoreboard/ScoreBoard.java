package it.polimi.codexnaturalis.model.scoreboard;

import it.polimi.codexnaturalis.model.enumeration.ColorType;
import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {
    private final String scoreCardImg;
    private List<Pawn> pawns;

    public ScoreBoard(Player[] players){
        pawns = new ArrayList<Pawn>();
        String supp;

        for(Player p: players){
            supp = p.getPawnColor();
            if(supp.equals(ColorType.RED)) {
                pawns.add(new Pawn(p, UtilCostantValue.pathToRedPawnImg));
            } else if(supp.equals(ColorType.YELLOW)) {
                pawns.add(new Pawn(p, UtilCostantValue.pathToYellowPawnImg));
            } else if(supp.equals(ColorType.GREEN)) {
                pawns.add(new Pawn(p, UtilCostantValue.pathToGreenPawnImg));
            } else if(supp.equals(ColorType.BLUE)) {
                pawns.add(new Pawn(p, UtilCostantValue.pathToBluePawnImg));
            } else {
                System.err.println("Initialization Error Scoreboard, invalid selected player color");
            }

        }
        scoreCardImg = UtilCostantValue.pathToScoreCardImg;
    }

    public boolean checkEnd20(Player player){
        return player.getPersonalScore() >= 20;
    }

    public List<Pawn> getpawns(){
        return pawns;
    }
}
