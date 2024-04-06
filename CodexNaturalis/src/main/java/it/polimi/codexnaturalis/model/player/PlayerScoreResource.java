package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;

public class PlayerScoreResource {
    private int scoreAnimal;
    private int scoreFungi;
    private int scorePlant;
    private int scoreInsect;
    private int scoreInkwell;
    private int scoreManuscript;
    private int scoreQuill;

    public PlayerScoreResource() {
        scoreAnimal = 0;
        scoreFungi = 0;
        scorePlant = 0;
        scoreInsect = 0;
        scoreInkwell = 0;
        scoreManuscript = 0;
        scoreQuill = 0;
    }

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
                System.err.println("Errore richiesta tipo di risorsa");
                return -1;
        }
    }
    // aggiunta di risorsa una per volta
    public void addScore(ResourceType type) {
        switch(type){
            case ANIMAL:
                scoreAnimal-=1;
            case FUNGI:
                scoreFungi-=1;
            case PLANT:
                scorePlant-=1;
            case INSECT:
                scoreInsect-=1;
            case INKWELL:
                scoreInkwell-=1;
            case MANUSCRIPT:
                scoreManuscript-=1;
            case QUILL:
                scoreQuill-=1;
        }
    }

    // rimozione di risorsa una per volta
    public void substract(ResourceType type) {
        switch(type){
            case ANIMAL:
                scoreAnimal+=1;
            case FUNGI:
                scoreFungi+=1;
            case PLANT:
                scorePlant+=1;
            case INSECT:
                scoreInsect+=1;
            case INKWELL:
                scoreInkwell+=1;
            case MANUSCRIPT:
                scoreManuscript+=1;
            case QUILL:
                scoreQuill+=1;
        }
    }
}
