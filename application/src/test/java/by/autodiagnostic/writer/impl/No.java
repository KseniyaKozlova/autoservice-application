//package by.autodiagnostic.writer.impl;
//
//import by.autodiagnostic.distribution.Direction;
//import by.autodiagnostic.distribution.SortChoice;
//import by.autodiagnostic.distribution.SortType;
//import by.autodiagnostic.distribution.TransportComparator;
//import by.autodiagnostic.transport.Category;
//import by.autodiagnostic.transport.Transport;
//import by.autodiagnostic.writer.TransportWriter;
//import by.autodiagnostic.writer.TransportWriterException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TransportJSONWriterTest {
//
//    private static final File PROCESSED_FILE_TEST = Path.of("src", "test", "resources", "processed-transport-test.json").toFile();
//    private static final File INVALID_FILE_TEST = Path.of("src", "test", "resources", "invalid transport-test.json").toFile();
//    private static final Charset CHARSET = StandardCharsets.UTF_8;
//
//    private List<Transport> transportList;
//    private List<SortChoice> sortingRequirements;
//    private TransportWriter transportJSONWriter;
//
//    @BeforeEach
//    void setUp() {
//        final Transport transport1 = new Transport(Category.MOTORBIKE, "Ninja ZX-14");
//        final Transport transport2 = new Transport(Category.MINIBUS, "Sprinter264");
//        final Transport transport3 = new Transport(Category.AUTOMOBILE, "Mazda C>X7");
//
//        transportList = List.of(transport1, transport2, transport3);
//
//        sortingRequirements = new ArrayList<>();
//        sortingRequirements.add(new SortChoice(SortType.TYPE, Direction.ASC));
//
//        transportJSONWriter = new TransportJSONWriter(PROCESSED_FILE_TEST, INVALID_FILE_TEST, CHARSET);
//    }
//
//    @Test
//    void writeFileTest_processedFile_successful() throws TransportWriterException, IOException {
//        final String processedTextActual = """
//                [
//                 {
//                  "type": "микроавтобус",
//                  "model": "Sprinter264",
//                  "cost": "30"
//                 },
//                 {
//                  "type": "мотоцикл",
//                  "model": "Ninja ZX-14",
//                  "cost": "10"
//                 }
//                ]""";
//
//        transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements));
//
//        String expected = Files.readString(PROCESSED_FILE_TEST.toPath(), CHARSET);
//
//        Assertions.assertTrue(PROCESSED_FILE_TEST.exists());
//        Assertions.assertEquals(expected, processedTextActual);
//
//        INVALID_FILE_TEST.delete();
//    }
//
//    @Test
//    void writeFileTest_invalidFile_successful() throws TransportWriterException, IOException {
//        final String invalidTextActual = """
//
//                {
//                 "type": "автомобиль",
//                 "model": "Mazda C>X7"
//                }""";
//
//        transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements));
//
//        String expected = Files.readString(INVALID_FILE_TEST.toPath(), CHARSET).replace("\r", "");
//
//        Assertions.assertTrue(INVALID_FILE_TEST.exists());
//        Assertions.assertEquals(expected, invalidTextActual);
//
//        INVALID_FILE_TEST.delete();
//    }
//
//    @Test
//    void writeFileTest_throwsTransportWriterException() {
//
//        PROCESSED_FILE_TEST.setReadOnly();
//
//        final var error = Assertions.assertThrows(TransportWriterException.class,
//                () -> transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements)));
//
//        Assertions.assertEquals("Problem with file", error.getMessage());
//
//        PROCESSED_FILE_TEST.delete();
//    }
//}
