package lebron;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lebron.parser.Parser;

/**
 * Tests the Parser class for correct index parsing and error handling.
 */
public class ParserTest {
    @Test
    public void parseTodo_emptyDescription_throwsException() {
        LebronException thrown = assertThrows(LebronException.class, () -> {
            Parser.parseTodo("todo   ");
        });
        assertTrue(thrown.getMessage().contains("can't be empty"));
    }

    @Test
    public void parseDeadline_invalidDateFormat_throwsException() {
        // LeBron expects yyyy-MM-dd HHmm
        assertThrows(LebronException.class, () -> {
            Parser.parseDeadline("deadline return book /by 12-12-2025 6pm");
        });
    }

    @Test
    public void parseIndex_outOfBounds_throwsException() {
        // Testing marking task 10 when only 5 exist
        LebronException thrown = assertThrows(LebronException.class, () -> {
            Parser.parseIndex("mark 10", "mark", 5);
        });
        assertTrue(thrown.getMessage().contains("rotation"));
    }
}
