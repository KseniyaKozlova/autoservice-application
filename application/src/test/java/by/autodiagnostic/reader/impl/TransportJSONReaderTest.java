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

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransportJSONReaderTest {
    private static final File TEST_READ_FILE = Path.of("src", "test", "resources", "transport-read.json").toFile();
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final String INITIAL_VALUE = """
            [
              {
                "type": "мотоцикл",
                "model": "Ninja ZX-14"
              },
              {
                "type": "микроавтобус",
                "model": "Sprinter264"
              },
              {
                "type": "автомобиль",
                "model": "Mazda CX7"
              }
            ]""";

    @Mock
    private Parser mockParser;

    private TransportReader transportJSONReader;

    @BeforeEach
    void setUP() {
        transportJSONReader = new TransportJSONReader(TEST_READ_FILE, CHARSET, mockParser);
    }

    @Test
    void readTransportTest_successful() throws ParserException, TransportReaderException {
        final Transport transport1 = new Transport(Category.MOTORBIKE, "Ninja ZX-14");
        final Transport transport2 = new Transport(Category.MINIBUS, "Sprinter264");
        final Transport transport3 = new Transport(Category.AUTOMOBILE, "Mazda CX7");

        final List<Transport> actual = List.of(transport1, transport2, transport3);

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