package by.autodiagnostic.transport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryTest {

    @Test
    void getCategoryByTitleTest_successful() throws CategoryException {
        final Category expected = Category.getCategoryByTitle("автомобиль");

        assertEquals(expected, Category.AUTOMOBILE);
    }

    @Test
    void getCategoryByTitleTest_throwsParserException() {
        final var name = "комбайн";
        final var exception = assertThrows(CategoryException.class, () -> Category.getCategoryByTitle(name));

        assertEquals("File contains nonexistent category: " + name, exception.getMessage());
    }
}