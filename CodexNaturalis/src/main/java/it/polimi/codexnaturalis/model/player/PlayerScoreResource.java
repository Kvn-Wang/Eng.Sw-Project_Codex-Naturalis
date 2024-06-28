package it.polimi.codexnaturalis.model.player;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;

/**
 * The type Player score resource.
 */
public class PlayerScoreResource {
    private int scoreAnimal;
    private int scoreFungi;
    private int scorePlant;
    private int scoreInsect;
    private int scoreInkwell;
    private int scoreManuscript;
    private int scoreQuill;

    /**
     * Instantiates a new Player score resource.
     */
    public PlayerScoreResource() {
        scoreAnimal = 0;
        scoreFungi = 0;
        scorePlant = 0;
        scoreInsect = 0;
        scoreInkwell = 0;
        scoreManuscript = 0;
        scoreQuill = 0;
    }

    /**
     * Instantiates a new Player score resource.
     * constructor used to make a copy of the same obj
     *
     * @param original the original
     */
    public PlayerScoreResource(PlayerScoreResource original) {
        this.scoreAnimal = original.getScoreAnimal();
        this.scoreFungi = original.getScoreFungi();
        this.scorePlant = original.getScorePlant();
        this.scoreInsect = original.getScoreInsect();
        this.scoreInkwell = original.getScoreInkwell();
        this.scoreManuscript = original.getScoreManuscript();
        this.scoreQuill = original.getScoreQuill();
    }

    /**
     * Gets score.
     *
     * @param type the type
     * @return the score
     */
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

    /**
     * Substract score boolean.
     * rimozione di risorsa una per volta, ritorna falso in caso la risorsa sia già = 0 e non si possa fare -1;
     *
     * @param type the type
     * @return the boolean
     */
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
                System.err.println("Requested illegal type of resource: "+ type);
                throw new IllegalArgumentException("Errore richiesta tipo di risorsa");
        }

        return true;
    }

    /**
     * Add score.
     * aggiunta di risorsa una per volta
     *
     * @param type the type
     */
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
                throw new IllegalArgumentException("Errore richiesta tipo di risorsa, richiesto: "+ type);
        }
    }

    /**
     * Gets score animal.
     *
     * @return the score animal
     */
    public int getScoreAnimal() {
        return scoreAnimal;
    }

    /**
     * Gets score fungi.
     *
     * @return the score fungi
     */
    public int getScoreFungi() {
        return scoreFungi;
    }

    /**
     * Gets score plant.
     *
     * @return the score plant
     */
    public int getScorePlant() {
        return scorePlant;
    }

    /**
     * Gets score insect.
     *
     * @return the score insect
     */
    public int getScoreInsect() {
        return scoreInsect;
    }

    /**
     * Gets score inkwell.
     *
     * @return the score inkwell
     */
    public int getScoreInkwell() {
        return scoreInkwell;
    }

    /**
     * Gets score manuscript.
     *
     * @return the score manuscript
     */
    public int getScoreManuscript() {
        return scoreManuscript;
    }

    /**
     * Gets score quill.
     *
     * @return the score quill
     */
    public int getScoreQuill() {
        return scoreQuill;
    }
}
