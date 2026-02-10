package lebron;

import java.io.IOException;
import java.util.ArrayList;

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
        try {
            if (input.equalsIgnoreCase("bye")) {
                return ui.showGoodbye();
            } else if (input.equalsIgnoreCase("list")) {
                return handleList();
            } else if (input.startsWith("mark")) {
                return handleMark(input);
            } else if (input.startsWith("unmark")) {
                return handleUnmark(input);
            } else if (input.startsWith("delete")) {
                return handleDelete(input);
            } else if (input.startsWith("todo")) {
                return handleTodo(input);
            } else if (input.startsWith("deadline")) {
                return handleDeadline(input);
            } else if (input.startsWith("event")) {
                return handleEvent(input);
            } else if (input.startsWith("find")) {
                return handleFind(input);
            } else {
                throw new LebronException("Hol' up... I don't know what '"
                        + input + "' means. Check the playbook!");
            }
        } catch (LebronException e) {
            return ui.showError(e.getMessage());
        } catch (IOException e) {
            return "ERROR: I couldn't save your legacy to the disk!";
        } catch (Exception e) {
            return "Unexpected turnover: " + e.getMessage();
        }
    }

    private String handleList() {
        StringBuilder sb = new StringBuilder("Here's how your legacy list is going:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1)).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString();
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

    private String handleTodo(String input) throws LebronException, IOException {
        Task t = Parser.parseTodo(input);
        tasks.add(t);
        storage.save(tasks.getAllTasks());
        return getAddMessage(t);
    }

    private String handleEvent(String input) throws LebronException, IOException {
        Task t = Parser.parseEvent(input);
        tasks.add(t);
        storage.save(tasks.getAllTasks());
        return getAddMessage(t);
    }

    private String handleDeadline(String input) throws LebronException, IOException {
        Task t = Parser.parseDeadline(input);
        tasks.add(t);
        storage.save(tasks.getAllTasks());
        return getAddMessage(t);
    }

    private String handleFind(String input) {
        String keyword = input.substring(4).trim();
        ArrayList<Task> matches = tasks.findTasks(keyword);
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your legacy:\n");
        for (int i = 0; i < matches.size(); i++) {
            sb.append((i + 1)).append(".").append(matches.get(i)).append("\n");
        }
        return sb.toString();
    }

    private String getAddMessage(Task task) {
        return "That's what's up king! I've added this to the legacy:\n  " + task
                + "\nNow you have " + tasks.size() + " tasks in the list. #StayReady";
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


