package by.autodiagnostic.reader.impl;

import by.autodiagnostic.parser.Parser;
import by.autodiagnostic.parser.ParserException;
import by.autodiagnostic.reader.TransportReader;
import by.autodiagnostic.reader.TransportReaderException;
import by.autodiagnostic.transport.Transport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static by.autodiagnostic.util.StandardConstants.CHARSET;

public class TransportJSONReader implements TransportReader {

    private final InputStream inputStream;
    private final Parser parser;

    public TransportJSONReader(final InputStream inputStream, final Parser parser) {
        this.inputStream = inputStream;
        this.parser = parser;
    }

    @Override
    public List<Transport> readTransport() throws TransportReaderException {
        if (inputStream == null) {
            throw new TransportReaderException("Entered meaning is empty");
        }

        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, CHARSET))) {
            final String body = bufferedReader.lines().reduce("", String::concat);
            return parser.parse(body);
        } catch (final IOException | ParserException e) {
            throw new TransportReaderException("Problem with file reading", e);
        }
    }
}
