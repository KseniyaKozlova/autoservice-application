package by.autodiagnostic.parser.impl;

import by.autodiagnostic.parser.Parser;
import by.autodiagnostic.parser.ParserException;
import by.autodiagnostic.transport.Category;
import by.autodiagnostic.transport.CategoryException;
import by.autodiagnostic.transport.Transport;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser implements Parser {

    @Override
    public List<Transport> parse(final String initialValue) throws ParserException {
        final List<Transport> transports = new ArrayList<>();
        final JSONArray transportArray = new JSONArray(initialValue);

        try {
            for (final Object element : transportArray) {
                final JSONObject transportJson = (JSONObject) element;

                final String type = transportJson.getString("type");
                final Category category = Category.getCategoryByTitle(type);
                final String model = transportJson.getString("model");

                transports.add(new Transport(category, model));
            }
            return transports;
        } catch (final CategoryException | JSONException e) {
            throw new ParserException("Can't parse file", e);
        }
    }
}
