package autodiagnostic.reader.impl;

import autodiagnostic.reader.TransportReader;
import autodiagnostic.reader.TransportReaderException;
import autodiagnostic.transport.Category;
import autodiagnostic.transport.CategoryException;
import autodiagnostic.transport.Transport;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TransportFileReader implements TransportReader {

    private final File fileName;
    private final Charset charset;

    public TransportFileReader(final File pathName, final Charset charset) {
        this.fileName = pathName;
        this.charset = charset;
    }

    public List<Transport> readTransport() throws TransportReaderException {
        final List<Transport> transports = new ArrayList<>();

        try (final BufferedReader reader = new BufferedReader(new FileReader(fileName, charset))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] trans = line.split(",\\s");
                transports.add(new Transport(Category.getCategoryByTitle(trans[0]), trans[1]));
            }
        } catch (final IOException | IllegalArgumentException | CategoryException e) {
            throw new TransportReaderException("Problem with file reading " + fileName, e);
        }
        return transports;
    }
}