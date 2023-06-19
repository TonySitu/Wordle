import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WordleController extends JFrame implements KeyListener, ActionListener {
    private final WordleModel wordleModel;

    public WordleController(WordleModel wordleModel, WordleView wordleView) {
        this.wordleModel = wordleModel;
        populateFrame(wordleView);
    }

    private void populateFrame(WordleView wordleView) {
        this.setLayout(new GridLayout(1, 1));
        this.setSize(800, 800);
        JMenuBar menuBar = new JMenuBar();
        JMenu help = new JMenu("Help");
        JMenuItem rules = new JMenuItem("Rules");
        JMenuItem controls = new JMenuItem("Controls");
        rules.addActionListener(this);
        controls.addActionListener(this);
        help.add(rules);
        help.add(controls);
        help.setForeground(Color.WHITE);
        menuBar.add(help);
        menuBar.setBackground(Color.DARK_GRAY);
        this.setJMenuBar(menuBar);

        wordleView.setVisible(true);
        this.add(wordleView);
        this.addKeyListener(this);
    }

    private boolean notInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_BACK_SPACE){
            wordleModel.removeLetter();
            return;
        }

        String userInput = KeyEvent.getKeyText(e.getKeyCode());
        if (userInput.length() == 1 && !notInt(userInput)) {
            wordleModel.updateRowLetter(userInput);
            return;
        }

        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            if (wordleModel.getInput().size() != 5) {
                return;
            }
            if (wordleModel.getRow() < 6) {
                if (wordleModel.isInputEqualToWord()) {
                    wordleModel.incrementStreak();
                    JOptionPane.showMessageDialog(null, "You Won!");
                    wordleModel.clearGame();
                    return;
                }
                if (wordleModel.getRow() == 5) {
                    JOptionPane.showMessageDialog(null, "You lost. The Word was: " + wordleModel.getWordleString());
                    wordleModel.resetStreak();
                    wordleModel.clearGame();
                    return;
                }
            }
            wordleModel.incrementRow();
            wordleModel.clearColIndex();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Rules")) {

        }
        if (action.equals("Controls")) {

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}