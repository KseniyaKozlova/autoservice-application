package autodiagnostic.writer.impl;

import autodiagnostic.transport.Transport;
import autodiagnostic.writer.TransportWriterException;
import autodiagnostic.writer.TransportWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class TransportFileWriter implements TransportWriter {

    private static final Pattern PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-\\s]+[0-9]|[a-zA-Z]$");

    private final File processedTransportPath;
    private final File invalidTransportPath;
    private final File readPath;
    private final Charset encoding;

    public TransportFileWriter(final File processedTransportPath, final File invalidTransportPath,
                               final File readPath, final Charset encoding) {
        this.processedTransportPath = processedTransportPath;
        this.invalidTransportPath = invalidTransportPath;
        this.readPath = readPath;
        this.encoding = encoding;
    }

    public void writeFile(final List<Transport> transportList, final Comparator<Transport> transportComparator)
            throws TransportWriterException {
        final List<Transport> rightTransportList = writeChangedFile(transportList);

        rightTransportList.sort(transportComparator);

        try (final FileWriter sortedTransport = new FileWriter(processedTransportPath, encoding)) {

            for (Transport transports : rightTransportList) {
                final String type = transports.getType();
                final String model = transports.getModel();
                final Integer cost = transports.getPrice();
                sortedTransport.write(type + "|" + model + "|" + cost + System.lineSeparator());
            }
        } catch (final IOException e) {
            throw new TransportWriterException("Problem with file reading", e);
        }
    }

    private List<Transport> writeChangedFile(final List<Transport> transportList) throws TransportWriterException {
        final List<Transport> transports = new ArrayList<>();

        try (final FileWriter rightTransportWriter = new FileWriter(readPath, encoding, true);
             final FileWriter invalidTransportWriter = new FileWriter(invalidTransportPath, encoding, true)) {
            for (Transport element : transportList) {

                if (PATTERN.matcher(element.getModel()).matches()) {
                    rightTransportWriter.append(System.lineSeparator())
                            .append(String.valueOf(element));
                    transports.add(element);
                } else {
                    invalidTransportWriter.append(String.valueOf(element))
                            .append(System.lineSeparator());
                }

            }
        } catch (final IOException e) {
            throw new TransportWriterException("Problem with file reading", e);
        }
        return transports;
    }
}