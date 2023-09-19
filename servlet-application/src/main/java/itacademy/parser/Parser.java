package itacademy.parser;

import itacademy.model.Transport;

import java.util.List;

public interface Parser {

    List<Transport> parse(String initialValue);
}
