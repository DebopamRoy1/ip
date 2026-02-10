package lebron;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lebron.task.Todo;

/**
 * Tests the Todo class functionality.
 */
public class TodoTest {
    @Test
    public void testStringConversion() {
        Todo todo = new Todo("join sports club");
        assertEquals("[T][ ]join sports club", todo.toString());
    }

    @Test
    public void testMarkingLogic() {
        Todo todo = new Todo("practice free throws");
        todo.markAsDone();
        assertEquals("[T][X]practice free throws", todo.toString());
        assertEquals("T | 1 | practice free throws", todo.toFileFormat());

        todo.markAsUndone();
        assertEquals("[T][ ]practice free throws", todo.toString());
    }
}
