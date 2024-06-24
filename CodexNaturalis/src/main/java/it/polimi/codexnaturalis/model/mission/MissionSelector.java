package it.polimi.codexnaturalis.model.mission;

import com.google.gson.*;
import it.polimi.codexnaturalis.model.enumeration.CardCorner;
import it.polimi.codexnaturalis.model.enumeration.MissionType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.utils.UtilCostantValue;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * The type Mission selector.
 */
public class MissionSelector {
    private String missionFile;
    private ArrayList<Mission> missions;

    /**
     * Instantiates a new Mission selector.
     */
    public MissionSelector() {
        System.out.println("inizializzato");
        missions = new ArrayList<>();
        missionFile = UtilCostantValue.pathToMissionJson;
        initializeArrayMissions();
    }

    /**
     * Gets missions.
     *
     * @return the missions
     */
    public ArrayList<Mission> getMissions() {
        return missions;
    }

    private void initializeArrayMissions() {
        Mission mission;

        try {
            FileReader reader = new FileReader(missionFile);

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(reader).getAsJsonArray();

            for(JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();

                String typeOfMission = jsonObject.get("typeOfMission").getAsString();
                if(typeOfMission.equals(MissionType.DIAGONAL.name())) {
                    int pngNumber = jsonObject.get("png").getAsInt();
                    int pointPerCondition = jsonObject.get("pointPerCondition").getAsInt();
                    boolean isLeftToRight = jsonObject.get("isLeftToRight").getAsBoolean();
                    ResourceType typeOfResource = ResourceType.valueOf(jsonObject.get("resourceType").getAsString());

                    mission = new DiagonalMission(pngNumber, pointPerCondition, isLeftToRight, typeOfResource);
                    missions.add(mission);
                } else if(typeOfMission.equals(MissionType.BEND.name())) {
                    int pngNumber = jsonObject.get("png").getAsInt();
                    int pointPerCondition = jsonObject.get("pointPerCondition").getAsInt();
                    ResourceType pillarResource = ResourceType.valueOf(jsonObject.get("pillarResource").getAsString());
                    ResourceType decorationResource = ResourceType.valueOf(jsonObject.get("decorationResource").getAsString());
                    CardCorner decorationPosition = CardCorner.valueOf(jsonObject.get("decorationPosition").getAsString());

                    mission = new BendMission(pngNumber, pointPerCondition, pillarResource, decorationResource, decorationPosition);
                    missions.add(mission);
                } else if(typeOfMission.equals(MissionType.RESOURCE.name())) {
                    int pngNumber = jsonObject.get("png").getAsInt();
                    int pointPerCondition = jsonObject.get("pointPerCondition").getAsInt();
                    int numberOfSymbols = jsonObject.get("numberOfSymbols").getAsInt();

                    JsonArray supp = jsonObject.getAsJsonArray("typeOfResource");
                    ResourceType[] typeOfResource = new ResourceType[supp.size()];
                    for (int i = 0; i < supp.size(); i++) {
                        typeOfResource[i] = ResourceType.valueOf(supp.get(i).getAsString());
                    }

                    mission = new ResourceMission(pngNumber, pointPerCondition, numberOfSymbols, typeOfResource);
                    missions.add(mission);
                } else {
                    throw new RuntimeException("File JSON Mission Configurato male, "+ typeOfMission +" missionType non valido");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("File JSON Mission non trovato");
        }

        shuffleMissions();
    }

    private void shuffleMissions() {
        Collections.shuffle(missions);
    }

    /**
     * Draw from file mission.
     *
     * @return the mission
     */
    public Mission drawFromFile() {
        return missions.remove(0);
    }
}
