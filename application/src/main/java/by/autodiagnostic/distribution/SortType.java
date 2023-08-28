package by.autodiagnostic.distribution;

import by.autodiagnostic.transport.Transport;

import java.util.function.BiFunction;

public enum SortType {

    TYPE((transport1, transport2) -> transport1.getType().compareTo(transport2.getType())),
    MODEL(((transport1, transport2) -> transport1.getModel().compareTo(transport2.getModel()))),
    COST(((transport1, transport2) -> transport1.getPrice().compareTo(transport2.getPrice())));

    private final BiFunction<Transport, Transport, Integer> compareTransport;

    SortType(final BiFunction<Transport, Transport, Integer> compareTransport) {
        this.compareTransport = compareTransport;
    }

    public Integer getComparingResult(final Transport transport1, final Transport transport2) {
        return compareTransport.apply(transport1, transport2);
    }
}
