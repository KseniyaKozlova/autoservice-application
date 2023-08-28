package by.autodiagnostic.reader.impl;

import by.autodiagnostic.parser.Parser;
import by.autodiagnostic.parser.ParserException;
import by.autodiagnostic.reader.TransportReader;
import by.autodiagnostic.reader.TransportReaderException;
import by.autodiagnostic.transport.CategoryException;
import by.autodiagnostic.transport.Transport;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class TransportJSONReader implements TransportReader {

    private final File fileName;
    private final Charset charset;
    private final Parser parser;

    public TransportJSONReader(final File fileName, final Charset charset, final Parser parser) {
        this.fileName = fileName;
        this.charset = charset;
        this.parser = parser;
    }

    @Override
    public List<Transport> readTransport() throws TransportReaderException {
        try {
            final String text = Files.readString(fileName.toPath(), charset);
            return parser.parse(text);
        } catch (final IOException | CategoryException | ParserException e) {
            throw new TransportReaderException("Problem with file reading", e);
        }
    }
}
