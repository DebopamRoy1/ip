package lebron.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import lebron.LebronException;
import lebron.task.Deadline;
import lebron.task.Event;
import lebron.task.Todo;

/**
 * Decodes user input strings into meaningful commands and task objects.
 * Validates the format of commands and throws exceptions for illegal inputs.
 */
public class Parser {
    // Formatter to handle "yyyy-MM-dd HHmm"
    private static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final int TODO_PREFIX_LENGTH = 5;
    private static final int DEADLINE_PREFIX_LENGTH = 9;
    private static final int EVENT_PREFIX_LENGTH = 6;

    /**
     * Parses a date-time string. If no time is provided, defaults to midnight (0000).
     *
     * @param dateTimeStr The string from the user input.
     * @return A LocalDateTime object.
     * @throws LebronException If the format is invalid.
     */
    private static LocalDateTime parseDateTime(String dateTimeStr) throws LebronException {
        try {
            String trimmed = dateTimeStr.trim();
            if (!trimmed.contains(" ")) {
                trimmed += " 0000";
            }
            return LocalDateTime.parse(trimmed, INPUT_FORMAT);
        } catch (DateTimeParseException e) {
            throw new LebronException("Invalid date format! Use yyyy-mm-dd HHmm (e.g., 2026-12-25 1800)");
        }
    }

    /**
     * Parses a todo command string
     *
     * @param input Raw user input.
     * @return New lebron.task.Todo object.
     * @throws LebronException If the description is empty.
     */
    public static Todo parseTodo(String input) throws LebronException {
        assert input != null : "Input cannot be null";
        String description = input.substring(TODO_PREFIX_LENGTH).trim();
        if (description.isEmpty()) {
            throw new LebronException("The description of a todo can't be empty king! Can't build on nothing!");
        }
        return new Todo(description);
    }

    /**
     * Parses a deadline command string
     *
     * @param input Raw user input.
     * @return New lebron.task.Deadline object.
     * @throws LebronException If description is empty or no deadline is input.
     */
    public static Deadline parseDeadline(String input) throws LebronException {
        if (!input.contains(" /by ")) {
            throw new LebronException(" A deadline needs a time king! Use: deadline [desc] /by [time]");
        }
        String content = input.substring(DEADLINE_PREFIX_LENGTH).trim(); // Remove "deadline"
        String[] parts = content.split(" /by ", 2);

        LocalDateTime date = parseDateTime(parts[1].trim());
        return new Deadline(parts[0].trim(), date);
    }

    /**
     * Parses an event command string
     *
     * @param input Raw user input.
     * @return New lebron.task.Event object.
     * @throws LebronException If description is empty or format is incorrect.
     */
    public static Event parseEvent(String input) throws LebronException {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new LebronException("Events need a start and a end time king! "
                    + "Use: event [desc] /from [start] /to [end]");
        }
        String content = input.substring(EVENT_PREFIX_LENGTH).trim(); // Remove "event"
        String[] parts = content.split(" /from ", 2);
        String[] timeParts = parts[1].split(" /to ", 2);

        if (parts[0].trim().isEmpty()) {
            throw new LebronException("The description of an event can't be empty!");
        }

        LocalDateTime fromDate = parseDateTime(timeParts[0].trim());
        LocalDateTime toDate = parseDateTime(timeParts[1].trim());

        return new Event(parts[0].trim(), fromDate, toDate);
    }

    /**
     * Parses a task index from a command string.
     *
     * @param input Raw user input.
     * @param command The command name being executed.
     * @param currentCount Total tasks currently in the list.
     * @return The zero-based index of the task.
     * @throws LebronException If the index is invalid or missing.
     */
    public static int parseIndex(String input, String command, int currentCount) throws LebronException {
        try {
            String indexString = input.substring(command.length()).trim();
            int index = Integer.parseInt(indexString) - 1;

            validateIndex(index, currentCount);
            return index;
        } catch (NumberFormatException e) {
            throw new LebronException("You gotta give me a valid number for the " + command + " command!");
        }
    }

    /**
     * Parses task index to delete from input string.
     *
     * @param input Raw user input.
     * @param currentCount Total tasks currently in the list.
     * @return Zero-based index of the task.
     * @throws LebronException If index is invalid or missing.
     */
    public static int parseDeleteIndex(String input, int currentCount) throws LebronException {
        return parseIndex(input, "delete", currentCount);
    }

    private static void validateIndex(int index, int totalCount) throws LebronException {
        if (index < 0 || index >= totalCount) {
            throw new LebronException("That task isn't in the rotation! You have " + totalCount + " tasks.");
        }
    }
}
