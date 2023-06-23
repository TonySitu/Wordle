/**
 * This class implements the Mode View Controller pattern to model the game
 * Wordle.
 *
 * To play the game, the user must input keystrokes in order to guess a
 * randomly generated 5-letter word. The letter will highlight green if the
 * letter is in the word and also in the correct position, yellow if the
 * letter is in the correct position, and gray if the letter is not in the
 * generated word. To submit a word, the user must press the enter key.
 *
 * @author Tony Situ
 * @version June 19th, 2023
 */

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        WordleView view = new WordleView();
        WordleModel model = new WordleModel();
        model.addObserver(view);

        WordleController controller = new WordleController(model, view);
        controller.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        controller.setResizable(true);
        controller.setLocationRelativeTo(null);
        controller.setVisible(true);

        view.update(model, null);
    }
}