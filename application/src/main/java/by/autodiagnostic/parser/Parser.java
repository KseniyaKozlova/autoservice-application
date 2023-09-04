package by.autodiagnostic.parser;

import by.autodiagnostic.transport.Transport;

import java.util.List;

public interface Parser {

    List<Transport> parse(String initialValue) throws ParserException;
}
