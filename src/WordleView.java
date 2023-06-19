import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class WordleView extends JPanel implements Observer {
    private final WordRow[] wordleRows;
    private final ScorePanel scorePanel;

    public WordleView() {
        this.setLayout(new GridLayout(7, 1));
        scorePanel = new ScorePanel();
        scorePanel.setBackground(Color.DARK_GRAY);
        this.add(scorePanel);
        wordleRows = new WordRow[6];

        for (int row = 0; row<6; row++) {
            wordleRows[row] = new WordRow();
            this.add(wordleRows[row]);
        }
    }

    @Override
    public void update(Observable obs, Object arg) {
        if (obs instanceof WordleModel wordleModel) {
            scorePanel.setStreak(wordleModel.getStreak());
            scorePanel.setHighScore(wordleModel.getHighScore());

            if (wordleModel.getClearGame()) {
                wordleModel.setClearGameFalse();
                for (int row = 0; row < 6; row++) {
                    for (int col = 0; col<wordleRows.length-1; col++) {
                        wordleRows[row].setText(col, "", Color.DARK_GRAY);
                    }
                }
                wordleRows[0].setText(0, "_", Color.DARK_GRAY);
                return;
            }

            int row = wordleModel.getRow();
            int colIndex = wordleModel.getColIndex();
            if (!wordleModel.getChangeColor()) {
                for (int col = 0; col<wordleRows.length-1; col++) {
                    wordleRows[row].setText(col, "", Color.DARK_GRAY);
                    if (col<wordleModel.getInput().size()) {
                        wordleRows[row].setText(col, wordleModel.getInput().get(col), Color.DARK_GRAY);
                    }
                }
                if (wordleModel.getColIndex()<wordleRows.length-1) {
                    wordleRows[row].setText(colIndex, "_", Color.DARK_GRAY);
                }

            } else {
                for (int col = 0; col<wordleRows.length-1; col++) {
                    wordleRows[row].setText(col, wordleModel.getInput().get(col), wordleModel.getRowColors()[col]);
                    }
                wordleModel.setChangeColorFalse();
                if (row+1 < 6) {
                    wordleRows[row+1].setText(0, "_", Color.DARK_GRAY);
                }
            }
        }
    }
}