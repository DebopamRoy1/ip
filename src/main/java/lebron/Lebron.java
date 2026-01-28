package lebron;

import lebron.parser.Parser;
import lebron.storage.Storage;
import lebron.task.Task;
import lebron.tasklist.TaskList;
import lebron.ui.Ui;
import java.io.IOException;

/**
 * Main class for the LeBron chatbot, a task tracking system.
 * This class coordinates the lebron.storage.Storage, lebron.ui.Ui, lebron.parser.Parser and lebron.tasklist.TaskList to process user commands.
 */
public class Lebron {
    private final Storage storage;
    private final Ui ui;
    private TaskList tasks;

    /**
     * Intialises the chatbot with the necessary components and loads data from the file.
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
     * Starts the main execution loop of the chatbot.
     * Continuously reads user input and executes the corresponding commands until exit.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            try {
                if (input.equalsIgnoreCase("bye")) {
                    ui.showGoodbye();
                    isExit = true;
                } else if (input.equalsIgnoreCase("list")) {
                    ui.showLine();
                    ui.showMessage("Here's how your legacy list is going:");
                    for (int i = 0; i < tasks.size(); i++) {
                        ui.showMessage((i + 1) + ". " + tasks.get(i));
                    }
                    ui.showLine();
                } else if (input.startsWith("mark")) {
                    int index = Parser.parseIndex(input, "mark", tasks.size());
                    tasks.get(index).markAsDone();
                    ui.showLine();
                    ui.showMessage("Nice! I've marked this as done for the king:");
                    ui.showMessage("  " + tasks.get(index));
                    ui.showLine();
                    storage.save(tasks.getAllTasks());
                } else if (input.startsWith("unmark")) {
                    int index = Parser.parseIndex(input, "unmark", tasks.size());
                    tasks.get(index).markAsUndone();
                    ui.showLine();
                    ui.showMessage("Ok, I've marked this as not done yet. Keep grinding:");
                    ui.showMessage("  " + tasks.get(index));
                    ui.showLine();
                    storage.save(tasks.getAllTasks());
                } else if (input.startsWith("delete")) {
                    int index = Parser.parseDeleteIndex(input, tasks.size());
                    Task removed = tasks.delete(index);
                    ui.showLine();
                    ui.showMessage("Noted. I've benched this task and removed it from the rotation:");
                    ui.showMessage("  " + removed);
                    ui.showMessage("Now you have " + tasks.size() + " tasks in the list. #StayReady");
                    ui.showLine();
                    storage.save(tasks.getAllTasks());
                } else if (input.startsWith("todo")) {
                    Task t = Parser.parseTodo(input);
                    tasks.add(t);
                    printAddedMessage(t);
                    storage.save(tasks.getAllTasks());
                } else if (input.startsWith("deadline")) {
                    Task t = Parser.parseDeadline(input);
                    tasks.add(t);
                    printAddedMessage(t);
                    storage.save(tasks.getAllTasks());
                } else if (input.startsWith("event")) {
                    Task t = Parser.parseEvent(input);
                    tasks.add(t);
                    printAddedMessage(t);
                    storage.save(tasks.getAllTasks());
                } else {
                    throw new LebronException("Hol' up... I don't know what '" + input + "' means. Check the playbook!");
                }
            } catch (LebronException e) {
                ui.showLine();
                ui.showError(e.getMessage());
                ui.showLine();
            } catch (IOException e) {
                ui.showLine();
                ui.showError("I couldn't save your legacy to the disk!");
                ui.showLine();
            }
        }
    }

    /**
     * Displays a confirmation that a task has been successfully added.
     *
     * @param task The task that was added.
     */
    private void printAddedMessage(Task task) {
        ui.showLine();
        ui.showMessage("That's what's up king! I've added this to the legacy:");
        ui.showMessage("  " + task);
        ui.showMessage("Now you have " + tasks.size() + " tasks in the list. #StayReady");
        ui.showLine();
    }

    public static void main(String[] args) {
        new Lebron("./data/lebron.txt").run();
    }
}
