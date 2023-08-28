package by.autodiagnostic.writer.impl;

import by.autodiagnostic.distribution.Direction;
import by.autodiagnostic.distribution.SortChoice;
import by.autodiagnostic.distribution.SortType;
import by.autodiagnostic.distribution.TransportComparator;
import by.autodiagnostic.transport.Category;
import by.autodiagnostic.transport.Transport;
import by.autodiagnostic.writer.TransportWriterException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransportJSONWriterTest {

    private static final File READ_TRANSPORT_FILE = Path.of("src", "test", "resources", "transport.json").toFile();
    private static final File PROCESSED_TRANSPORT_FILE = Path.of("src", "test", "resources", "processed-transport.json").toFile();
    private static final File INVALID_TRANSPORT_FILE = Path.of( "src", "test", "resources", "invalid transport.json").toFile();
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    @Test
    void testWriteFile_successfull() throws TransportWriterException, IOException {
        final Transport transport1 = new Transport(Category.MOTORBIKE, "Ninja ZX-14");
        final Transport transport2 = new Transport(Category.MINIBUS, "Sprinter264");
        final Transport transport3 = new Transport(Category.AUTOMOBILE, "Mazda CX7");

        List<Transport> transportList = new ArrayList<>();
        transportList.add(transport1);
        transportList.add(transport2);
        transportList.add(transport3);

        SortChoice sortChoice = new SortChoice(SortType.TYPE, Direction.ASC);
        List<SortChoice> sortChoiceList = new ArrayList<>();
        sortChoiceList.add(sortChoice);

        Comparator <Transport> transportComparator = new TransportComparator(sortChoiceList);
        final var writer = new TransportJSONWriter(PROCESSED_TRANSPORT_FILE, INVALID_TRANSPORT_FILE, READ_TRANSPORT_FILE, CHARSET);

        writer.writeFile(transportList, transportComparator);

        final var actual = new String(Files.readAllBytes(PROCESSED_TRANSPORT_FILE.toPath()));

        transportList.sort(transportComparator);
        final var expected = Arrays.toString(transportList.toArray());

        assertEquals(expected, actual);
    }
}