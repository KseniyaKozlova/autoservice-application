package by.itacademy.jdbc;

import by.itacademy.model.Transport;

public interface JdbcCreator {

    void createTransport(Transport transport) throws JdbcCreatorException;

    void getTransport() throws JdbcCreatorException;
}
