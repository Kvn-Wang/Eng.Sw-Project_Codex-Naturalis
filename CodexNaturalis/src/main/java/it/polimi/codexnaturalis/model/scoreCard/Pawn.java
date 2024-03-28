package it.polimi.codexnaturalis.model.scoreCard;

import it.polimi.codexnaturalis.model.player.Player;

public class Pawn {
    private final Player player;
    private final String imageFile;

    public Pawn(Player user, String filePath){
        player = user;
        imageFile = filePath;
    }

    public Player getPlayer() {
        return player;
    }
}
