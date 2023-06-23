/**
 * This class is a JPanel that displays the current and best
 * streak the user has achieved.
 *
 * @author Tony Situ
 * @version June 19th, 2023
 */

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private final JLabel streakLabel;
    private final JLabel highScoreLabel;

    /**
     * Create a panel that contains the user's current streak and
     * best streak. The current and best streak is automatically
     * saved and is updated to the score panel when required.
     */
    public ScorePanel() {
        this.setLayout(new GridLayout(1, 2));
        streakLabel = new JLabel();
        streakLabel.setVisible(true);
        streakLabel.setHorizontalAlignment(JLabel.CENTER);
        streakLabel.setForeground(Color.WHITE);
        streakLabel.setFont(new Font(null, Font.BOLD, 30));

        highScoreLabel = new JLabel();
        highScoreLabel.setVisible(true);
        highScoreLabel.setHorizontalAlignment(JLabel.CENTER);
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setFont(new Font(null, Font.BOLD, 30));

        this.add(streakLabel);
        this.add(highScoreLabel);
    }

    /**
     * Sets the label text to display the current streak.
     *
     * @param currentStreak is the streak to be displayed
     */
    public void setStreak(int currentStreak) {
        streakLabel.setText("Streak: " + currentStreak);
    }

    /**
     * Sets the label text to display the high score.
     *
     * @param currentStreak is the high score to be displayed
     */
    public void setHighScore(int currentStreak) {
        highScoreLabel.setText("Best Streak: " + currentStreak);
    }
}