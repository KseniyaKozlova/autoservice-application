package by.autodiagnostic.distribution;

import by.autodiagnostic.transport.Category;
import by.autodiagnostic.transport.Transport;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransportComparatorTest {

    @Test
    void compare() {
        final List<SortChoice> sortingRequirements = new ArrayList<>();
        sortingRequirements.add(new SortChoice(SortType.MODEL, Direction.ASC));

        final Transport transport1 = new Transport(Category.AUTOMOBILE, "Audi");
        final Transport transport2 = new Transport(Category.MOTORBIKE, "BMW");

        final var comparator = new TransportComparator(sortingRequirements);
        final var expected = comparator.compare(transport1, transport2);

        assertEquals(expected, -1);
    }
}