package by.autodiagnostic.web.service.impl;

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
import by.autodiagnostic.web.decoder.Decoder;
import by.autodiagnostic.web.decoder.impl.InputStreamDecoder;
import by.autodiagnostic.web.service.TransportService;
import by.autodiagnostic.web.writer.ServletWriter;
import by.autodiagnostic.web.writer.impl.ServletTransportWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.autodiagnostic.util.StandartConstants.CHARSET;

public class ServletTransportService implements TransportService {

    public void processTransport(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final Parser parser = new JSONParser();
        final Decoder decoder = new InputStreamDecoder(request); //for not postman request
        final TransportReader transportReader = new TransportJSONReader(request.getInputStream(), parser);
        List<Transport> transportList;

        response.setContentType("text/html");
        response.setCharacterEncoding(CHARSET.name());

        try {
            transportList = transportReader.readTransport();
            final String sorting = request.getParameter("sorting");

            if (sorting != null) {
                final ChoiceReader choiceReader = new InputChoiceReader();
                final List<SortChoice> sortChoiceList = choiceReader.readChoice(sorting);

                transportList.sort(new TransportComparator(sortChoiceList));
            }

        } catch (final TransportReaderException | ChoiceReaderException e) {
            throw new IOException("Wrong entered meaning", e);
        }

        final ServletWriter servletWriter = new ServletTransportWriter();
        servletWriter.writeTables(transportList, response);
    }
}
