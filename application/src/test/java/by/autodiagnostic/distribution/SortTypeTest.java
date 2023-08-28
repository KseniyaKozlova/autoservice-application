package by.autodiagnostic.distribution;

import by.autodiagnostic.transport.Category;
import by.autodiagnostic.transport.Transport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortTypeTest {
    final Transport transport1 = new Transport(Category.AUTOMOBILE, "Audi");
    final Transport transport2 = new Transport(Category.MOTORBIKE, "BMW");

    @Test
    void testGetComparingResult_enumModel_successfull() {
        final Integer expected = SortType.MODEL.getComparingResult(transport1, transport2);

        assertNotNull(expected, "Comparing result is null");
        assertEquals(expected, -1);
    }

    @Test
    void testGetComparingResult_enumType_successfull() {
        final var expected = SortType.TYPE.getComparingResult(transport1, transport2);

        assertNotNull(expected, "Comparing result is null");
        assertEquals(expected, -12);
    }

    @Test
    void testGetComparingResult_enumCost_successfull() {
        final var expected = SortType.COST.getComparingResult(transport1, transport2);

        assertNotNull(expected, "Comparing result is null");
        assertEquals(expected, 1);
    }
}