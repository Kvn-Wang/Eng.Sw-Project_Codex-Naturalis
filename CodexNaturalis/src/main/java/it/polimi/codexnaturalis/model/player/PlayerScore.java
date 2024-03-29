package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;

public class PlayerScore {
    private int scoreAnimal;
    private int scoreFungi;
    private int scorePlant;
    private int scoreInsect;
    private int scoreInkwell;
    private int scoreManuscript;
    private int scoreQuill;

    public int getScore(ResourceType type) {
        return 0;
    }

    public void addScore(ResourceType type) {}
    public void substract(ResourceType type) {}
}
