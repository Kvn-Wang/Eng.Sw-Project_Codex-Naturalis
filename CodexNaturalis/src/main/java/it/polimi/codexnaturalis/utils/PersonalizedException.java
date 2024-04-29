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

    public static class InvalidAddCardException extends Exception{
        public InvalidAddCardException() {super("The hand is Already full");}
    }

    public static class InvalidNumPopCardException extends Exception{
        public InvalidNumPopCardException() {super("this position doesn't exist in the hand");}
    }

    public static class InvalidPopCardException extends Exception{
        public InvalidPopCardException() {super("this position in the hand is empty");}
    }

    public static class InvalidRequestTypeOfNetworkMessage extends Exception{
        public InvalidRequestTypeOfNetworkMessage(String networkMessageType) {super("Error: requested an unknown type of message: "
                + networkMessageType + ", check LobbyThread if it's present in the switch case construct");}
    }
}
