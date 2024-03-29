package it.polimi.codexnaturalis.model.scoreCard;

import it.polimi.codexnaturalis.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ScoreCard {
    private final String scoreCardImg;
    private List<Pawn> pawns;
    public boolean checkEnd20(Player player){
        return player.getPersonalScore() >= 20;
    }

    public ScoreCard(Player[] players){
        pawns = new ArrayList<Pawn>();
        for(Player p: players){
            switch (p.getPawnColor()) {
                case "red":
                    pawns.add(new Pawn(p, "path red"));
                    break;
                case "yellow":
                    pawns.add(new Pawn(p, "path yellow"));
                    break;
                case "green":
                    pawns.add(new Pawn(p, "path green"));
                    break;
                case "blue":
                    pawns.add(new Pawn(p, "path blue"));
                    break;
            }
        }
        scoreCardImg = "CodexNaturalis/src/main/resources/it/polimi/codexnaturalis/graphics/PLATEAU-SCORE-IMP/Scoreboard.png";
    }

    public List<Pawn> getpawns(){
        return pawns;
    }
}
