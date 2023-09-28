package by.autodiagnostic.web;

import by.autodiagnostic.choice.ChoiceReader;
import by.autodiagnostic.choice.ChoiceReaderException;
import by.autodiagnostic.choice.impl.InputChoiceReader;
import by.autodiagnostic.distribution.SortChoice;
import by.autodiagnostic.distribution.TransportComparator;
import by.autodiagnostic.parser.Parser;
import by.autodiagnostic.parser.impl.JSONParser;
import by.autodiagnostic.reader.TransportReader;
import by.autodiagnostic.reader.TransportReaderException;
import by.autodiagnostic.reader.impl.TransportJSONReader;
import by.autodiagnostic.transport.Transport;
import by.autodiagnostic.validation.FieldValidator;
import by.autodiagnostic.validation.FieldValidatorException;
import by.autodiagnostic.validation.impl.TransportFieldValidator;
import by.autodiagnostic.web.decoder.Decoder;
import by.autodiagnostic.web.decoder.impl.InputStreamDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.autodiagnostic.util.StandardConstants.CHARSET;

public class TransportServletJSP extends HttpServlet {

    private static final Parser PARSER = new JSONParser();

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException, ServletException {
        final Decoder decoder = new InputStreamDecoder(request); //for not postman request
        final TransportReader transportReader = new TransportJSONReader(request.getInputStream(), PARSER);

        final FieldValidator fieldValidator = new TransportFieldValidator();
        final List<Transport> processedTransportList = new ArrayList<>();
        final List<Transport> invalidTransportList = new ArrayList<>();

        try {
            final List<Transport> transportList = transportReader.readTransport();
            final String sorting = request.getParameter("sorting");

            if (sorting != null) {
                sortList(transportList, sorting);
            }

            for (final Transport transport : transportList) {
                if (fieldValidator.workField(transport)) {
                    processedTransportList.add(transport);
                } else {
                    invalidTransportList.add(transport);
                }
            }
        } catch (final TransportReaderException | ChoiceReaderException e) {
            throw new IOException("Wrong entered meaning", e);
        } catch (final FieldValidatorException e) {
            throw new IOException("Can't check validator field");
        }

        request.setAttribute("listTransport", processedTransportList);
        request.setAttribute("invalidList", invalidTransportList);

        request.getRequestDispatcher("/table.jsp").forward(request, response);

        response.setContentType("text/html");
        response.setCharacterEncoding(CHARSET.name());
    }

    private static void sortList(final List<Transport> transportList, final String sorting) throws ChoiceReaderException {
        final ChoiceReader choiceReader = new InputChoiceReader();
        final List<SortChoice> sortChoiceList = choiceReader.readChoice(sorting);

        transportList.sort(new TransportComparator(sortChoiceList));
    }
}
