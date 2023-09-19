package by.autodiagnostic.parser.impl;

import by.autodiagnostic.annotation.JSONField;
import by.autodiagnostic.parser.Parser;
import by.autodiagnostic.parser.ParserException;
import by.autodiagnostic.transport.Transport;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JSONParser implements Parser {

    private final Map<String, String> mapForJSON = new LinkedHashMap<>();

    @Override
    public List<Transport> parse(final String initialValue) throws ParserException {
        final List<Transport> transportList = new ArrayList<>();
        final JSONArray jsonArray = new JSONArray(initialValue);

        try {
            for (final Object object : jsonArray) {
                final JSONObject transportJson = (JSONObject) object;

                final Transport resultInstance = parseFromJSON(transportJson, Transport.class);
                transportList.add(resultInstance);
            }
            return transportList;
        } catch (final JSONException e) {
            throw new ParserException("Can't parse file", e);
        } catch (final IllegalAccessException e) {
            throw new ParserException("Can't process JSONProperty annotation", e);
        } catch (final InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            throw new ParserException("Problem with reflection methods", e);
        }
    }

    @Override
    public <T> JSONObject createJSON(final T t, final boolean isExtraFieldNeed) throws IllegalAccessException {

        for (final Field field : t.getClass().getDeclaredFields()) {
            for (final Annotation annotation : field.getDeclaredAnnotations()) {

                if (!(annotation instanceof final JSONField jsonField)) {
                    continue;
                }

                field.setAccessible(true);

                if (field.getType().isEnum()) {
                    processEnum(field, t, isExtraFieldNeed);
                    break;
                }

                final Object object = field.get(t);
                mapForJSON.put(jsonField.name(), object.toString());
            }
        }
        return new JSONObject(mapForJSON);
    }

//    private <T> Object processField(final Field field, final T t) throws IllegalAccessException {
//        Object object = null;
//
////        for (final Field field : t.getClass().getDeclaredFields()) {
//        for (final Annotation annotation : field.getDeclaredAnnotations()) {
//
//        if (!(annotation instanceof final JSONField jsonField)) {
//        continue;
//        }
//
//        field.setAccessible(true);
//
//        object = field.get(t);
//    }
//        return object;
//    }

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

    private <T> T parseFromJSON(final JSONObject jsonObject, final Class<T> clazz)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final T resultInstance = clazz.getDeclaredConstructor().newInstance();

        for (final Field field : clazz.getDeclaredFields()) {
            for (final Annotation annotation : field.getDeclaredAnnotations()) {
                if (!(annotation instanceof final JSONField jsonField)) {
                    continue;
                }

                Object value = jsonObject.get(jsonField.name());

                field.setAccessible(true);
                if (value != null) {
                    if (field.getType().isEnum()) {
                        final Class<?> clazzEnum = field.getType();

                        if (createResultObject("getCategoryByTitle", clazzEnum, value.toString()) != null) {
                            value = createResultObject("getCategoryByTitle", clazzEnum, value.toString());
                        } else {
                            value = createResultObject("valueOf", clazzEnum, value.toString().toUpperCase());
                        }
                    }
                    field.set(resultInstance, value);
                }
            }
        }
        return resultInstance;
    }

    private <T> Object createResultObject(final String methodName, final Class<T> clazz, final Object value)
            throws InvocationTargetException, IllegalAccessException {
        Object resultObject = null;

        for (final Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                resultObject = method.invoke(null, value);
                break;
            }
        }
        return resultObject;
    }
}
