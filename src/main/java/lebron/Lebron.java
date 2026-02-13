package lebron;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lebron.parser.Parser;
import lebron.storage.Storage;
import lebron.task.Task;
import lebron.tasklist.TaskList;
import lebron.ui.Ui;

/**
 * Main class for the LeBron chatbot, a task tracking system.
 * This class coordinates the lebron.storage.Storage, lebron.ui.Ui, lebron.parser.Parser and
 * lebron.tasklist.TaskList to process user commands.
 */
public class Lebron extends Application {
    private static Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Empty constructor for JavaFX.
     */
    public Lebron() {
        this("./data/lebron.txt");
    }

    /**
     * Initialises the chatbot with the necessary components and loads data from the file.
     *
     * @param filePath The path to the file where tasks are saved.
     */
    public Lebron(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showError("Couldn't load previous legacy. Starting fresh.");
            tasks = new TaskList();
        }
    }

    /**
     * Generates a response to user input.
     * This replaces the old 'run()' loop to work with the GUI.
     *
     * @param input User input string from the GUI.
     * @return LeBron's response as a string.
     */
    public String getResponse(String input) {
        assert tasks != null : "TaskList should be initialised before getting response";
        assert ui != null : "Ui should be initialised before getting response";
        try {
            return executeCommand(input);
        } catch (LebronException e) {
            return ui.showError(e.getMessage());
        } catch (IOException e) {
            return ui.showError("ERROR: I couldn't save your legacy to the disk!");
        } catch (Exception e) {
            return ui.showError("Unexpected turnover: " + e.getMessage());
        }
    }

    /**
     * Identifies the command and delegates to specific handlers.
     */
    private String executeCommand(String input) throws LebronException, IOException {
        String trimmedInput = input.trim();
        String command = input.split(" ")[0].toLowerCase();
        switch (command) {
        case "bye":
            return ui.showGoodbye();
        case "list":
            return handleList();
        case "mark":
            return handleMark(trimmedInput);
        case "unmark":
            return handleUnmark(trimmedInput);
        case "delete":
            return handleDelete(trimmedInput);
        case "todo":
        case "deadline":
        case "event":
            return handleAddTask(trimmedInput, command);
        case "find":
            return handleFind(trimmedInput);
        case "update":
            return handleUpdate(trimmedInput);
        default:
            throw new LebronException("Hol' up... I don't know what '" + input + "' means. Check the playbook!");
        }
    }

    private String handleList() {
        if (tasks.size() == 0) {
            return "Your legacy list is empty, King!. Let's get to work!";
        }

        String listBody = IntStream.range(0, tasks.size())
                .mapToObj(i -> (i + 1) + ". " + tasks.get(i))
                .collect(Collectors.joining("\n"));

        return "Here's how your legacy list is going:\n" + listBody;
    }

    private String handleMark(String input) throws LebronException, IOException {
        int index = Parser.parseIndex(input, "mark", tasks.size());
        tasks.get(index).markAsDone();
        storage.save(tasks.getAllTasks());
        return "Nice! I've marked this as done for the king:\n  " + tasks.get(index);
    }

    private String handleUnmark(String input) throws LebronException, IOException {
        int index = Parser.parseIndex(input, "unmark", tasks.size());
        tasks.get(index).markAsUndone();
        storage.save(tasks.getAllTasks());
        return "Ok, I've marked this as not done yet. Keep grinding:\n  " + tasks.get(index);
    }

    private String handleDelete(String input) throws LebronException, IOException {
        int index = Parser.parseDeleteIndex(input, tasks.size());
        Task removed = tasks.delete(index);
        storage.save(tasks.getAllTasks());
        return "Noted. I've benched this task:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks left. #StayReady";
    }

    private String handleAddTask(String input, String type) throws LebronException, IOException {
        Task newTask;

        if (type.equals("todo")) {
            newTask = Parser.parseTodo(input);
        } else if (type.equals("deadline")) {
            newTask = Parser.parseDeadline(input);
        } else {
            newTask = Parser.parseEvent(input);
        }

        tasks.add(newTask);
        storage.save(tasks.getAllTasks());

        return getAddMessage(newTask);
    }

    private String handleFind(String input) throws LebronException {
        if (input.length() <= 5) {
            throw new LebronException("Give me a keyword to search the playbook!");
        }
        String keyword = input.substring(5).trim();
        List<Task> matches = tasks.findTasks(keyword);
        if (matches.isEmpty()) {
            return "Nothing in the legacy matches that keyword, King.";
        }
        String findResults = IntStream.range(0, matches.size())
                .mapToObj(i -> (i + 1) + ". " + matches.get(i))
                .collect(Collectors.joining("\n"));

        return "Here are the matching tasks in your legacy:\n" + findResults;
    }

    private String getAddMessage(Task task) {
        return "That's what's up king! I've added this to the legacy:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list. #StayReady";
    }

    /**
     * Handles the update command by modifying an existing task's description.
     *
     * @param input The raw user input.
     * @return A confirmation message of the update.
     * @throws LebronException If the index is invalid or description is missing.
     * @throws IOException If the update cannot be saved to disk.
     */
    private String handleUpdate(String input) throws LebronException, IOException {
        int index = Parser.parseIndex(input, "update", tasks.size());
        String newDescription = Parser.parseUpdateDescription(input);

        Task taskToUpdate = tasks.get(index);
        String oldDescription = taskToUpdate.toString();
        taskToUpdate.setDescription(newDescription);

        storage.save(tasks.getAllTasks());

        return "Playbook updated, King!\n  From: " + oldDescription + "\n  To: " + taskToUpdate;
    }

    /**
     * Returns the welcome message from the Ui class.
     */
    public String getWelcomeMessage() {
        return ui.showWelcome();
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Lebron.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setLebron(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Lebron("./data/lebron.txt").getResponse("list");
    }
}


