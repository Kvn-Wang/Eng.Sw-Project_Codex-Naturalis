package it.polimi.codexnaturalis.view.GUI;

import it.polimi.codexnaturalis.model.enumeration.ResourceType;
import it.polimi.codexnaturalis.model.shop.card.Card;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class GuiCard {
    private Rectangle vCard;
    private Card card;
    private int num;
    private Image cardImg;
    public Rectangle getRectangle() {
        return vCard;
    }

    public Card getCard() {
        return card;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public GuiCard(Card card, Circle[][] matrix) {
        double x=170;
        double y=100;
        System.out.println();
        //cardImg =new Image("src/main/resources/it/polimi/codexnaturalis/graphics/CODEX_cards_gold_front/"+card.getPng()+".png");

        switch (card.getCardColor()){
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

//        vCard.setStroke(null);
//        vCard.setFill(new ImagePattern(cardImg));

        vCard.setOnDragDetected(event -> {
            Dragboard db = vCard.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            if(!card.getIsBack()){
                content.putString("false"+"||"+num);
            }else{
                content.putString("true"+"||"+num);
            }
            //content.putImage(vCardimg);
            db.setContent(content);
            event.consume();
        });

        vCard.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                card.setIsBack(!card.getIsBack());
                event.consume();
            }
        });

//        GuiGame.getMap().setOnDragDropped(e -> {
//            Dragboard db = e.getDragboard();
//            boolean success = false;
//            if (db.hasString() && "rectangle".equals(db.getString())) {
//                Rectangle sourceRect = (Rectangle) e.getGestureSource();
//                Rectangle newRect = new Rectangle(sourceRect.getWidth(), sourceRect.getHeight(), sourceRect.getFill());
//                GuiGame.getMap().getChildren().add(newRect);
//                double closestDistance = Double.MAX_VALUE;
//                Circle closestAnchor = null;
//
//                for(Circle[] rows : matrix) {
//                    for (Circle anchor : rows) {
//                        double distance = Math.hypot(
//                                anchor.getCenterX() - (newRect.getTranslateX() + newRect.getBoundsInLocal().getCenterX()),
//                                anchor.getCenterY() - (newRect.getTranslateY() + newRect.getBoundsInLocal().getCenterY())
//                        );
//
//                        if (distance < closestDistance) {
//                            closestDistance = distance;
//                            closestAnchor = anchor;
//                        }
//                    }
//                }
//
//                double snapThreshold = 50;
//                if (closestAnchor != null && closestDistance < snapThreshold) {
//                    newRect.setTranslateX(closestAnchor.getCenterX() - sourceRect.getBoundsInLocal().getCenterX());
//                    newRect.setTranslateY(closestAnchor.getCenterY() - sourceRect.getBoundsInLocal().getCenterY());
//                }
//
//                success = true;
//            }
//            e.setDropCompleted(success);
//            e.consume();
//        });

        vCard.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                GuiGame.getvHand().getChildren().remove(vCard);
            }
            event.consume();
        });

//        vCard.setOnMousePressed((MouseEvent event) -> {
//            orgSceneX = event.getSceneX();
//            orgSceneY = event.getSceneY();
//            orgTranslateX = vCard.getTranslateX();
//            orgTranslateY = vCard.getTranslateY();
//        });
//
//        vCard.setOnMouseDragged((MouseEvent event) -> {
//            double offsetX = event.getSceneX() - orgSceneX;
//            double offsetY = event.getSceneY() - orgSceneY;
//            vCard.setTranslateX(orgTranslateX + offsetX);
//            vCard.setTranslateY(orgTranslateY + offsetY);
//        });

        //vCard.setOnMouseReleased((MouseEvent event) -> {
            // Snap to the nearest anchor point if close enough
//            double closestDistance = Double.MAX_VALUE;
//            Circle closestAnchor = null;
//
//            for(Circle[] rows : matrix) {
//                for (Circle anchor : rows) {
//                    double distance = Math.hypot(
//                            anchor.getCenterX() - (vCard.getTranslateX() + vCard.getBoundsInLocal().getCenterX()),
//                            anchor.getCenterY() - (vCard.getTranslateY() + vCard.getBoundsInLocal().getCenterY())
//                    );
//
//                    if (distance < closestDistance) {
//                        closestDistance = distance;
//                        closestAnchor = anchor;
//                    }
//                }
//            }
//
//            double snapThreshold = 50;
//            if (closestAnchor != null && closestDistance < snapThreshold) {
//                vCard.setTranslateX(closestAnchor.getCenterX() - vCard.getBoundsInLocal().getCenterX());
//                vCard.setTranslateY(closestAnchor.getCenterY() - vCard.getBoundsInLocal().getCenterY());
//            }
     //   });
    }


}
