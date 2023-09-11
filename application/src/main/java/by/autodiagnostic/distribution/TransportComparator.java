package by.autodiagnostic.distribution;

import by.autodiagnostic.transport.Transport;

import java.util.Comparator;
import java.util.List;

public class TransportComparator implements Comparator<Transport> {

    private final List<SortChoice> sortingRequirements;

    public TransportComparator(final List<SortChoice> sortingRequirements) {
        this.sortingRequirements = sortingRequirements;
    }

    @Override
    public int compare(final Transport transport1, final Transport transport2) {
        int value = 0;

        for (final SortChoice requirement : sortingRequirements) {
            final SortType sort = requirement.getSortType();
            final String direction = requirement.getDirection().name();

            Comparator<Transport> transportComparator = sort.getComparator();

            if (direction.equalsIgnoreCase(Direction.DSC.name())) {
                transportComparator = transportComparator.reversed();
            }
            value = transportComparator.compare(transport1, transport2);

            if (value != 0) {
                break;
            }
        }
        return value;
    }
}