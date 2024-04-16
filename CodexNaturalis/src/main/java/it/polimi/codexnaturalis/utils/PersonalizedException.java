package it.polimi.codexnaturalis.utils;

public class PersonalizedException {
    public static class InvalidPlacementException extends Exception {
        public InvalidPlacementException() {
            super("You can't place a card in this position");
        }
    }

    public static class InvalidPlaceCardRequirementException extends Exception {
        public InvalidPlaceCardRequirementException() {
            super("You do not meet the requirement to place this card");
        }
    }
}
