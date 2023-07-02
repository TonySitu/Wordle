/**
 * Allows the user to interact with the GUI of the game
 * using keystrokes.
 *
 * @author Tony Situ
 * @version June 23rd, 2023
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WordleController extends JFrame implements KeyListener, ActionListener {
    private final WordleModel wordleModel;

    /**
     * Creates the GUI using the view class.
     *
     * @param wordleModel The model of wordle
     * @param wordleView  The view/GUI of wordle
     */
    public WordleController(WordleModel wordleModel, WordleView wordleView) {
        this.wordleModel = wordleModel;
        populateFrame(wordleView);
    }

    /**
     * Creates the GUI for wordle
     *
     * @param wordleView the main panel to be added to the GUI
     */
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

    /**
     * Checks if string is not an integer.
     *
     * @param s the String to be assessed
     * @return true if String is not an int, false otherwise
     */
    private boolean notInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks whether enter, backspace or a latter is pressed.
     * Depending on these inputs will send different instructions
     * to the model.
     *
     * @param e the event to be processed
     */
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

    /**
     * Checks if the menu buttons are pressed and
     * performs a specific task depending on the button.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Rules")) {
            new HelpFrame();
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