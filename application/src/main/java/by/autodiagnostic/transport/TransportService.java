package by.autodiagnostic.transport;

import by.autodiagnostic.distribution.SortChoice;
import by.autodiagnostic.distribution.TransportComparator;
import by.autodiagnostic.menu.ConsoleInput;
import by.autodiagnostic.menu.MenuReader;
import by.autodiagnostic.menu.impl.ConsoleInputReader;
import by.autodiagnostic.menu.impl.ConsoleMenuReader;
import by.autodiagnostic.parser.Parser;
import by.autodiagnostic.parser.impl.JSONParser;
import by.autodiagnostic.printer.MessagePrinter;
import by.autodiagnostic.printer.impl.PathMessagePrinter;
import by.autodiagnostic.reader.TransportReader;
import by.autodiagnostic.reader.impl.TransportJSONReader;
import by.autodiagnostic.validation.FieldValidator;
import by.autodiagnostic.validation.impl.TransportFieldValidator;
import by.autodiagnostic.writer.TransportWriter;
import by.autodiagnostic.writer.impl.TransportJSONWriter;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class TransportService {

    private static final File READ_TRANSPORT_FILE = Path.of("src", "main", "resources", "transport.json").toFile();
    private static final File PROCESSED_TRANSPORT_FILE = Path.of("src", "main", "resources", "processed-transport.json").toFile();
    private static final File INVALID_TRANSPORT_FILE = Path.of("src", "main", "resources", "invalid transport.json").toFile();
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public void startApplication() {
        try {
            final Parser parser = new JSONParser();

            final TransportReader transportReader = new TransportJSONReader(READ_TRANSPORT_FILE, CHARSET, parser);
            final List<Transport> transports = transportReader.readTransport();

            final ConsoleInput consoleInput = new ConsoleInputReader();
            final MenuReader menuReader = new ConsoleMenuReader(consoleInput);
            final List<SortChoice> requirements = menuReader.readChoice();

            final FieldValidator fieldValidator = new TransportFieldValidator();
            final TransportWriter transportWriter = new TransportJSONWriter(fieldValidator, PROCESSED_TRANSPORT_FILE,
                    INVALID_TRANSPORT_FILE, CHARSET);
            transportWriter.writeFile(transports, new TransportComparator(requirements));

            final MessagePrinter messagePrinter = new PathMessagePrinter();
            messagePrinter.print(PROCESSED_TRANSPORT_FILE);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
