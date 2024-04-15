package it.polimi.codexnaturalis.model.mission;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import it.polimi.codexnaturalis.model.enumeration.MissionType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.utils.UtilCostantValue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MissionSelector {
    private String missionFile;
    private ArrayList<Mission> missions;

    public String getMissionFile() {
        return missionFile;
    }

    public void setMissionFile(String missionFile) {
        this.missionFile = missionFile;
    }

    public ArrayList<Mission> getMissions() {
        return missions;
    }

    public void setMissions(ArrayList<Mission> missions) {
        this.missions = missions;
    }

    public MissionSelector() {
        missionFile = UtilCostantValue.pathToMissionJson;
    }

    public void shuffle() {
        missions = new ArrayList<>();
        Mission mission;
        Gson gson = new Gson();

        try {
            FileReader reader = new FileReader(missionFile);

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(reader).getAsJsonArray();

            for(JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();

                String typeOfMission = jsonObject.get("typeOfMission").getAsString();
                if(typeOfMission.equals(MissionType.DIAGONAL.name())) {
                    String pngNumber = jsonObject.get("png").getAsString();
                    int pointPerCondition = jsonObject.get("pointPerCondition").getAsInt();
                    boolean isLeftToRight = jsonObject.get("isLeftToRight").getAsBoolean();
                    ResourceType typeOfResource = ResourceType.valueOf(jsonObject.get("resourceType").getAsString());

                    mission = new DiagonalMission(pngNumber, pointPerCondition, isLeftToRight, typeOfResource);
                    missions.add(mission);
                } else if(typeOfMission.equals(MissionType.BEND.name())) {
                    String pngNumber = jsonObject.get("png").getAsString();
                    int pointPerCondition = jsonObject.get("pointPerCondition").getAsInt();
                    ResourceType pillarResource = ResourceType.valueOf(jsonObject.get("pillarResource").getAsString());
                    ResourceType decorationResource = ResourceType.valueOf(jsonObject.get("decorationResource").getAsString());
                    String decorationPosition = jsonObject.get("decorationPosition").getAsString();

                    mission = new BendMission(pngNumber, pointPerCondition, pillarResource, decorationResource, decorationPosition);
                    missions.add(mission);
                } else if(typeOfMission.equals(MissionType.RESOURCE.name())) {
                    String pngNumber = jsonObject.get("png").getAsString();
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

            Collections.shuffle(missions);

        } catch (IOException e) {
            System.err.println("File JSON Mission non trovato");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Mission drawFromFile() {
        Mission drawnMission = null;
        return drawnMission;
    }
}
