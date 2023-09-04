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
            int currentValue = 0;
            final SortType sort = requirement.getSortType();

            final String direction = requirement.getDirection().name();
            final boolean isAscending = direction.equalsIgnoreCase(Direction.ASC.name());
            final boolean isDescending = direction.equalsIgnoreCase(Direction.DSC.name());

            if (isAscending) {
                currentValue = sort.getComparator().compare(transport1, transport2);
            } else if (isDescending) {
                currentValue = sort.getComparator().compare(transport2, transport1);
            }

            if (currentValue != 0) {
                value = currentValue;
                break;
            }
        }
        return value;
    }
}