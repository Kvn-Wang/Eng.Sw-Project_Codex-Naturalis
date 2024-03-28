package it.polimi.codexnaturalis.model.scoreCard;

import it.polimi.codexnaturalis.model.player.Player;

import java.util.List;

public class ScoreCard {
    private final String scoreCardImg;
    private List<Pawn> pawns;
    public boolean checkEnd20(Player player){
        return player.getPersonalScore() >= 20;
    }

    public ScoreCard(List<Player> players, String color){
        for(Player p: players){
            switch (color) {
                case "red":
                    pawns.add(new Pawn(p, "path red"));
                    break;
                case "Yellow":
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
        scoreCardImg = "path to file";
    }
}
