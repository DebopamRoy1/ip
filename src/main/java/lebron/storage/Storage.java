package lebron.storage;

import lebron.task.Deadline;
import lebron.task.Event;
import lebron.task.Task;
import lebron.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles the loading and saving of task data onto a hard disk.
 * Encapsulates file IO operations for LeBron's list.
 */
public class Storage {
    private String filePath;

    /**
     * Initialises the storage with a specific file path.
     *
     * @param filePath Relative path to the save file.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Writes the current list of tasks to the save file.
     * Uses toFileFormat() from each task to create the text line.
     *
     * @param tasks The ArrayList of tasks to be saved.
     * @throws IOException If the file cannot be written to.
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();

        FileWriter fw = new FileWriter(file);
        for (Task t : tasks) {
            fw.write(t.toFileFormat() + System.lineSeparator());
        }
        fw.close();
    }


    /**
     * Loads tasks from the data file.
     * If the file doesn't exist, an empty list is returned.
     *
     * @return An ArrayList containing the loaded tasks.
     * @throws IOException If there is an error reading the file.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks;
        }

        Scanner s = new Scanner(file);
        while (s.hasNext()) {
            String line = s.nextLine();
            String[] parts = line.split(" \\| ");
            Task task;

            // Reconstruct the specific task type based on the first letter
            switch (parts[0]) {
            case "T":
                task = new Todo(parts[2]);
                break;
            case "D":
                task = new Deadline(parts[2], LocalDateTime.parse(parts[3]));
                break;
            case "E":
                task = new Event(parts[2], LocalDateTime.parse(parts[3]), LocalDateTime.parse(parts[4]));
                break;
            default:
                continue;
            }

            if (parts[1].equals("1")) {
                task.markAsDone();
            }
            tasks.add(task);
        }
        s.close();
        return tasks;
    }
}
