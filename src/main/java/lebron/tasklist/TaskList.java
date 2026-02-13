package lebron.tasklist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lebron.task.Task;

/**
 * Manages the list of tasks and provides operations to modify it.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Initializes an empty lebron.tasklist.TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Initialises a lebron.tasklist.TaskList with an existing set of tasks.
     *
     * @param tasks The initial ArrayList of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to be added.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes a task from the list at the specified index.
     *
     * @param index The zero-based index of the task to be removed.
     * @return The task that was removed.
     */
    public Task delete(int index) {
        return tasks.remove(index);
    }

    /**
     * Retrieves a task from the list at the specified index.
     *
     * @param index The zero-based index of the task.
     * @return The task at the specified index.
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Index out of bounds";
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return The size of the task list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns all tasks stored in the list.
     *
     * @return The internal ArrayList of tasks.
     */
    public ArrayList<Task> getAllTasks() {
        return tasks;
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param searchString The string to search for.
     * @return A list of matching tasks.
     */
    public ArrayList<Task> findTasks(String searchString) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerSearch = searchString.toLowerCase();

        for (Task task : tasks) {
            if (task.toString().toLowerCase().contains(lowerSearch)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
