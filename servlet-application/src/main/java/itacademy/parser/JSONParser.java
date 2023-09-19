package itacademy.parser;

import itacademy.model.Category;
import itacademy.model.Transport;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONParser implements Parser{

    @Override
    public List<Transport> parse(final String initialValue) {
        final List<Transport> transports = new ArrayList<>();
        final JSONArray typeArray = new JSONArray(initialValue);

        for (final Object objectType : typeArray) {
            final JSONObject typeJSON = (JSONObject) objectType;
            final String type = typeJSON.getString("type");
            final String model = typeJSON.getString("model");
            transports.add(new Transport(Category.valueOf(type.toUpperCase()), model));
        }
        return transports;
    }
}
