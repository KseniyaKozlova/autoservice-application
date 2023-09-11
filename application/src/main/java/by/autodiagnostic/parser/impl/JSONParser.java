package by.autodiagnostic.parser.impl;

import by.autodiagnostic.annotation.JSONProperty;
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
import java.util.List;

public class JSONParser implements Parser {

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

    private <T> T parseFromJSON(final JSONObject jsonObject, final Class<T> clazz)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        final T resultInstance = clazz.getDeclaredConstructor().newInstance();

        for (final Field field : clazz.getDeclaredFields()) {
            for (final Annotation annotation : field.getDeclaredAnnotations()) {
                if (!(annotation instanceof final JSONProperty jsonProperty)) {
                    continue;
                }

                Object value = jsonObject.get(jsonProperty.fieldName());

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
