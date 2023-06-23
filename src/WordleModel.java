/**
 * This class models a game of Wordle.
 *
 * @author Tony Situ
 * @version June 19th, 2023
 */

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.ArrayList;

public class WordleModel extends Observable {
    private ArrayList<String> wordleWord;
    private final ArrayList<String> input;
    private Color[] rowColors;
    private String wordleString;
    private boolean changeColor;
    private boolean clearGame;
    private int row;
    private int colIndex;
    private int streak;
    private int highScore;

    /**
     * Begins the game of Wordle at its default state.
     */
    public WordleModel() {
        wordleString = getWordleWord().toUpperCase();
        wordleWord = new ArrayList<String>();
        input = new ArrayList<String>();
        rowColors = new Color[5];
        changeColor = false;
        clearGame = false;
        row = 0;
        colIndex = 0;
        highScore = 0;
        for (char c: wordleString.toCharArray()) {
            wordleWord.add(String.valueOf(c));
        }
        System.out.println(wordleString);
        setStreak();
        setHighScore();
    }

    /**
     * Returns the user's input.
     *
     * @return the user's input as an ArrayList<String>
     */
    public ArrayList<String> getInput() {
        return input;
    }

    /**
     * Returns an array of colors that represent
     * a single row in a game of wordle.
     *
     * @return the row of colors as an array of type Color
     */
    public Color[] getRowColors() {
        return rowColors;
    }

    /**
     * getWordleString returns the randomly generated word.
     *
     * @return a String that contains the random word
     */
    public String getWordleString() {
        return wordleString;
    }

    /**
     * Returns true or false on whether the
     * letter boxes should change colors
     *
     * @return true if letter boxes should change colors,
     *         false otherwise
     */
    public boolean getChangeColor() {
        return changeColor;
    }

    /**
     * Returns true if game needs to be cleared,
     * false otherwise.
     *
     * @return true if the game should be cleared, false otherwise
     */
    public boolean getClearGame() {
        return clearGame;
    }

    /**
     * Gets the current row
     *
     * @return a number that describes the row as an int
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index i.e. the letter
     * index that the next letter should appear in.
     *
     * @return a number that describes the column as an int
     */
    public int getColIndex() {
        return colIndex;
    }

    /**
     * Returns the user's current streak.
     *
     * @return a number that describes the user's streak as
     *         an int
     */
    public int getStreak() {
        return streak;
    }

    /**
     * Returns the user's high score.
     *
     * @return a number that describes the user's high score
     *         as an int
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Increments the row to the next for a
     * new user input.
     */
    public void incrementRow() {
        row += 1;
    }

    /**
     * Resets the column index to 0.
     */
    public void clearColIndex() {
        colIndex = 0;
    }

    /**
     * Updates the row with the user's input on the wordle
     * board one letter at a time.
     *
     * @param userInput the String input to be added to the row
     */
    public void updateRowLetter(String userInput) {
        if (input.size() == 5) {
            return;
        }
        input.add(userInput);
        colIndex += 1;
        setChanged();
        notifyObservers();
    }

    /**
     * Removes a single letter from right to left
     */
    public void removeLetter() {
        if (!input.isEmpty()) {
            input.remove(input.size()-1);
            colIndex -= 1;
            setChanged();
            notifyObservers();
        }
    }

    /**
     * Decides whether the letters of the word should
     * be green, yellow, or gray decided by the rules of
     * wordle. If there are 5 green letters, the user has
     * won and returns true, otherwise return false
     *
     * @return true if user wins, false otherwise
     */
    public boolean isInputEqualToWord() {
        int valid = 0;
        changeColor = true;

        for (int c = 0; c < input.size(); c++) {
            if (wordleWord.contains(input.get(c))) {
                if (wordleWord.get(c).equals(input.get(c))) {
                    rowColors[c] = Color.GREEN;
                    valid += 1;
                } else {
                    rowColors[c] = Color.YELLOW;
                }
            } else {
                rowColors[c] = Color.GRAY;
            }
        }

        setChanged();
        notifyObservers();
        input.clear();
        changeColor = false;
        return valid == 5;
    }

    /**
     * Increments the win streak of the user by 1 and stores
     * it inside an output file. IF the current streak is
     * greater than the best streak, then update the best streak
     * and also put it into the output.txt.
     */
    public void incrementStreak() {
        streak += 1;

        try {
            if (streak > highScore) {
                highScore = streak;
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write(highScore + "\n" + streak);
            writer.close();
            setHighScore();
            } catch(Exception e){
                e.printStackTrace();
            }
    }

    /**
     * Sets the high score for this model by reading the
     * output.txt.
     */
    public void setHighScore() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
            String line = reader.readLine();
            highScore = Integer.parseInt(line);

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the streak for this model by reading the
     * output.txt.
     */
    public void setStreak() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
            String line = reader.readLine();
            line = reader.readLine();
            streak = Integer.parseInt(line);

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setChanged();
        notifyObservers();
    }

    /**
     * Resets the game state for the Model.
     */
    public void clearGame() {
        row = 0;
        colIndex = 0;
        clearGame = true;
        input.clear();
        rowColors = new Color[5];
        wordleString = getWordleWord().toUpperCase();
        wordleWord = new ArrayList<String>();
        for (char c: wordleString.toCharArray()) {
            wordleWord.add(String.valueOf(c));
        }

        System.out.println(wordleString);

        setChanged();
        notifyObservers();
        clearGame = false;
    }

    /**
     * Resets the streak of the user if user loses a
     * wordle game.
     */
    public void resetStreak() {
        streak = 0;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write(highScore + "\n" + streak);
            writer.close();
            setHighScore();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the wordle word for the user to guess by grabbing a
     * random word from Words.txt.
     *
     * @return a String that contains the wordle word
     */
    private String getWordleWord() {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("Words.txt"));
        } catch (Exception e) {
            System.out.println("No File Found");
            return null;
        }
        Random random = new Random();
        return lines.get(random.nextInt(lines.size()));
    }
}