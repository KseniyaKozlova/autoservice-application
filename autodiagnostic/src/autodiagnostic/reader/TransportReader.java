package autodiagnostic.reader;

import autodiagnostic.transport.Transport;

import java.util.List;

public interface TransportReader {

    List<Transport> readTransport() throws TransportReaderException;

}
