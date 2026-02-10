package lebron.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific deadline date and optional time.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
    protected LocalDateTime by;

    /**
     * Initialises a new deadline
     *
     * @param description The text describing the task
     * @param by The deadline of the task
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + getStatusBinary() + " | " + description + " | " + by;
    }
}
