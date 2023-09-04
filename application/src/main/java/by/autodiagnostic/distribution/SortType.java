package by.autodiagnostic.distribution;

import by.autodiagnostic.transport.Transport;

import java.util.Comparator;

public enum SortType {

    TYPE(Comparator.comparing(Transport::getType)),
    MODEL(Comparator.comparing(Transport::getModel)),
    COST(Comparator.comparingInt(Transport::getPrice));

    private final Comparator<Transport> comparator;

    SortType(final Comparator<Transport> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Transport> getComparator() {
        return comparator;
    }
}
