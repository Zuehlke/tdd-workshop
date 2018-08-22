package org.zuhlke;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    List<Player> players;

    List<Question> popQuestions = new LinkedList<>();
    List<Question> scienceQuestions = new LinkedList<>();
    List<Question> sportsQuestions = new LinkedList<>();
    List<Question> rockQuestions = new LinkedList<>();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    private PrintStream out;

    public Game() {
        this(System.out, new ArrayList<>());
    }

    Game(PrintStream printStream, List<Player> players) {
        this.out = printStream;
        this.players = players;
        for (int i = 0; i < 50; i++) {
            popQuestions.add(new Question("Pop Question " + i));
            scienceQuestions.add(new Question("Science Question " + i));
            sportsQuestions.add(new Question("Sports Question " + i));
            rockQuestions.add(new Question("Rock Question " + i));
        }
    }

    public boolean add(String playerName) {
        players.add(new Player(playerName, 0, false, 0));

        out.println(playerName + " was added");
        out.println("They are player number " + players.size());
        return true;
    }

    public Question roll(int roll) {
        out.println(players.get(currentPlayer) + " is the current player");
        out.println("They have rolled a " + roll);

        if (players.get(currentPlayer).isInPenaltyBox()) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                return advancePlayerAndAskQuestion(roll);
            } else {
                out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
                // You don't get a question, return null
                return null;
            }
        } else {
            return advancePlayerAndAskQuestion(roll);
        }
    }

    private Question advancePlayerAndAskQuestion(int roll) {
        players.get(currentPlayer).advance(roll);

        out.println(players.get(currentPlayer)
                + "'s new location is "
                + players.get(currentPlayer).getPlace());
        out.println("The category is " + currentCategory());
        return askQuestion();
    }

    private Question askQuestion() {
        Question question = null;
        if (currentCategory() == "Pop") {
            question = popQuestions.remove(0);
        }
        if (currentCategory() == "Science") {
            question = scienceQuestions.remove(0);
        }
        if (currentCategory() == "Sports") {
            question = sportsQuestions.remove(0);
        }
        if (currentCategory() == "Rock") {
            question = rockQuestions.remove(0);
        }
        out.println(question);
        return question;
    }


    private String currentCategory() {
        int place = players.get(currentPlayer).getPlace();
        if (place == 0) return "Pop";
        if (place == 4) return "Pop";
        if (place == 8) return "Pop";
        if (place == 1) return "Science";
        if (place == 5) return "Science";
        if (place == 9) return "Science";
        if (place == 2) return "Sports";
        if (place == 6) return "Sports";
        if (place == 10) return "Sports";
        return "Rock";
    }

    public boolean wasCorrectlyAnswered() {
        if (players.get(currentPlayer).isInPenaltyBox() && !isGettingOutOfPenaltyBox) {
            nextPlayer();
            return true;
        } else {
            return addPursesAndDidPlayerWin();
        }
    }

    private boolean addPursesAndDidPlayerWin() {
        out.println("Answer was correct!!!!");
        players.get(currentPlayer).increasePurse();
        out.println(players.get(currentPlayer)
                + " now has "
                + players.get(currentPlayer).getPurse()
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
        players.get(currentPlayer).setInPenaltyBox(true);

        nextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        return !(players.get(currentPlayer).getPurse() == 6);
    }
}
