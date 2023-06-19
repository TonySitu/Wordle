import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class WordRow extends JPanel {
    private final JLabel[] wordPanel;
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

    public void setText(int col, String text, Color color) {
        wordPanel[col].setText(text);
        wordPanel[col].setBackground(color);
        wordPanel[col].setForeground(Color.WHITE);
    }
}