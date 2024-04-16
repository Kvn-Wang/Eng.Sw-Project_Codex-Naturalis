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
    //constructor used to make a copy of the same obj
    public PlayerScoreResource(PlayerScoreResource original) {
        this.scoreAnimal = original.getScoreAnimal();
        this.scoreFungi = original.getScoreFungi();
        this.scorePlant = original.getScorePlant();
        this.scoreInsect = original.getScoreInsect();
        this.scoreInkwell = original.getScoreInkwell();
        this.scoreManuscript = original.getScoreManuscript();
        this.scoreQuill = original.getScoreQuill();
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
                throw new IllegalArgumentException("Errore richiesta tipo di risorsa");
        }
    }

    // rimozione di risorsa una per volta, ritorna falso in caso la risorsa sia già = 0 e non si possa fare -1;
    public boolean substractScore(ResourceType type) {
        switch(type){
            case ANIMAL:
                if(scoreAnimal > 0) {
                    scoreAnimal-=1;
                } else {
                    return false;
                }
                break;

            case FUNGI:
                if(scoreFungi > 0) {
                    scoreFungi-=1;
                } else {
                    return false;
                }
                break;

            case PLANT:
                if(scorePlant > 0) {
                    scorePlant-=1;
                } else {
                    return false;
                }
                break;

            case INSECT:
                if(scoreInsect > 0) {
                    scoreInsect-=1;
                } else {
                    return false;
                }
                break;

            case INKWELL:
                if(scoreInkwell > 0) {
                    scoreInkwell-=1;
                } else {
                    return false;
                }
                break;

            case MANUSCRIPT:
                if(scoreManuscript > 0) {
                    scoreManuscript-=1;
                } else {
                    return false;
                }
                break;

            case QUILL:
                if(scoreQuill > 0) {
                    scoreQuill-=1;
                } else {
                    return false;
                }
                break;

            default:
                throw new IllegalArgumentException("Errore richiesta tipo di risorsa");
        }

        return true;
    }

    // aggiunta di risorsa una per volta
    public void addScore(ResourceType type) {
        switch(type){
            case ANIMAL:
                scoreAnimal+=1;
                break;
            case FUNGI:
                scoreFungi+=1;
                break;
            case PLANT:
                scorePlant+=1;
                break;
            case INSECT:
                scoreInsect+=1;
                break;
            case INKWELL:
                scoreInkwell+=1;
                break;
            case MANUSCRIPT:
                scoreManuscript+=1;
                break;
            case QUILL:
                scoreQuill+=1;
                break;
            default:
                throw new IllegalArgumentException("Errore richiesta tipo di risorsa");
        }
    }

    public int getScoreAnimal() {
        return scoreAnimal;
    }

    public int getScoreFungi() {
        return scoreFungi;
    }

    public int getScorePlant() {
        return scorePlant;
    }

    public int getScoreInsect() {
        return scoreInsect;
    }

    public int getScoreInkwell() {
        return scoreInkwell;
    }

    public int getScoreManuscript() {
        return scoreManuscript;
    }

    public int getScoreQuill() {
        return scoreQuill;
    }
}
