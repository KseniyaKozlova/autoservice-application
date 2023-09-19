package by.autodiagnostic.web;

import by.autodiagnostic.web.service.TransportService;
import by.autodiagnostic.web.service.impl.ServletTransportService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransportServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        final TransportService transportService = new ServletTransportService();
        transportService.processTransport(request, response);
    }
}
