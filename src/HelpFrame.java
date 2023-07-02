/**
 * Class explains the rules to the game Wordle
 *
 * @author Tony Situ
 * @version july 7th, 2023
 */

import javax.swing.*;

public class HelpFrame extends JFrame {
    ImageIcon helpIcon;
    JLabel helpLabel;

    public HelpFrame() {
        this.setSize(800, 800);
        this.setResizable(false);
        helpIcon = new ImageIcon("Images/wordle_instructions.jpg");
        helpIcon = new ImageIcon(helpIcon.getImage().getScaledInstance(800, 770, java.awt.Image.SCALE_SMOOTH));
        helpLabel = new JLabel();
        helpLabel.setIcon(helpIcon);
        this.add(helpLabel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
