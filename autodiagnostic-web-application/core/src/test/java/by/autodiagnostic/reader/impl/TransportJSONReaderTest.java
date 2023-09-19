package by.autodiagnostic.reader.impl;

import by.autodiagnostic.parser.Parser;
import by.autodiagnostic.parser.ParserException;
import by.autodiagnostic.reader.TransportReader;
import by.autodiagnostic.reader.TransportReaderException;
import by.autodiagnostic.transport.Category;
import by.autodiagnostic.transport.Transport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransportJSONReaderTest {

    private static final String INITIAL_VALUE = "[ { \"type\": \"мотоцикл\", \"model\": \"Ninja ZX-14\" }, { \"type\": \"микроавтобус\", \"model\": \"Sprinter264\" }, { \"type\": \"автомобиль\", \"model\": \"Mazda CX7\" }]";

    @Mock
    private Parser mockParser;

    private TransportReader transportJSONReader;

    @BeforeEach
    void setUP() {
        final var inputStream = new ByteArrayInputStream(INITIAL_VALUE.getBytes());
        transportJSONReader = new TransportJSONReader(inputStream, mockParser);
    }

    @Test
    void readTransportTest_successful() throws ParserException, TransportReaderException {
        final var transport1 = new Transport(Category.MOTORBIKE, "Ninja ZX-14");
        final var transport2 = new Transport(Category.MINIBUS, "Sprinter264");
        final var transport3 = new Transport(Category.AUTOMOBILE, "Mazda CX7");

        final var actual = List.of(transport1, transport2, transport3);

        Mockito.doReturn(actual).when(mockParser).parse(INITIAL_VALUE);
        final var expected = transportJSONReader.readTransport();

        Assertions.assertEquals(expected, actual);
        Mockito.verify(mockParser).parse(INITIAL_VALUE);
        Mockito.verifyNoMoreInteractions(mockParser);
    }

    @Test
    void readTransportTest_throwsTransportReaderException() throws ParserException {
        Mockito.doThrow(ParserException.class).when(mockParser).parse(INITIAL_VALUE);

        final var error = Assertions.assertThrowsExactly(TransportReaderException.class, transportJSONReader::readTransport);

        Assertions.assertEquals(error.getMessage(), "Problem with file reading");
        Mockito.verify(mockParser).parse(INITIAL_VALUE);
    }
}