/**
 * This class is what the user sees when they interact with
 * the game. The view displays the game state of the model.
 *
 * @author Tony Situ
 * @version June 23rd, 2023
 */

import javax.swing.*;
import java.awt.*;

public class SwingView extends JPanel implements View {
    private final WordRow[] wordleRows;
    private final ScorePanel scorePanel;

    /**
     * Creates the GUI for the wordle game that observes
     * the model of the game and updates accordingly.
     */
    public SwingView() {
        this.setLayout(new GridLayout(7, 1));
        scorePanel = new ScorePanel();
        scorePanel.setBackground(Color.DARK_GRAY);
        this.add(scorePanel);
        wordleRows = new WordRow[6];

        for (int row = 0; row<6; row++) {
            wordleRows[row] = new WordRow();
            this.add(wordleRows[row]);
        }
        wordleRows[0].setText(0, "_", Color.DARK_GRAY);
    }

    @Override
    public void onAddCharacter(String userInput, int rowIndex, int colIndex) {
        wordleRows[rowIndex].setText(colIndex, userInput, Color.DARK_GRAY);
        if (colIndex < 4) {
            wordleRows[rowIndex].setText(colIndex+1, "_", Color.DARK_GRAY);
        }
    }

    @Override
    public void onRemoveCharacter(int rowIndex, int colIndex) {
        wordleRows[rowIndex].setText(colIndex, "_", Color.DARK_GRAY);
        if (colIndex < 4) {
            wordleRows[rowIndex].setText(colIndex+1, "", Color.DARK_GRAY);
        }
    }

    @Override
    public void onUpdateStreak(int streak) {
        scorePanel.setStreak(streak);
    }

    @Override
    public void onUpdateScore(int highscore) {
        scorePanel.setHighScore(highscore);
    }

    @Override
    public void onUpdateRowColor(Color[] rowColors, int rowIndex) {
        for (int i = 0; i < 5; i++) {
            wordleRows[rowIndex].setColor(i, rowColors[i]);
        }
    }

    @Override
    public void onWin() {
        JOptionPane.showMessageDialog(null, "You Won!");
    }

    @Override
    public void onLose(String wordleString) {
        JOptionPane.showMessageDialog(null, "You lost. The Word was: " + wordleString);
    }

    @Override
    public void onReset() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 5; col++) {
                wordleRows[row].setText(col, "", Color.DARK_GRAY);
            }
        }
        wordleRows[0].setText(0, "_", Color.DARK_GRAY);
    }
}