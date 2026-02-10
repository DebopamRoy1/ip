package lebron;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import lebron.task.Deadline;

public class StorageTest {
    @Test
    public void testDeadlineReconstruction() {
        // Simulate a line from the text file
        String savedLine = "2026-12-25T18:00";
        LocalDateTime time = LocalDateTime.parse(savedLine);
        Deadline d = new Deadline("Final Exam", time);

        assertEquals("[D][ ]Final Exam (by: Dec 25 2026, 6:00 pm)", d.toString());
    }
}
