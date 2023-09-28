package by.autodiagnostic.parser;

import by.autodiagnostic.transport.Transport;

import java.util.List;

public interface Parser {

    <T> List<T> parse(String initialValue, Class<T> clazz) throws ParserException;
}
