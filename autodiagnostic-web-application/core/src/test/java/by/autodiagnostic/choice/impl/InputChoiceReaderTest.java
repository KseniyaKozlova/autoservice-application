package by.autodiagnostic.choice.impl;

import by.autodiagnostic.choice.ChoiceReader;
import by.autodiagnostic.choice.ChoiceReaderException;
import by.autodiagnostic.distribution.Direction;
import by.autodiagnostic.distribution.SortChoice;
import by.autodiagnostic.distribution.SortType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputChoiceReaderTest {

    private ChoiceReader choiceReader;

    @BeforeEach
    void setUp() {
        choiceReader = new InputChoiceReader();
    }

    @Test
    void readChoiceTest_successful() throws ChoiceReaderException {
        final SortChoice sortChoice = new SortChoice(SortType.MODEL, Direction.ASC);
        final List<SortChoice> actual = List.of(sortChoice);

        final String step = "model, asc";
        final List<SortChoice> expected = choiceReader.readChoice(step);

        Assertions.assertNotNull(expected, "Requirement list is empty");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void readChoiceTest_throwsChoiceReaderException() {
        final String wrongStep = "model, ssc";

        final ChoiceReaderException exception = assertThrows(ChoiceReaderException.class, () -> choiceReader.readChoice(wrongStep));

        assertEquals("You entered incorrect sort meaning", exception.getMessage());
    }
}