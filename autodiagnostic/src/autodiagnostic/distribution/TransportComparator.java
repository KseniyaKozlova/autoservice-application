package autodiagnostic.distribution;

import autodiagnostic.transport.Transport;

import java.util.Comparator;
import java.util.List;

public class TransportComparator implements Comparator<Transport> {

    private final List<SortChoice> sortingRequirements;

    public TransportComparator(final List<SortChoice> sortingRequirements) {
        this.sortingRequirements = sortingRequirements;
    }

    @Override
    public int compare(Transport transport1, Transport transport2) {
        int value = 0;
        for (final SortChoice requirement : sortingRequirements) {
            String sort = requirement.getSortType().name();
            String direction = requirement.getDirection().name();
            final boolean isAscending = direction.equalsIgnoreCase(Direction.ASC.name());
            final boolean isDescending = direction.equalsIgnoreCase(Direction.DSC.name());
            int currentValue = 0;

            for (SortType sortType : SortType.values()) {
                if (sort.equals(sortType.name()) && isAscending) {
                    currentValue = sortType.compareTransport.apply(transport1, transport2);
                } else if (sort.equals(sortType.name()) && isDescending) {
                    currentValue = sortType.compareTransport.apply(transport2, transport1);
                }
            }

            if (currentValue != 0) {
                value = currentValue;
                break;
            }
        }
        return value;
    }
}