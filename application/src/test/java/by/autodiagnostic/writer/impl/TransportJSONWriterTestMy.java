//package by.autodiagnostic.writer.impl;
//
//import by.autodiagnostic.distribution.Direction;
//import by.autodiagnostic.distribution.SortChoice;
//import by.autodiagnostic.distribution.SortType;
//import by.autodiagnostic.distribution.TransportComparator;
//import by.autodiagnostic.parser.ParserException;
//import by.autodiagnostic.transport.Category;
//import by.autodiagnostic.transport.Transport;
//import by.autodiagnostic.writer.TransportWriter;
//import by.autodiagnostic.writer.TransportWriterException;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
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
//@ExtendWith(MockitoExtension.class)
//class TransportJSONWriterTestMy {
//
//    private static final File PROCESSED_FILE_TEST = Path.of("src", "test", "resources", "processed-transport-test.json").toFile();
//    private static final File INVALID_FILE_TEST = Path.of("src", "test", "resources", "invalid transport-test.json").toFile();
//    private static final Charset CHARSET = StandardCharsets.UTF_8;
////    private static final Transport TRANSPORT = new Transport(Category.MOTORBIKE, "Ninja ZX-14");
////    private static final Transport TRANSPORT_INVALID = new Transport(Category.AUTOMOBILE, "Mazda C>X7");
////    private static final boolean TEST_BOOLEAN = true;
//
//    private List<Transport> transportList;
//    private List<SortChoice> sortingRequirements;
//    private TransportWriter transportJSONWriter;
////    private Map<String, String> mapForJson = new LinkedHashMap<>();
////    private JSONObject jsonObject;
////    private JSONObject jsonObjectInvalid;
//
////    @Mock
////    private JSONParser mockJsonParser;
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
//
////        mapForJson = new LinkedHashMap<>();
////        mapForJson.put("type", "мотоцикл");
////        mapForJson.put("model", "Ninja ZX-14");
////        mapForJson.put("cost", "10");
//
////        jsonObject = new JSONObject(mapForJson);
//
////        Map<String, String> mapForJsonInvalid = new LinkedHashMap<>();
////        mapForJson.put("type", "автомобиль");
////        mapForJson.put("model", "Mazda C>X7");
////        mapForJson.put("cost", "10");
//
////        jsonObjectInvalid = new JSONObject(mapForJsonInvalid);
//    }
//
//    @AfterEach
//    void checkMock() throws IOException, ParserException {
////        Mockito.verify(mockJsonParser).createJSON(TRANSPORT, false);
////        Mockito.verify(mockJsonParser).createJSON(TRANSPORT_INVALID, false);
////        Mockito.verifyNoMoreInteractions(mockJsonParser);
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
////        mapForJson.put("type", "мотоцикл");
////        mapForJson.put("model", "Ninja ZX-14");
////        mapForJson.put("cost", "10");
////        JSONObject jsonObject = new JSONObject(mapForJson);
//
////        Mockito.doReturn(jsonObject).when(mockJsonParser).createJSON(TRANSPORT, false);
////        Mockito.when(mockJsonParser.createJSON(any(), false)).thenReturn(jsonObject).thenReturn(jsonObjectInvalid);
////        Mockito.doReturn(jsonObjectInvalid).when(mockJsonParser).createJSON(TRANSPORT_INVALID, false);
//
//        transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements));
//
//        String expected = Files.readString(PROCESSED_FILE_TEST.toPath(), CHARSET);
//
//        Assertions.assertTrue(PROCESSED_FILE_TEST.exists());
//        Assertions.assertEquals(expected, processedTextActual);
//    }

//    @Test
//    void writeFileTest_invalidFile_successful() throws TransportWriterException, IOException {
//        final String invalidTextActual = """
//
//                {
//                 "type": "автомобиль",
//                 "model": "Mazda C>X7"
//                }""";
//
//        Mockito.doReturn(new FileWriter(PROCESSED_FILE_TEST)).when(mockFileCreator).createFile(PROCESSED_FILE_TEST, CHARSET);
//
//        transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements));
//
//        String expected = Files.readString(INVALID_FILE_TEST.toPath(), CHARSET).replace("\r", "");
//
//        Assertions.assertTrue(INVALID_FILE_TEST.exists());
//        Assertions.assertEquals(expected, invalidTextActual);
//    }
//
//    @Test
//    void writeFileTest_readFile_successful() throws TransportWriterException, IOException {
//        final String readTextActual = """
//
//                {
//                 "type": "мотоцикл",
//                 "model": "Ninja ZX-14"
//                }
//                {
//                 "type": "микроавтобус",
//                 "model": "Sprinter264"
//                }""";
//
//        Mockito.doReturn(new FileWriter(PROCESSED_FILE_TEST)).when(mockFileCreator).createFile(PROCESSED_FILE_TEST, CHARSET);
//
//        transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements));
//
//        String expected = Files.readString(READ_FILE_TEST.toPath(), CHARSET).replace("\r", "");
//
//        Assertions.assertTrue(READ_FILE_TEST.exists());
//        Assertions.assertEquals(expected, readTextActual);
//    }

//    @Test
//    void writeFileTest_throwsTransportWriterException() throws IOException {
//        Mockito.doThrow(IOException.class).when(mockFileCreator).createFile(PROCESSED_FILE_TEST, CHARSET);
//
//        final var error = Assertions.assertThrowsExactly(TransportWriterException.class,
//                () -> transportJSONWriter.writeFile(transportList, new TransportComparator(sortingRequirements)));
//
//        Assertions.assertEquals(error.getMessage(), "Problem with file");
//    }
//}