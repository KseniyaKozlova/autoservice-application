package by.autodiagnostic.web.writer.impl;

import by.autodiagnostic.transport.Transport;
import by.autodiagnostic.validation.FieldValidator;
import by.autodiagnostic.validation.FieldValidatorException;
import by.autodiagnostic.validation.impl.TransportFieldValidator;
import by.autodiagnostic.web.writer.ServletWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ServletTransportWriter implements ServletWriter {

    public void writeTables(final List<Transport> transportList, final HttpServletResponse response) throws IOException {
        final List<Transport> processedTransportList = new ArrayList<>();
        final List<Transport> invalidTransportList = new ArrayList<>();
        final FieldValidator fieldValidator = new TransportFieldValidator();

        try (final PrintWriter writer = response.getWriter()) {
            for (final Transport transport : transportList) {

                if (fieldValidator.workField(transport)) {
                    processedTransportList.add(transport);
                } else {
                    invalidTransportList.add(transport);
                }
            }
            writer.println("Processed Transport");
            createTable(processedTransportList, writer, true);

            writer.println("Invalid Transport");
            createTable(invalidTransportList, writer, false);
        } catch (final FieldValidatorException e) {
            throw new IOException("Can't check validator field");
        }
    }

    private void createTable(
            final List<Transport> transportList,
            final PrintWriter writer,
            final boolean isProcessed
    ) throws FieldValidatorException {

        writer.println("<table border=1>");
        writer.println("<tr>");
        writer.println("<th> тип </th>");
        writer.println("<th> модель </th>");
        writer.println("<th> цена </th>");
        writer.println("</tr>");

        for (final Transport transport : transportList) {
            writer.println("<tr>");
            writer.println("<td>" + transport.getType() + "</td>");
            writer.println("<td>" + transport.getModel() + "</td>");

            if (isProcessed) {
                writer.println("<td>" + transport.getPrice() + "</td>");
            }

            writer.println("</tr>");
        }
        writer.println("</table>");
    }
}
