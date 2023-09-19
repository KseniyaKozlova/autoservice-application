package itacademy.servlet;

import itacademy.model.Category;
import itacademy.model.Transport;
import itacademy.parser.JSONParser;
import itacademy.parser.Parser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TransportServletApplication extends HttpServlet {

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final Transport transport1 = new Transport(Category.MOTORBIKE, "Ninja ZX-14");
        final Transport transport2 = new Transport(Category.AUTOMOBILE, "BMW M5");
        final Transport transport3 = new Transport(Category.MINIBUS, "Sprinter264");
        final List<Transport> transportList = List.of(transport1, transport2, transport3);

        writeResponse(response, transportList);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final Parser parser = new JSONParser();
        List<Transport> transportList;

        try (final BufferedReader bufferedReader = new BufferedReader(request.getReader())) {
            final String body = bufferedReader.lines().reduce("", String::concat);
            transportList = parser.parse(body);
        }
        writeResponse(response, transportList);
    }

    private void writeResponse(final HttpServletResponse response, final List<Transport> transportList) throws IOException {
        final String transports = transportList.stream().map(Objects::toString).collect(Collectors.joining("; "));

        response.setContentType("text/html");

        try (final PrintWriter writer = response.getWriter()) {
            writer.println(transports);
        }
    }
}
