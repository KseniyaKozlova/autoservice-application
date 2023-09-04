package by.autodiagnostic.menu.impl;

import by.autodiagnostic.distribution.Direction;
import by.autodiagnostic.distribution.SortChoice;
import by.autodiagnostic.distribution.SortType;
import by.autodiagnostic.menu.ConsoleInput;
import by.autodiagnostic.menu.MenuReaderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class ConsoleMenuReaderTest {

    @Test
    void readChoiceTest_successful() throws MenuReaderException {
        final String testInput = "type, asc";

        final SortChoice sortChoice = new SortChoice(SortType.TYPE, Direction.ASC);
        final var actualRequirements = List.of(sortChoice);

        final var mockInput = Mockito.mock(ConsoleInput.class);
        Mockito.when(mockInput.readLine()).thenReturn(testInput).thenReturn("stop");

        final var consoleMenuReader = new ConsoleMenuReader(mockInput);
        final var expectedRequirements = consoleMenuReader.readChoice();

        Assertions.assertEquals(expectedRequirements, actualRequirements);
        Mockito.verify(mockInput, Mockito.times(2)).readLine();
        Mockito.verifyNoMoreInteractions(mockInput);
    }

    @Test
    void readChoiceTest_throwsMenuReaderException() {
        final var mockInput = Mockito.mock(ConsoleInput.class);
        Mockito.when(mockInput.readLine()).thenThrow(IllegalArgumentException.class);

        final var consoleMenuReader = new ConsoleMenuReader(mockInput);
        final var exception = Assertions.assertThrowsExactly(MenuReaderException.class, consoleMenuReader::readChoice);

        Assertions.assertEquals(exception.getMessage(), "You entered incorrect sort meaning");
        Mockito.verify(mockInput, Mockito.times(1)).readLine();
        Mockito.verifyNoMoreInteractions(mockInput);
    }
}