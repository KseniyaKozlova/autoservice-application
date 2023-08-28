package by.autodiagnostic.transport;

import by.autodiagnostic.parser.ParserException;
import by.autodiagnostic.parser.impl.JSONParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void getCategoryByTitle() throws CategoryException {
        final Category expected = Category.getCategoryByTitle("автомобиль");

        assertEquals(expected, Category.AUTOMOBILE);
    }

    @Test
    void testParse_throwsParserException() {
        final var name = "комбайн";
        final var exception = assertThrows(CategoryException.class, () -> Category.getCategoryByTitle(name));

        assertEquals("File contains nonexistent category: " + name, exception.getMessage());
    }
}