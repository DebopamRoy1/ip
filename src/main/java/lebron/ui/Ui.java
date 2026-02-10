package lebron.ui;

/**
 * Handles all user interactions for the LeBron chatbot.
 * Responsible for reading user input and printing formatted messages.
 */
public class Ui {
    /**
     * Returns the welcome message and LeBron persona greeting.
     * @return The formatted welcome string.
     */
    public String showWelcome() {
        return "Yo yo yo, what's up it's your boy LeBron James haha.\n"
                + "Building a legacy, one task at a time. What we workin' on today GOAT?";
    }

    /**
     * Returns an error message with LeBron's signature "And One" catchphrase.
     * @param message The specific error description.
     * @return The formatted error string.
     */
    public String showError(String message) {
        return "ANDDDD ONEE: " + message;
    }

    /**
     * Returns the goodbye message when the user exits the application.
     * @return The formatted goodbye string.
     */
    public String showGoodbye() {
        return "That's all from your boy from Akron, Ohio\n"
                + "#striveforgreatness #appreciategreatness";
    }

}
