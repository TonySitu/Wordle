import java.awt.*;

public interface View {

    /**
     * Adds a character to the user's input word
     *
     * @param userInput the character to add to the user input
     * @param colIndex the index of the character in user's word
     * @param rowIndex index of user word's row
     */
    void onAddCharacter(String userInput, int rowIndex, int colIndex);

    /**
     * Removes a character from the user's input word
     */
    void onRemoveCharacter(int rowIndex, int colIndex);

    /**
     * Updates the user's current streak
     *
     * @param streak the new streak that the user has
     */
    void onUpdateStreak(int streak);

    /**
     * Updates the user's current highscore
     *
     * @param highscore the highscore the user has
     */
    void onUpdateScore(int highscore);

    /**
     * Updates the colors to the rows after comparing user input to
     * the word user is trying to guess
     */
    void onUpdateRowColor(Color[] rowColors, int rowIndex);

    /**
     * Show a win message and reset the game
     */
    void onWin();

    /**
     * Show a loss message and reset the game
     *
     * @param wordleString wordle word to be displayed
     */
    void onLose(String wordleString);

    /**
     * Resets the GUI after game is done
     */
    void onReset();
}
