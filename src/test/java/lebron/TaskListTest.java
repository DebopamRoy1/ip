package lebron;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lebron.task.Todo;
import lebron.tasklist.TaskList;

public class TaskListTest {
    @Test
    public void testAddAndDelete() {
        TaskList list = new TaskList();
        Todo t = new Todo("test task");

        list.add(t);
        assertEquals(1, list.size());

        list.delete(0);
        assertEquals(0, list.size());
    }
}
