package by.autodiagnostic.writer;

import by.autodiagnostic.transport.Transport;

import java.util.Comparator;
import java.util.List;

public interface TransportWriter {

    void writeFile(List<Transport> transportList, Comparator<Transport> transportComparator) throws TransportWriterException;
}
