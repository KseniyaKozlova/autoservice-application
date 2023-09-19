package by.autodiagnostic.validation;

import by.autodiagnostic.transport.Category;
import by.autodiagnostic.transport.Transport;
import by.autodiagnostic.validation.impl.TransportFieldValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransportFieldValidatorTest {

    private FieldValidator fieldValidator;

    @BeforeEach
    void setUp() {
        fieldValidator = new TransportFieldValidator();
    }

    @Test
    void workFieldTest_isValid_successful() throws FieldValidatorException {
        final Object transport = new Transport(Category.MOTORBIKE, "BMW 5");

        final boolean actual = true;

        final boolean expected = fieldValidator.workField(transport);

        assertEquals(expected, actual);
    }

    @Test
    void workFieldTest_notValid_successful() throws FieldValidatorException {
        final Object transport = new Transport(Category.MOTORBIKE, "BMW 5&S");

        final boolean actual = false;

        final boolean expected = fieldValidator.workField(transport);

        assertEquals(expected, actual);
    }
}