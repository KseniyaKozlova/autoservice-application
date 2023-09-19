package by.autodiagnostic.transport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void getCategoryByTitleTest_successful() throws CategoryException {
        final var expected = Category.getCategoryByTitle("автомобиль");

        Assertions.assertEquals(expected, Category.AUTOMOBILE);
    }

    @Test
    void getCategoryByTitleTest_throwsParserException() {
        final var name = "комбайн";
        final var exception = Assertions.assertThrows(CategoryException.class, () -> Category.getCategoryByTitle(name));

        Assertions.assertEquals("File contains nonexistent category: " + name, exception.getMessage());
    }
}