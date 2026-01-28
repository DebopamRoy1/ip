import java.util.Scanner;

/**
 * Handles all user interactions for the LeBron chatbot.
 * Responsible for reading user input and printing formatted messages.
 */
public class Ui {
    private final String LINE = "_______________________________________________________________________________";
    private final Scanner scanner;

    /**
     * Initialises the Ui component and its input scanner.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prints the welcome message and LeBron persona greeting.
     */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println("Yo yo yo, what's up it's your boy LeBron James haha.");
        System.out.println("Building a legacy, one task at a time. What we workin' on today GOAT?");
        System.out.println(LINE);
    }

    /**
     * Reads the next line of input from the user.
     *
     * @return The full string command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the divider line to separate different command outputs.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Prints an error message.
     *
     * @param message The specific error description.
     */
    public void showError(String message) {
        System.out.println("ANDDDD ONEE: " + message);
    }

    /**
     * Prints a standard message to the console.
     *
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints the goodbye message when the user exits the application.
     */
    public void showGoodbye() {
        System.out.println(LINE);
        System.out.println("That's all from your boy from Akron, Ohio");
        System.out.println("#striveforgreatness #appreciategreatness");
        System.out.println(LINE);
    }
}
