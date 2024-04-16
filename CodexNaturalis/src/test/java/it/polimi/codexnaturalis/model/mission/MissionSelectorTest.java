package it.polimi.codexnaturalis.model.mission;

import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MissionSelectorTest {
    private MissionSelector missionSelector;

    @BeforeEach
    public void setUp() {
        missionSelector = new MissionSelector();
    }

    @Test
    void shuffle() {
        for(Mission missionElement : missionSelector.getMissions()) {
            System.out.println(missionElement);
            System.out.println(missionElement.pngNumber);
        }
    }

    @Test
    void drawFromFile() {
        for(Mission element : missionSelector.getMissions()) {
            if(element.getPointPerCondition() <= 0) {
                fail("Errore inizializzazione di missione, PointPerCondition non è valido");
            }
            if(element instanceof DiagonalMission diagonalMission) {
                if(diagonalMission.getResourceType() == null) {
                    fail("Errore inizializzazione di missione, qualche campo è null");
                }
            } else if(element instanceof BendMission bendMission) {
                if(bendMission.getDecorationPosition() == null || bendMission.getDecorationResource() == null ||
                        bendMission.getPillarResource() == null) {
                    fail("Errore inizializzazione di missione, qualche campo è null");
                }
            } else if(element instanceof ResourceMission diagonalMission) {
                if(diagonalMission.getNumberOfSymbols() <= 0 || diagonalMission.getTypeOfResource() == null) {
                    fail("Errore inizializzazione di missione, qualche campo è null o non validp");
                }
            } else {
                fail("Richiesto missione di tipo non valido: "+ element.getClass());
            }
        }
    }
}