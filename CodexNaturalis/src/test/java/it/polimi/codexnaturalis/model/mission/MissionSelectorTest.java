package it.polimi.codexnaturalis.model.mission;

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
        missionSelector.shuffle();
        for(Mission missionElement : missionSelector.getMissions()) {
            System.out.println(missionElement);
            System.out.println(missionElement.pngNumber);
        }
    }

    @Test
    void drawFromFile() {
    }
}