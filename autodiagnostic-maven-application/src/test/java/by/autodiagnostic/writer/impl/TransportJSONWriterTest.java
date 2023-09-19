package by.autodiagnostic.writer.impl;

import by.autodiagnostic.distribution.Direction;
import by.autodiagnostic.distribution.SortChoice;
import by.autodiagnostic.distribution.SortType;
import by.autodiagnostic.distribution.TransportComparator;
import by.autodiagnostic.parser.Parser;
import by.autodiagnostic.transport.Category;
import by.autodiagnostic.transport.Transport;
import by.autodiagnostic.validation.FieldValidator;
import by.autodiagnostic.validation.FieldValidatorException;
import by.autodiagnostic.writer.TransportWriter;
import by.autodiagnostic.writer.TransportWriterException;
import org.json.JSONObject;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class TransportJSONWriterTest {

    private static final File PROCESSED_FILE_TEST = Path.of("src", "test", "resources", "processed-transport-test.json").toFile();
    private static final File INVALID_FILE_TEST = Path.of("src", "test", "resources", "invalid transport-test.json").toFile();
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final Transport TRANSPORT_1 = new Transport(Category.MOTORBIKE, "Ninja ZX-14");
    private static final Transport TRANSPORT_2 = new Transport(Category.MINIBUS, "Sprinter264");
    private static final Transport TRANSPORT_3 = new Transport(Category.AUTOMOBILE, "Mazda C>X7");

    private List<Transport> transportList;
    private List<SortChoice> sortingRequirements;
    private TransportWriter transportJSONWriter;
    private JSONObject jsonObject1;
    private JSONObject jsonObject2;
    private JSONObject jsonObject3;

    @Mock
    private FieldValidator mockFieldValidator;
    @Mock
    private Parser mockParser;


    @BeforeEach
    void setUp() {
        transportList = List.of(TRANSPORT_1, TRANSPORT_2, TRANSPORT_3);

        sortingRequirements = new ArrayList<>();
        sortingRequirements.add(new SortChoice(SortType.TYPE, Direction.ASC));

        transportJSONWriter = new TransportJSONWriter(mockFieldValidator, mockParser, PROCESSED_FILE_TEST, INVALID_FILE_TEST, CHARSET);

        final Map<String, String> map1 = new LinkedHashMap<>();
        map1.put("type", "мотоцикл");
        map1.put("model", "Ninja ZX-14");
        map1.put("cost", "10");
        jsonObject1 = new JSONObject(map1);

        final Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("type", "микроавтобус");
        map2.put("model", "Sprinter264");
        map2.put("cost", "30");
        jsonObject2 = new JSONObject(map2);

        final Map<String, String> map3 = new LinkedHashMap<>();
        map3.put("type", "автомобиль");
        map3.put("model", "Mazda C>X7");
        jsonObject3 = new JSONObject(map3);
    }

    @AfterEach
    void deleteFiles() {
        INVALID_FILE_TEST.delete();
    }

    @Test
    void writeFileTest_processedFile_successful() throws TransportWriterException, IOException, FieldValidatorException, IllegalAccessException {
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

        Mockito.doReturn(true).when(mockFieldValidator).workField(TRANSPORT_1);
        Mockito.doReturn(true).when(mockFieldValidator).workField(TRANSPORT_2);
        Mockito.doReturn(false).when(mockFieldValidator).workField(TRANSPORT_3);

        Mockito.doReturn(jsonObject1).when(mockParser).createJSON(TRANSPORT_1, true);
        Mockito.doReturn(jsonObject2).when(mockParser).createJSON(TRANSPORT_2, true);
        Mockito.doReturn(jsonObject3).when(mockParser).createJSON(TRANSPORT_3, false);

        transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements));

        final String expected = Files.readString(PROCESSED_FILE_TEST.toPath(), CHARSET);

        Assertions.assertTrue(PROCESSED_FILE_TEST.exists());
        Assertions.assertEquals(expected, processedTextActual);

        Mockito.verify(mockFieldValidator).workField(TRANSPORT_1);
        Mockito.verify(mockFieldValidator).workField(TRANSPORT_2);
        Mockito.verify(mockFieldValidator).workField(TRANSPORT_3);

        Mockito.verify(mockParser).createJSON(TRANSPORT_1, true);
        Mockito.verify(mockParser).createJSON(TRANSPORT_2, true);
        Mockito.verify(mockParser).createJSON(TRANSPORT_3, false);

        Mockito.verifyNoMoreInteractions(mockFieldValidator);
    }

    @Test
    void writeFileTest_invalidFile_successful() throws TransportWriterException, IOException, FieldValidatorException, IllegalAccessException {
        final String invalidTextActual = """

                {
                 "type": "автомобиль",
                 "model": "Mazda C>X7"
                }""";

        Mockito.doReturn(true).when(mockFieldValidator).workField(TRANSPORT_1);
        Mockito.doReturn(true).when(mockFieldValidator).workField(TRANSPORT_2);
        Mockito.doReturn(false).when(mockFieldValidator).workField(TRANSPORT_3);

        Mockito.doReturn(jsonObject1).when(mockParser).createJSON(TRANSPORT_1, true);
        Mockito.doReturn(jsonObject2).when(mockParser).createJSON(TRANSPORT_2, true);
        Mockito.doReturn(jsonObject3).when(mockParser).createJSON(TRANSPORT_3, false);

        transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements));

        final String expected = Files.readString(INVALID_FILE_TEST.toPath(), CHARSET).replace("\r", "");

        Assertions.assertTrue(INVALID_FILE_TEST.exists());
        Assertions.assertEquals(expected, invalidTextActual);

        Mockito.verify(mockFieldValidator).workField(TRANSPORT_1);
        Mockito.verify(mockFieldValidator).workField(TRANSPORT_2);
        Mockito.verify(mockFieldValidator).workField(TRANSPORT_3);

        Mockito.verify(mockParser).createJSON(TRANSPORT_1, true);
        Mockito.verify(mockParser).createJSON(TRANSPORT_2, true);
        Mockito.verify(mockParser).createJSON(TRANSPORT_3, false);

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
