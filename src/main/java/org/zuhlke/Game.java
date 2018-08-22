package org.zuhlke;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class Game {
    ArrayList players = new ArrayList();
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox = new boolean[6];

    LinkedList popQuestions = new LinkedList();
    LinkedList scienceQuestions = new LinkedList();
    LinkedList sportsQuestions = new LinkedList();
    LinkedList rockQuestions = new LinkedList();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    private PrintStream out;

    public Game() {
        this(System.out);
    }

    Game(PrintStream printStream) {
        out = printStream;
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast("Science Question " + i);
            sportsQuestions.addLast("Sports Question " + i);
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public boolean add(String playerName) {
        players.add(playerName);
        places[howManyPlayers()] = 0;
        purses[howManyPlayers()] = 0;
        inPenaltyBox[howManyPlayers()] = false;

        out.println(playerName + " was added");
        out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int roll) {
        out.println(players.get(currentPlayer) + " is the current player");
        out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                advancePlayerAndAskQuestion(roll);
            } else {
                out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }
        } else {
            advancePlayerAndAskQuestion(roll);
        }
    }

    private void advancePlayerAndAskQuestion(int roll) {
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

        out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
        out.println("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        if (currentCategory() == "Pop")
            out.println(popQuestions.removeFirst());
        if (currentCategory() == "Science")
            out.println(scienceQuestions.removeFirst());
        if (currentCategory() == "Sports")
            out.println(sportsQuestions.removeFirst());
        if (currentCategory() == "Rock")
            out.println(rockQuestions.removeFirst());
    }


    private String currentCategory() {
        if (places[currentPlayer] == 0) return "Pop";
        if (places[currentPlayer] == 4) return "Pop";
        if (places[currentPlayer] == 8) return "Pop";
        if (places[currentPlayer] == 1) return "Science";
        if (places[currentPlayer] == 5) return "Science";
        if (places[currentPlayer] == 9) return "Science";
        if (places[currentPlayer] == 2) return "Sports";
        if (places[currentPlayer] == 6) return "Sports";
        if (places[currentPlayer] == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer] && !isGettingOutOfPenaltyBox) {
            nextPlayer();
            return true;
        } else {
            return addPursesAndDidPlayerWin();
        }
    }

    private boolean addPursesAndDidPlayerWin() {
        out.println("Answer was correct!!!!");
        purses[currentPlayer]++;
        out.println(players.get(currentPlayer)
                + " now has "
                + purses[currentPlayer]
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        nextPlayer();

        return winner;
    }

    private void nextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    public boolean wrongAnswer() {
        out.println("Question was incorrectly answered");
        out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        nextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
