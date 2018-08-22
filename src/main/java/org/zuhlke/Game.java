package org.zuhlke;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    List players;
    int[] places = new int[6];
    int[] purses = new int[6];
    boolean[] inPenaltyBox;

    List<Question> popQuestions = new LinkedList<>();
    List<Question> scienceQuestions = new LinkedList<>();
    List<Question> sportsQuestions = new LinkedList<>();
    List<Question> rockQuestions = new LinkedList<>();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    private PrintStream out;

    public Game() {
        this(System.out, new ArrayList(), new boolean[6]);
    }

    Game(PrintStream printStream, List players, boolean[] inPenaltyBox) {
        this.out = printStream;
        this.players = players;
        this.inPenaltyBox = inPenaltyBox;
        for (int i = 0; i < 50; i++) {
            popQuestions.add(new Question("Pop Question " + i));
            scienceQuestions.add(new Question("Science Question " + i));
            sportsQuestions.add(new Question("Sports Question " + i));
            rockQuestions.add(new Question("Rock Question " + i));
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

    public Question roll(int roll) {
        out.println(players.get(currentPlayer) + " is the current player");
        out.println("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
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
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

        out.println(players.get(currentPlayer)
                + "'s new location is "
                + places[currentPlayer]);
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
