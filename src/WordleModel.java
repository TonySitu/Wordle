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

    public ArrayList<String> getInput() {
        return input;
    }

    public Color[] getRowColors() {
        return rowColors;
    }

    public String getWordleString() {
        return wordleString;
    }

    public boolean getChangeColor() {
        return changeColor;
    }

    public boolean getClearGame() {
        return clearGame;
    }

    public int getRow() {
        return row;
    }

    public int getColIndex() {
        return colIndex;
    }

    public int getStreak() {
        return streak;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setChangeColorFalse() {
        changeColor = false;
    }

    public void setClearGameFalse() {
        clearGame = false;
    }

    public void incrementRow() {
        row += 1;
    }

    public void clearColIndex() {
        colIndex = 0;
    }

    public void updateRowLetter(String userInput) {
        if (input.size() == 5) {
            return;
        }
        input.add(userInput);
        colIndex += 1;
        setChanged();
        notifyObservers();
    }

    public void removeLetter() {
        if (!input.isEmpty()) {
            input.remove(input.size()-1);
            colIndex -= 1;
            setChanged();
            notifyObservers();
        }
    }

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
        return valid == 5;
    }

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

    public void clearGame() {
        row = 0;
        colIndex = 0;
        clearGame = true;
        input.clear();
        rowColors = new Color[5];
        wordleString = getWordleWord().toUpperCase();
        wordleWord = new ArrayList<String>();

        System.out.println(wordleString);

        setChanged();
        notifyObservers();
    }

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