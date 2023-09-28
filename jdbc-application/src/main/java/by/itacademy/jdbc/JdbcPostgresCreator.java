package by.itacademy.jdbc;

import by.itacademy.JdbcApplication;
import by.itacademy.model.Client;
import by.itacademy.model.Model;
import by.itacademy.model.Transport;
import by.itacademy.model.Type;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcPostgresCreator implements JdbcCreator {

    private static final Properties PROPERTIES = loadProperties();

    public void createTransport(final Transport transport) throws JdbcCreatorException {
        try (final Connection connection = DriverManager.getConnection(PROPERTIES.getProperty("url"), PROPERTIES)) {
            final String insertQuery = "INSERT INTO public.transport (model_type_id, transport_type_id, client_id) VALUES " +
                    "((SELECT id FROM model_type WHERE name = ?), " +
                    "(SELECT id FROM transport_type WHERE name = ?), " +
                    "(SELECT id FROM client WHERE first_name = ? AND last_name = ?))";

            final PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, transport.getModel().name());
            preparedStatement.setString(2, transport.getType().name());
            preparedStatement.setString(3, transport.getClient().getFirstName());
            preparedStatement.setString(4, transport.getClient().getLastName());
            preparedStatement.executeUpdate();

            final ResultSet resultSet = preparedStatement.getGeneratedKeys();
            System.out.println(resultSet.getInt(1));
        } catch (final SQLException e) {
            throw new JdbcCreatorException("Unright entered transport", e);
        }
    }

    public void getTransport() throws JdbcCreatorException {
        try (final Connection connection = DriverManager.getConnection(PROPERTIES.getProperty("url"), PROPERTIES)) {
            final String transportObjectQuery = "SELECT t.id, mt.\"name\", tt.\"name\", c.first_name, c.last_name  FROM public.transport t " +
                    "LEFT JOIN model_type mt ON t.model_type_id = mt.id " +
                    "LEFT JOIN transport_type tt ON t.transport_type_id = tt.id " +
                    "LEFT JOIN client c ON t.client_id = c.id ";

            final Statement statement = connection.createStatement();

            final ResultSet resultSetTransport = statement.executeQuery(transportObjectQuery);
            while (resultSetTransport.next()) {
                final int id = resultSetTransport.getInt(1);
                final String model = resultSetTransport.getString(2);
                final String type = resultSetTransport.getString(3);
                final String firstName = resultSetTransport.getString(4);
                final String lastName = resultSetTransport.getString(5);

                final Client client = new Client(firstName, lastName);
                final Transport transport = new Transport(Model.valueOf(model), Type.valueOf(type), client);
                System.out.println(transport + "with id: " + id);
            }
        } catch (final SQLException e) {
            throw new JdbcCreatorException("Unright SQL", e);
        }
    }

    private static Properties loadProperties() {
        try (final InputStream inputStream = JdbcApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            final Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (final IOException e) {
            throw new RuntimeException();
        }
    }
}
