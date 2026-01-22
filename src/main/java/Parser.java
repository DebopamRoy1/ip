import java.text.NumberFormat;

public class Parser {
    public static Todo parseTodo(String input) throws LebronException {
        String description = input.substring(4).trim(); // Remove "todo"
        if (description.isEmpty()) {
            throw new LebronException("The description of a todo can't be empty king! Can't build on nothing!");
        }
        return new Todo(description);
    }

    public static Deadline parseDeadline(String input) throws LebronException {
        if (!input.contains(" /by ")) {
            throw new LebronException(" A deadline needs a time king! Use: deadline [desc] /by [time]");
        }
        String content = input.substring(8).trim(); // Remove "deadline"
        String[] parts = content.split(" /by ", 2);
        if (parts[0].trim().isEmpty()) {
            throw new LebronException("The description of a deadline can't be empty!");
        }
        return new Deadline(parts[0].trim(), parts[1].trim());
    }

    public static Event parseEvent(String input) throws LebronException {
        if (!input.contains(" /from ") || !input.contains(" /to ")) {
            throw new LebronException("Events need a start and a end time king! Use: event [desc] /from [start] /to [end]");
        }
        String content = input.substring(5).trim(); // Remove "event"
        String[] parts = content.split(" /from ", 2);
        String[] timeParts = parts[1].split(" /to ", 2);

        if (parts[0].trim().isEmpty()) {
            throw new LebronException("The description of an event can't be empty!");
        }
        return new Event(parts[0].trim(), timeParts[0].trim(), timeParts[1].trim());
    }

    public static int parseIndex(String input, String command, int currentCount) throws LebronException {
        try {
            int index = Integer.parseInt(input.substring(command.length()).trim()) - 1;
            if (index < 0 || index >= currentCount) {
                throw new LebronException("That task isn't in the rotation (invalid number)!");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new LebronException("You gotta give me a valid number for that " + command + " command!");
        }
    }

    public static int parseDeleteIndex(String input, int currentCount) throws LebronException {
        try {
            int index = Integer.parseInt(input.substring(7).trim()) - 1;
            if (index < 0 || index >= currentCount) {
                throw new LebronException("That task isn't in the rotation! You only have " + currentCount + " tasks.");
            }
            return index;
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            throw new LebronException("You gotta tell me the specific number to bench!");
        }
    }
}
