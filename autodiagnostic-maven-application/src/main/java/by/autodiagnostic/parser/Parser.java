package by.autodiagnostic.parser;

import by.autodiagnostic.transport.Transport;
import org.json.JSONObject;

import java.util.List;

public interface Parser {

    List<Transport> parse(String initialValue) throws ParserException;

    <T> JSONObject createJSON(T t, boolean isExtraFieldNeed) throws IllegalAccessException;
}
