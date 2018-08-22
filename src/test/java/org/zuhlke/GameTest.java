package org.zuhlke;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void test() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        Game aGame = new Game(new PrintStream(out), new ArrayList(), new boolean[6]);
        aGame.add("Chet");
        boolean actual = aGame.wrongAnswer();
        assertTrue(actual);

        assertThat(out.toString(), equalToIgnoringWhiteSpace("Chet was added\n" +
                "They are player number 1\n" +
                "Question was incorrectly answered\n" +
                "Chet was sent to the penalty box"));
    }


    @Test
    public void main() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        Game aGame = new Game(new PrintStream(out), new ArrayList(), new boolean[6]);

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = new Random(1);

        boolean notAWinner;
        do {

            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }


        } while (notAWinner);

        assertThat(out.toString().replaceAll("\\r", ""), equalToIgnoringWhiteSpace("Chet was added\n" +
                "They are player number 1\n" +
                "Pat was added\n" +
                "They are player number 2\n" +
                "Sue was added\n" +
                "They are player number 3\n" +
                "Chet is the current player\n" +
                "They have rolled a 1\n" +
                "Chet's new location is 1\n" +
                "The category is Science\n" +
                "Science Question 0\n" +
                "Answer was correct!!!!\n" +
                "Chet now has 1 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 3\n" +
                "Pat's new location is 3\n" +
                "The category is Rock\n" +
                "Rock Question 0\n" +
                "Answer was correct!!!!\n" +
                "Pat now has 1 Gold Coins.\n" +
                "Sue is the current player\n" +
                "They have rolled a 5\n" +
                "Sue's new location is 5\n" +
                "The category is Science\n" +
                "Science Question 1\n" +
                "Answer was correct!!!!\n" +
                "Sue now has 1 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 5\n" +
                "Chet's new location is 6\n" +
                "The category is Sports\n" +
                "Sports Question 0\n" +
                "Answer was correct!!!!\n" +
                "Chet now has 2 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 4\n" +
                "Pat's new location is 7\n" +
                "The category is Rock\n" +
                "Rock Question 1\n" +
                "Answer was correct!!!!\n" +
                "Pat now has 2 Gold Coins.\n" +
                "Sue is the current player\n" +
                "They have rolled a 5\n" +
                "Sue's new location is 10\n" +
                "The category is Sports\n" +
                "Sports Question 1\n" +
                "Question was incorrectly answered\n" +
                "Sue was sent to the penalty box\n" +
                "Chet is the current player\n" +
                "They have rolled a 3\n" +
                "Chet's new location is 9\n" +
                "The category is Science\n" +
                "Science Question 2\n" +
                "Answer was correct!!!!\n" +
                "Chet now has 3 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 3\n" +
                "Pat's new location is 10\n" +
                "The category is Sports\n" +
                "Sports Question 2\n" +
                "Question was incorrectly answered\n" +
                "Pat was sent to the penalty box\n" +
                "Sue is the current player\n" +
                "They have rolled a 3\n" +
                "Sue is getting out of the penalty box\n" +
                "Sue's new location is 1\n" +
                "The category is Science\n" +
                "Science Question 3\n" +
                "Answer was correct!!!!\n" +
                "Sue now has 2 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 2\n" +
                "Chet's new location is 11\n" +
                "The category is Rock\n" +
                "Rock Question 2\n" +
                "Answer was correct!!!!\n" +
                "Chet now has 4 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 2\n" +
                "Pat is not getting out of the penalty box\n" +
                "Sue is the current player\n" +
                "They have rolled a 1\n" +
                "Sue is getting out of the penalty box\n" +
                "Sue's new location is 2\n" +
                "The category is Sports\n" +
                "Sports Question 3\n" +
                "Answer was correct!!!!\n" +
                "Sue now has 3 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 5\n" +
                "Chet's new location is 4\n" +
                "The category is Pop\n" +
                "Pop Question 0\n" +
                "Answer was correct!!!!\n" +
                "Chet now has 5 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 4\n" +
                "Pat is not getting out of the penalty box\n" +
                "Sue is the current player\n" +
                "They have rolled a 3\n" +
                "Sue is getting out of the penalty box\n" +
                "Sue's new location is 5\n" +
                "The category is Science\n" +
                "Science Question 4\n" +
                "Answer was correct!!!!\n" +
                "Sue now has 4 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 1\n" +
                "Chet's new location is 5\n" +
                "The category is Science\n" +
                "Science Question 5\n" +
                "Answer was correct!!!!\n" +
                "Chet now has 6 Gold Coins.\n"));
    }

    @Test
    public void main2() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        Game aGame = new Game(new PrintStream(out), new ArrayList(), new boolean[6]);

        aGame.add("Chet");
        aGame.add("Pat");

        Random rand = new Random(25);

        boolean notAWinner;
        do {

            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }


        } while (notAWinner);

        assertThat(out.toString().replaceAll("\\r", ""), equalToIgnoringWhiteSpace("Chet was added\n" +
                "They are player number 1\n" +
                "Pat was added\n" +
                "They are player number 2\n" +
                "Chet is the current player\n" +
                "They have rolled a 2\n" +
                "Chet's new location is 2\n" +
                "The category is Sports\n" +
                "Sports Question 0\n" +
                "Answer was correct!!!!\n" +
                "Chet now has 1 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 3\n" +
                "Pat's new location is 3\n" +
                "The category is Rock\n" +
                "Rock Question 0\n" +
                "Answer was correct!!!!\n" +
                "Pat now has 1 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 3\n" +
                "Chet's new location is 5\n" +
                "The category is Science\n" +
                "Science Question 0\n" +
                "Answer was correct!!!!\n" +
                "Chet now has 2 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 1\n" +
                "Pat's new location is 4\n" +
                "The category is Pop\n" +
                "Pop Question 0\n" +
                "Answer was correct!!!!\n" +
                "Pat now has 2 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 2\n" +
                "Chet's new location is 7\n" +
                "The category is Rock\n" +
                "Rock Question 1\n" +
                "Answer was correct!!!!\n" +
                "Chet now has 3 Gold Coins.\n" +
                "Pat is the current player\n" +
                "They have rolled a 1\n" +
                "Pat's new location is 5\n" +
                "The category is Science\n" +
                "Science Question 1\n" +
                "Answer was correct!!!!\n" +
                "Pat now has 3 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 5\n" +
                "Chet's new location is 0\n" +
                "The category is Pop\n" +
                "Pop Question 1\n" +
                "Question was incorrectly answered\n" +
                "Chet was sent to the penalty box\n" +
                "Pat is the current player\n" +
                "They have rolled a 2\n" +
                "Pat's new location is 7\n" +
                "The category is Rock\n" +
                "Rock Question 2\n" +
                "Answer was correct!!!!\n" +
                "Pat now has 4 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 2\n" +
                "Chet is not getting out of the penalty box\n" +
                "Pat is the current player\n" +
                "They have rolled a 5\n" +
                "Pat's new location is 0\n" +
                "The category is Pop\n" +
                "Pop Question 2\n" +
                "Answer was correct!!!!\n" +
                "Pat now has 5 Gold Coins.\n" +
                "Chet is the current player\n" +
                "They have rolled a 2\n" +
                "Chet is not getting out of the penalty box\n" +
                "Pat is the current player\n" +
                "They have rolled a 5\n" +
                "Pat's new location is 5\n" +
                "The category is Science\n" +
                "Science Question 2\n" +
                "Answer was correct!!!!\n" +
                "Pat now has 6 Gold Coins."));
    }

    @Test
    public void roll3_bradOnPlace0_bradMovedAndQuestionAsked() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        Game aGame = new Game(new PrintStream(out), Arrays.asList("Brad", "Pit"), new boolean[6]);

        Question forBrad = aGame.roll(3);

        // Not in penalty box: Question was asked
        assertEquals("Rock Question 0", forBrad.toString());
        // Brad has moved
        assertEquals(3, aGame.places[0]);
    }

    @Test
    public void roll2_bradInPenaltyBoxEvenRoll_bradNotMovedAndNoQuestionAsked() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        Game aGame = new Game(new PrintStream(out), Arrays.asList("Brad", "Pit"), new boolean[]{true, false});

        Question forBrad = aGame.roll(2);

        // In penalty box and even roll: Question was not asked
        assertNull(forBrad);
        // Brad has not moved
        assertEquals(0, aGame.places[0]);
    }


    @Test
    public void roll3_bradInPenaltyBoxUnevenRoll_bradMovedAndQuestionAsked() {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        Game aGame = new Game(new PrintStream(out), Arrays.asList("Brad", "Pit"), new boolean[]{true, false});

        Question forBrad = aGame.roll(3);

        // In penalty box but uneven roll: Question was asked
        assertEquals("Rock Question 0", forBrad.toString());
        // Brad has moved
        assertEquals(3, aGame.places[0]);
    }

}