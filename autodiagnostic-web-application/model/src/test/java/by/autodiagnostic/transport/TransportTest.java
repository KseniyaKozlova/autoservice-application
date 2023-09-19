package by.autodiagnostic.transport;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransportTest {

    @Test
    void getTypeTest_successful() {
        final String expected = Category.AUTOMOBILE.getTitle();

        final Transport transport = new Transport(Category.AUTOMOBILE, "Audi");
        final String actual = transport.getType();

        assertEquals(expected, actual);
    }

    @Test
    void getPriceTest_successful() {
        final Integer expected = 20;

        final Transport transport = new Transport(Category.AUTOMOBILE, "Audi");
        final Integer actual = transport.getPrice();

        assertEquals(expected, actual);
    }

    @Test
    void getModelTest_successful() {
        final String expected = "Audi";

        final Transport transport = new Transport(Category.AUTOMOBILE, "Audi");
        final String actual = transport.getModel();

        assertEquals(expected, actual);
    }
}