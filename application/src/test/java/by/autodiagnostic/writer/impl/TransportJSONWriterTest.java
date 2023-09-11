package by.autodiagnostic.writer.impl;

import by.autodiagnostic.distribution.Direction;
import by.autodiagnostic.distribution.SortChoice;
import by.autodiagnostic.distribution.SortType;
import by.autodiagnostic.distribution.TransportComparator;
import by.autodiagnostic.parser.ParserException;
import by.autodiagnostic.transport.Category;
import by.autodiagnostic.transport.Transport;
import by.autodiagnostic.validation.FieldValidator;
import by.autodiagnostic.validation.FieldValidatorException;
import by.autodiagnostic.writer.TransportWriter;
import by.autodiagnostic.writer.TransportWriterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransportJSONWriterTest {
    private static final File PROCESSED_FILE_TEST = Path.of("src", "test", "resources", "processed-transport-test.json").toFile();
    private static final File INVALID_FILE_TEST = Path.of("src", "test", "resources", "invalid transport-test.json").toFile();
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private Transport transport1;
    private Transport transport2;
    private Transport transport3;
    private List<Transport> transportList;
    private List<SortChoice> sortingRequirements;
    private TransportWriter transportJSONWriter;

    @Mock
    private FieldValidator mockFieldValidator;


    @BeforeEach
    void setUp() {
        transport1 = new Transport(Category.MOTORBIKE, "Ninja ZX-14");
        transport2 = new Transport(Category.MINIBUS, "Sprinter264");
        transport3 = new Transport(Category.AUTOMOBILE, "Mazda C>X7");

        transportList = List.of(transport1, transport2, transport3);

        sortingRequirements = new ArrayList<>();
        sortingRequirements.add(new SortChoice(SortType.TYPE, Direction.ASC));

        transportJSONWriter = new TransportJSONWriter(mockFieldValidator, PROCESSED_FILE_TEST, INVALID_FILE_TEST, CHARSET);
    }

    @AfterEach
    void checkMock() throws IOException, ParserException {
        INVALID_FILE_TEST.delete();
    }

    @Test
    void writeFileTest_processedFile_successful() throws TransportWriterException, IOException, FieldValidatorException {
        final String processedTextActual = """
                [
                 {
                  "type": "микроавтобус",
                  "model": "Sprinter264",
                  "cost": "30"
                 },
                 {
                  "type": "мотоцикл",
                  "model": "Ninja ZX-14",
                  "cost": "10"
                 }
                ]""";

        Mockito.doReturn(true).when(mockFieldValidator).workField(transport1);
        Mockito.doReturn(true).when(mockFieldValidator).workField(transport2);
        Mockito.doReturn(false).when(mockFieldValidator).workField(transport3);

        transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements));

        final String expected = Files.readString(PROCESSED_FILE_TEST.toPath(), CHARSET);

        Assertions.assertTrue(PROCESSED_FILE_TEST.exists());
        Assertions.assertEquals(expected, processedTextActual);

        Mockito.verify(mockFieldValidator).workField(transport1);
        Mockito.verify(mockFieldValidator).workField(transport2);
        Mockito.verify(mockFieldValidator).workField(transport3);
        Mockito.verifyNoMoreInteractions(mockFieldValidator);
    }

    @Test
    void writeFileTest_invalidFile_successful() throws TransportWriterException, IOException, FieldValidatorException {
        final String invalidTextActual = """

                {
                 "type": "автомобиль",
                 "model": "Mazda C>X7"
                }""";

        Mockito.doReturn(true).when(mockFieldValidator).workField(transport1);
        Mockito.doReturn(true).when(mockFieldValidator).workField(transport2);
        Mockito.doReturn(false).when(mockFieldValidator).workField(transport3);

        transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements));

        final String expected = Files.readString(INVALID_FILE_TEST.toPath(), CHARSET).replace("\r", "");

        Assertions.assertTrue(INVALID_FILE_TEST.exists());
        Assertions.assertEquals(expected, invalidTextActual);

        Mockito.verify(mockFieldValidator).workField(transport1);
        Mockito.verify(mockFieldValidator).workField(transport2);
        Mockito.verify(mockFieldValidator).workField(transport3);
        Mockito.verifyNoMoreInteractions(mockFieldValidator);
    }

    @Test
    void writeFileTest_throwsTransportWriterException() {
        PROCESSED_FILE_TEST.setReadOnly();

        final var error = Assertions.assertThrowsExactly(TransportWriterException.class,
                () -> transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements)));

        Assertions.assertEquals(error.getMessage(), "Problem with file");

        PROCESSED_FILE_TEST.setWritable(true);
    }
}
