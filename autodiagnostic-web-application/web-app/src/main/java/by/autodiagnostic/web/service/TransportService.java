package by.autodiagnostic.web.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface TransportService {

    void processTransport(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
