package it.polimi.codexnaturalis.game;

import it.polimi.codexnaturalis.model.player.Player;
import it.polimi.codexnaturalis.model.scoreboard.Pawn;
import it.polimi.codexnaturalis.model.scoreboard.ScoreBoard;

import java.util.Scanner;

public class TestScoreCard {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String inputString;
        Player[] players = new Player[4];
        for(int i=0; i<4; i++){
            Player newp = new Player();
            System.out.print("Enter nickname: ");
            inputString = scanner.nextLine();
            newp.setNickname(inputString);
            System.out.print("Enter color: ");
            inputString = scanner.nextLine();
            newp.setPawnColor(inputString);
            players[i] = newp;
        }
        ScoreBoard board = new ScoreBoard(players);
        for(Pawn p: board.getpawns()) {
            System.out.printf("%n%s", p.getPlayer().getNickname());
            System.out.printf("%n%s", p.getImageFile());
        }
    }
}
