package by.autodiagnostic.reader;

import by.autodiagnostic.transport.Transport;

import java.util.List;

public interface TransportReader {

    List<Transport> readTransport() throws TransportReaderException;
}
