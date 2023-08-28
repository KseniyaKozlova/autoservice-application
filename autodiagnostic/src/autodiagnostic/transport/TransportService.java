package autodiagnostic.transport;

import autodiagnostic.distribution.SortChoice;
import autodiagnostic.distribution.TransportComparator;
import autodiagnostic.menu.MenuReader;
import autodiagnostic.menu.impl.ConsoleMenuReader;
import autodiagnostic.reader.TransportReader;
import autodiagnostic.reader.impl.TransportFileReader;
import autodiagnostic.writer.TransportWriter;
import autodiagnostic.writer.impl.TransportFileWriter;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class TransportService {

    private static final File READ_TRANSPORT_FILE = Path.of("resources", "transport.txt").toFile();
    private static final File PROCESSED_TRANSPORT_FILE = Path.of("resources", "processed-transport.txt").toFile();
    private static final File INVALID_TRANSPORT_FILE = Path.of("resources", "invalid transport.txt").toFile();
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public void startApplication() {
        try {
            final TransportReader transportReader = new TransportFileReader(READ_TRANSPORT_FILE, CHARSET);
            final List<Transport> transports = transportReader.readTransport();

            final MenuReader menuReader = new ConsoleMenuReader();
            final List<SortChoice> requirements = menuReader.readChoice();

            final TransportWriter transportWriter = new TransportFileWriter(PROCESSED_TRANSPORT_FILE,
                    INVALID_TRANSPORT_FILE, READ_TRANSPORT_FILE, CHARSET);
            transportWriter.writeFile(transports, new TransportComparator(requirements));

            transports.forEach(System.out::println);
            System.out.println(Path.of("resources", "processed-transport.txt").toAbsolutePath());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
