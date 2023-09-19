package by.autodiagnostic.web.writer;

import by.autodiagnostic.transport.Transport;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ServletWriter {

    void writeTables(List<Transport> transportList, HttpServletResponse response) throws IOException;
}
