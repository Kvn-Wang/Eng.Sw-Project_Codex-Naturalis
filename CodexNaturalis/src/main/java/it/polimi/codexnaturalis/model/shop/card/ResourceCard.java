package it.polimi.codexnaturalis.model.shop.card;

import it.polimi.codexnaturalis.model.enumeration.CardType;
import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.player.GamePlayerMap;
import it.polimi.codexnaturalis.model.player.PlayerScoreResource;

import java.util.ArrayList;

public class ResourceCard extends Card{
    protected ResourceType backCentralResource;
    protected int frontCardPoint;

    public ResourceCard(int png, ResourceType frontNorthResource, ResourceType frontSouthResource, ResourceType frontEastResource, ResourceType frontWestResource,
                        ResourceType backCentralResource, int frontCardPoint) {
        super(png, frontNorthResource, frontSouthResource, frontEastResource, frontWestResource);
        this.backCentralResource = backCentralResource;
        this.frontCardPoint = frontCardPoint;
    }

    public int getFrontCardPoint() {
        return frontCardPoint;
    }

    public ResourceType getColor() {
        return backCentralResource;
    }

    @Override
    public boolean checkPlaceableCardCondition(PlayerScoreResource scoreCard) {
        return true;
    }

    @Override
    public ArrayList<ResourceType> getCardResources() {
        ArrayList<ResourceType> temp = new ArrayList<>();

        if(isBack) {
            if(checkValidResource(frontNorthResource)) {
                temp.add(frontNorthResource);
            }
            if(checkValidResource(frontSouthResource)) {
                temp.add(frontSouthResource);
            }
            if(checkValidResource(frontEastResource)) {
                temp.add(frontEastResource);
            }
            if(checkValidResource(frontWestResource)) {
                temp.add(frontWestResource);
            }
        } else {
            if(checkValidResource(backCentralResource)) {
                temp.add(backCentralResource);
            }
        }
        return temp;
    }

    @Override
    public int getCardPoints(PlayerScoreResource scoreCard, int neightbouringCard) {
        if(isBack) {
            return 0;
        } else {
            return frontCardPoint;
        }
    }

    @Override
    protected ResourceType getBackNorthResource() {
        return ResourceType.NONE;
    }

    @Override
    protected ResourceType getBackSouthResource() {
        return ResourceType.NONE;
    }

    @Override
    protected ResourceType getBackEastResource() {
        return ResourceType.NONE;
    }

    @Override
    protected ResourceType getBackWestResource() {
        return ResourceType.NONE;
    }

    @Override
    public CardType getCardType() {
        return CardType.RESOURCE;
    }
}
