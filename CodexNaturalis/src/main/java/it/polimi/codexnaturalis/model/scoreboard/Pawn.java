package it.polimi.codexnaturalis.model.scoreboard;

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

    public String getImageFile() {
        return imageFile;
    }
}
