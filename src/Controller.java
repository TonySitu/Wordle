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

public class Controller extends JFrame implements KeyListener, ActionListener {
    private final Model model;

    /**
     * Creates the GUI using the swingView class.
     *
     * @param model The model of wordle
     * @param swingView  The swingView/GUI of wordle
     */
    public Controller(Model model, SwingView swingView) {
        this.model = model;
        populateFrame(swingView);
    }

    /**
     * Creates the GUI for wordle
     *
     * @param swingView the main panel to be added to the GUI
     */
    private void populateFrame(SwingView swingView) {
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

        swingView.setVisible(true);
        this.add(swingView);
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
            model.removeLetter();
            return;
        }

        String userInput = KeyEvent.getKeyText(e.getKeyCode());
        if (userInput.length() == 1 && !notInt(userInput)) {
            model.updateRowLetter(userInput);
            return;
        }

        if (e.getKeyCode()==KeyEvent.VK_ENTER){
            if (model.getRowIndex() < 6 && model.getColIndex() == 4) {
                model.isInputEqualToWord();
            }
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
            //it'll eventually do something
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }
}