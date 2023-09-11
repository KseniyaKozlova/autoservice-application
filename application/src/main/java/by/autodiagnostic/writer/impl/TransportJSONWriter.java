package by.autodiagnostic.writer.impl;

import by.autodiagnostic.annotation.JSONField;
import by.autodiagnostic.annotation.JSONProperty;
import by.autodiagnostic.transport.Transport;
import by.autodiagnostic.validation.FieldValidator;
import by.autodiagnostic.validation.FieldValidatorException;
import by.autodiagnostic.writer.TransportWriter;
import by.autodiagnostic.writer.TransportWriterException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;

public class TransportJSONWriter implements TransportWriter {

    private final FieldValidator fieldValidator;
    private final File processedTransportPath;
    private final File invalidTransportPath;
    private final Charset encoding;

    private final Map<String, String> mapForJSON = new LinkedHashMap<>();

    public TransportJSONWriter(final FieldValidator fieldValidator, final File processedTransportPath,
                               final File invalidTransportPath, final Charset encoding) {
        this.fieldValidator = fieldValidator;
        this.processedTransportPath = processedTransportPath;
        this.invalidTransportPath = invalidTransportPath;
        this.encoding = encoding;
    }

    @Override
    public void writeFile(final List<Transport> transportList, final Comparator<Transport> transportComparator)
            throws TransportWriterException {
        try (final FileWriter processedTransportWriter = new FileWriter(processedTransportPath, encoding)) {
            final List<Transport> rightTransportList = getRightTransport(transportList);
            rightTransportList.sort(transportComparator);

            final JSONArray automobileArray = new JSONArray();
            for (final Transport transport : rightTransportList) {
                final JSONObject rightJsonObject = createJSON(transport, true);
                automobileArray.put(rightJsonObject);
            }
            processedTransportWriter.write(automobileArray.toString(1));
        } catch (final IOException e) {
            throw new TransportWriterException("Problem with file", e);
        } catch (final IllegalAccessException | FieldValidatorException e) {
            throw new TransportWriterException("Problem with annotation", e);
        }
    }

    private List<Transport> getRightTransport(final List<Transport> transportList)
            throws FieldValidatorException, IOException, IllegalAccessException {
        final List<Transport> transports = new ArrayList<>();

        for (final Transport element : transportList) {
            final JSONObject jsonObject = createJSON(element, false);

            if (fieldValidator.workField(element)) {
                transports.add(element);
            } else {
                writeInvalidTransport(jsonObject);
            }
        }
        return transports;
    }

    private void writeInvalidTransport(final JSONObject jsonObject) throws IOException {
        try (final FileWriter invalidTransportWriter = new FileWriter(invalidTransportPath, encoding, true)) {
            invalidTransportWriter.append(System.lineSeparator())
                    .append(jsonObject.toString(1));
        }
    }

    private <T> JSONObject createJSON(final T t, final boolean isExtraFieldNeed) throws IllegalAccessException {

        for (final Field field : t.getClass().getDeclaredFields()) {
            for (final Annotation annotation : field.getDeclaredAnnotations()) {

                if (!(annotation instanceof final JSONProperty jsonProperty)) {
                    continue;
                }

                field.setAccessible(true);

                if (field.getType().isEnum()) {
                    processEnum(field, t, isExtraFieldNeed);
                    break;
                }

                final Object object = field.get(t);
                mapForJSON.put(jsonProperty.fieldName(), object.toString());
            }
        }
        return new JSONObject(mapForJSON);
    }

    private <T> void processEnum(final Field field, final T t, final boolean isExtraFieldNeed)
            throws IllegalAccessException {
        final Class<?> clazzEnum = field.getType();

        for (final Field enumField : clazzEnum.getDeclaredFields()) {
            for (final Annotation enumAnnotation : enumField.getDeclaredAnnotations()) {

                if (!(enumAnnotation instanceof final JSONField JSONField)) {
                    continue;
                }

                final Object meaningFromEnum = getEnumMeaning(clazzEnum, enumField, field, t);

                if (!JSONField.isExtraField()) {
                    mapForJSON.put(JSONField.name(), meaningFromEnum.toString());
                } else {
                    if (isExtraFieldNeed) {
                        mapForJSON.put(JSONField.name(), meaningFromEnum.toString());
                    }
                }
            }
        }
    }

    private <T> Object getEnumMeaning(final Class<?> clazz, final Field enumField, final Field objectField, final T t)
            throws IllegalAccessException {
        Object resultObject = null;

        for (final Object object : clazz.getEnumConstants()) {
            if (object.equals(objectField.get(t))) {
                enumField.setAccessible(true);
                resultObject = enumField.get(object);
            }
        }
        return resultObject;
    }
}
