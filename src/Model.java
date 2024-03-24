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
import java.util.*;
import java.util.List;

public class Model{
    private final Collection<View> views;
    private final ArrayList<String> wordleWord;
    private final ArrayList<String> input;
    private Color[] rowColors;
    private String wordleString;
    private int rowIndex;
    private int colIndex;
    private int streak;
    private int highScore;

    /**
     * Begins the game of Wordle at its default state.
     */
    public Model() {
        wordleString = Objects.requireNonNull(getWordleWord()).toUpperCase();
        wordleWord = new ArrayList<>();
        input = new ArrayList<>();
        views = new ArrayList<>();
        rowColors = new Color[5];
        rowIndex = 0;
        colIndex = 0;
        highScore = 0;
        for (char c: wordleString.toCharArray()) {
            wordleWord.add(String.valueOf(c));
        }
        System.out.println(wordleString);
    }

    /**
     * Adds a view to update to the model
     *
     * @param view the view to add to the model
     */
    public void addView(View view) {
        views.add(view);
    }

    /**
     * Gets the current row
     *
     * @return a number that describes the row as an int
     */
    public int getRowIndex() {
        return rowIndex;
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
     * Increments the row to the next for a
     * new user input.
     */
    public void incrementRow() {
        rowIndex += 1;
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
        input.add(colIndex, userInput);
        views.forEach(view -> view.onAddCharacter(userInput, rowIndex, colIndex));
        colIndex += 1;

    }

    /**
     * Removes a single letter from right to left
     */
    public void removeLetter() {
        if (!input.isEmpty()) {
            input.remove(input.size()-1);
            views.forEach(view -> view.onRemoveCharacter(rowIndex, colIndex-1));
            if (colIndex > 0) {
                colIndex -= 1;
            }
        }
    }

    /**
     * Decides whether the letters of the word should
     * be green, yellow, or gray decided by the rules of
     * wordle. If there are 5 green letters, the user has
     * won and returns true, otherwise return false
     */
    public void isInputEqualToWord() {
        int valid = 0;

        for (int i = 0; i < 5; i++) {
            if (wordleWord.contains(input.get(i))) {
                if (wordleWord.get(i).equals(input.get(i))) {
                    rowColors[i] = Color.GREEN;
                    valid += 1;
                } else {
                    rowColors[i] = Color.YELLOW;
                }
            } else {
                rowColors[i] = Color.GRAY;
            }
        }
        input.clear();

        if (valid == 5) {
            incrementStreak();
            clearGame();
            views.forEach(View::onWin);
            return;
        }
        if (rowIndex == 5) {
            resetStreak();
            clearGame();
            views.forEach(view -> view.onLose(wordleString));
            return;
        }

        views.forEach(view -> view.onUpdateRowColor(rowColors, rowIndex));
        incrementRow();
        clearColIndex();
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
            } catch(Exception ignored){

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
            views.forEach(view -> view.onUpdateScore(highScore));
            reader.close();
        } catch (Exception ignored) {
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
            streak = Integer.parseInt(line);
            views.forEach(view -> view.onUpdateStreak(streak));
            reader.close();
        } catch (Exception ignored) {
        }
    }

    /**
     * Resets the game state for the Model.
     */
    public void clearGame() {
        rowIndex = 0;
        colIndex = 0;
        input.clear();
        rowColors = new Color[5];
        wordleString = Objects.requireNonNull(getWordleWord()).toUpperCase();
        views.forEach(View::onReset);
        wordleWord.clear();
        for (char c: wordleString.toCharArray()) {
            wordleWord.add(String.valueOf(c));
        }

        System.out.println(wordleString);
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

            views.forEach(view -> view.onUpdateStreak(streak));
        } catch(Exception ignored) {
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