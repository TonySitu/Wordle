import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {
    private final JLabel streakLabel;
    private final JLabel highScoreLabel;

    public ScorePanel() {
        this.setLayout(new GridLayout(1, 2));
        streakLabel = new JLabel();
        streakLabel.setVisible(true);
        streakLabel.setHorizontalAlignment(JLabel.CENTER);
        streakLabel.setText("Streak: " + 0);
        streakLabel.setForeground(Color.WHITE);
        streakLabel.setFont(new Font(null, Font.BOLD, 30));

        highScoreLabel = new JLabel();
        highScoreLabel.setVisible(true);
        highScoreLabel.setHorizontalAlignment(JLabel.CENTER);
        highScoreLabel.setText("HighScore: " + 0);
        highScoreLabel.setForeground(Color.WHITE);
        highScoreLabel.setFont(new Font(null, Font.BOLD, 30));

        this.add(streakLabel);
        this.add(highScoreLabel);
    }

    public void setStreak(int currentStreak) {
        streakLabel.setText("Streak: " + currentStreak);
    }

    public void setHighScore(int currentStreak) {
        highScoreLabel.setText("Best Streak: " + currentStreak);
    }
}