package it.polimi.codexnaturalis.view.GUI;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GuiCard {
    private double orgTranslateX;
    private double orgTranslateY;
    private double orgSceneX;
    private double orgSceneY;
    private Rectangle vCard;
    public Rectangle getRectangle() {
        return vCard;
    }
    public GuiCard(Card card, Circle[][] matrix) {
        double x=160;
        double y=100;
        switch (card.getColor()){
            case ResourceType.FUNGI:
                vCard = new Rectangle(x, y, Color.ORANGE);
                break;
            case ResourceType.ANIMAL:
                vCard = new Rectangle(x, y, Color.BLUE);
                break;
            case ResourceType.INSECT:
                vCard = new Rectangle(x, y, Color.PURPLE);
                break;
            case ResourceType.PLANT:
                vCard = new Rectangle(x, y, Color.GREEN);
                break;
            case ResourceType.NONE:
                vCard = new Rectangle(x, y, Color.SNOW);
                break;
        }



        vCard.setOnMousePressed((MouseEvent event) -> {
            orgSceneX = event.getSceneX();
            orgSceneY = event.getSceneY();
            orgTranslateX = vCard.getTranslateX();
            orgTranslateY = vCard.getTranslateY();
        });

        vCard.setOnMouseDragged((MouseEvent event) -> {
            double offsetX = event.getSceneX() - orgSceneX;
            double offsetY = event.getSceneY() - orgSceneY;
            vCard.setTranslateX(orgTranslateX + offsetX);
            vCard.setTranslateY(orgTranslateY + offsetY);
        });

        vCard.setOnMouseReleased((MouseEvent event) -> {
            // Snap to the nearest anchor point if close enough
            double closestDistance = Double.MAX_VALUE;
            Circle closestAnchor = null;

            for(Circle[] rows : matrix) {
                for (Circle anchor : rows) {
                    double distance = Math.hypot(
                            anchor.getCenterX() - (vCard.getTranslateX() + vCard.getBoundsInLocal().getCenterX()),
                            anchor.getCenterY() - (vCard.getTranslateY() + vCard.getBoundsInLocal().getCenterY())
                    );

                    if (distance < closestDistance) {
                        closestDistance = distance;
                        closestAnchor = anchor;
                    }
                }
            }

            double snapThreshold = 50;
            if (closestAnchor != null && closestDistance < snapThreshold) {
                vCard.setTranslateX(closestAnchor.getCenterX() - vCard.getBoundsInLocal().getCenterX());
                vCard.setTranslateY(closestAnchor.getCenterY() - vCard.getBoundsInLocal().getCenterY());
            }
        });
    }


}
