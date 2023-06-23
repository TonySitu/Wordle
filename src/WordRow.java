/**
 * This class is the GUI for a single row on the
 * wordle panel that will contain the user's inputs.
 *
 * @author Tony Situ
 * @version June 23rd, 2023
 */

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WordRow extends JPanel {
    private final JLabel[] wordPanel;

    /**
     * Creates an instance of a row in wordle and set its
     * default state.
     */
    public WordRow() {
        this.setLayout(new GridLayout(1, 5));
        wordPanel = new JLabel[5];
        Border whiteBorder = BorderFactory.createLineBorder(Color.WHITE);
        for (int row = 0; row < 5; row++) {
            wordPanel[row] = new JLabel();
            wordPanel[row].setOpaque(true);
            wordPanel[row].setBorder(whiteBorder);
            wordPanel[row].setHorizontalAlignment(JLabel.CENTER);
            wordPanel[row].setBackground(Color.DARK_GRAY);
            wordPanel[row].setFont(new Font(null, Font.BOLD, 30));
            this.add(wordPanel[row]);
        }
    }

    /**
     * Sets a single letter on the row to a specific character
     * and color.
     *
     * @param col the column that is to be updated.
     * @param text the text that is set onto the panel
     * @param color the color that is set onto the panel
     */
    public void setText(int col, String text, Color color) {
        wordPanel[col].setText(text);
        wordPanel[col].setBackground(color);
        wordPanel[col].setForeground(Color.WHITE);
    }
}