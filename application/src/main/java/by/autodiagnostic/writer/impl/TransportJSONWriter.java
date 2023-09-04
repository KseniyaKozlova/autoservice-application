package by.autodiagnostic.writer.impl;

import by.autodiagnostic.transport.Transport;
import by.autodiagnostic.writer.FileCreator;
import by.autodiagnostic.writer.TransportWriter;
import by.autodiagnostic.writer.TransportWriterException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class TransportJSONWriter implements TransportWriter {

    private static final Predicate<String> RIGHT_MODEL_PATTERN =
            Pattern.compile("^[a-zA-Z][a-zA-Z0-9-\\s]+[0-9]|[a-zA-Z]$").asMatchPredicate();

    private final FileCreator fileCreator;
    private final File processedTransportPath;
    private final File invalidTransportPath;
    private final File readPath;
    private final Charset encoding;

    public TransportJSONWriter(FileCreator fileCreator, File processedTransportPath, File invalidTransportPath, File readPath, Charset encoding) {
        this.fileCreator = fileCreator;
        this.processedTransportPath = processedTransportPath;
        this.invalidTransportPath = invalidTransportPath;
        this.readPath = readPath;
        this.encoding = encoding;
    }

    @Override
    public void writeFile(final List<Transport> transportList, final Comparator<Transport> transportComparator)
            throws TransportWriterException {
        try (final FileWriter processedTransportWriter = fileCreator.createFile(processedTransportPath, encoding)) {
            final List<Transport> rightTransportList = haveRightTransport(transportList);
            rightTransportList.sort(transportComparator);

            final JSONArray automobileArray = new JSONArray();
            for (final Transport transport : rightTransportList) {
                JSONObject rightJsonObject = createJSON(transport, true);
                automobileArray.put(rightJsonObject);
            }
            processedTransportWriter.write(automobileArray.toString(1));
        } catch (final IOException e) {
            throw new TransportWriterException("Problem with file", e);
        }
    }

    private List<Transport> haveRightTransport(final List<Transport> transportList) throws IOException {
        final List<Transport> transports = new ArrayList<>();

        try (final FileWriter rightTransportWriter = new FileWriter(readPath, encoding, true);
             final FileWriter invalidTransportWriter = new FileWriter(invalidTransportPath, encoding, true)) {
            for (final Transport element : transportList) {

                JSONObject jsonObject = createJSON(element, false);

                if (RIGHT_MODEL_PATTERN.test(element.getModel())) {
                    rightTransportWriter.append(System.lineSeparator())
                            .append(jsonObject.toString(1));
                    transports.add(element);
                } else {
                    invalidTransportWriter.append(System.lineSeparator())
                            .append(jsonObject.toString(1));
                }
            }
            return transports;
        }
    }

    private JSONObject createJSON(final Transport transport, final boolean isCostFieldNeed) {
        final Map<String, String> mapForJSON = new LinkedHashMap<>();
        mapForJSON.put("type", transport.getType());
        mapForJSON.put("model", transport.getModel());

        if (isCostFieldNeed) {
            mapForJSON.put("cost", transport.getPrice().toString());
        }
        return new JSONObject(mapForJSON);
    }
}
