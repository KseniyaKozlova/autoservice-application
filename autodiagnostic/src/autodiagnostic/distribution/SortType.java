package autodiagnostic.distribution;

import autodiagnostic.transport.Transport;

import java.util.function.BiFunction;

public enum SortType {

    TYPE((transport1, transport2) -> transport1.getType().compareTo(transport2.getType())),
    MODEL(((transport1, transport2) -> transport1.getModel().compareTo(transport2.getModel()))),
    COST(((transport1, transport2) -> transport1.getPrice().compareTo(transport2.getPrice())));

    final BiFunction<Transport, Transport, Integer> compareTransport;

    SortType(final BiFunction<Transport, Transport, Integer> compareTransport) {
        this.compareTransport = compareTransport;
    }
}
