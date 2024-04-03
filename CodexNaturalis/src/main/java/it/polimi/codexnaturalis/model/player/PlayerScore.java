package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;

import java.util.spi.ResourceBundleControlProvider;

public class PlayerScore {
    private int scoreAnimal;
    private int scoreFungi;
    private int scorePlant;
    private int scoreInsect;
    private int scoreInkwell;
    private int scoreManuscript;
    private int scoreQuill;

    public int getScore(ResourceType type) {
        switch(type){
            case ANIMAL:
                return scoreAnimal;
            case FUNGI:
                return scoreFungi;
            case PLANT:
                return scorePlant;
            case INSECT:
                return scoreInsect;
            case INKWELL:
                return scoreInkwell;
            case MANUSCRIPT:
                return scoreManuscript;
            case QUILL:
                return scoreQuill;
            default:
                return -1;
        }

    }

    public void addScore(ResourceType type) {}
    public void substract(ResourceType type) {}
}
