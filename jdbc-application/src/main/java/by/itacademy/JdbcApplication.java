package by.itacademy;

import by.itacademy.jdbc.JdbcCreator;
import by.itacademy.jdbc.JdbcPostgresCreator;
import by.itacademy.model.Client;
import by.itacademy.model.Model;
import by.itacademy.model.Transport;
import by.itacademy.model.Type;

public class JdbcApplication {

    public static void main(final String[] args) {
        try {
            final Client client = new Client("Viktor", "Ivanov");
            final Transport transport = new Transport(Model.AUDI, Type.AUTOMOBILE, client);

            final JdbcCreator jdbcCreator = new JdbcPostgresCreator();
            jdbcCreator.createTransport(transport);
            jdbcCreator.getTransport();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
