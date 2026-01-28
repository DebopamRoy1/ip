/**
 * An abstract representation of a task in the LeBron system.
 * Encapsulates the description and completion status of a task.
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initialises a new task with a description.
     *
     * @param description The text describing the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns "X", representing the completion status of the task.
     *
     * @return "X" if the task is done, and a blank space otherwise.
     */
    public String getStatusIcon() {
        if (isDone) {
            return "X";
        } else {
            return " ";
        }
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed yet.
     */
    public void markAsUndone() {
        this.isDone = false;
    }

    /**
     * Returns binary representation of the task for file storage.
     *
     * @return "1" if done, "0" otherwise.
     */
    public String getStatusBinary() {
        return (isDone ? "1" : "0");
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "]" + description;
    }

    /**
     * Returns the string representation of the task formatted for a text file.
     *
     * @return Formatted string for saving to disk.
     */
    public abstract String toFileFormat();
}
