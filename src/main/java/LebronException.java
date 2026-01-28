/**
 * Represents exceptions specific to the LeBron chatbot.
 * Used to signal invalid inputs during command execution.
 */
public class LebronException extends Exception {
    /**
     * Constructs a new LebronException with a specific error message.
     *
     * @param message The detailed error message describing the invalid input.
     */
    public LebronException(String message) {
        super(message);
    }
}
