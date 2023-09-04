//package by.autodiagnostic.distribution;
//
//import by.autodiagnostic.transport.Category;
//import by.autodiagnostic.transport.Transport;
//import org.junit.jupiter.api.Test;
//
//import java.util.Comparator;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SortTypeTest {
//
//    private static final Transport TRANSPORT_1 = new Transport(Category.AUTOMOBILE, "Audi");
//    private static final Transport TRANSPORT_2 = new Transport(Category.MOTORBIKE, "BMW");
//
//    @Test
//    void testGetComparingResult_enumModel_successful() {
//        final var expected = SortType.MODEL.getComparator();
//
//        assertNotNull(expected, "Comparing result is null");
//        assertEquals(expected, -1);
//    }
//
//    @Test
//    void testGetComparingResult_enumType_successful() {
//        final var expected = SortType.TYPE.getComparator();
//
//        assertNotNull(expected, "Comparing result is null");
//        assertEquals(expected, Comparator.comparing(Transport::getType));
//    }
//
//    @Test
//    void testGetComparingResult_enumCost_successful() {
//        final var expected = SortType.COST.getComparator();
//
//        assertNotNull(expected, "Comparing result is null");
//        assertEquals(expected, 1);
//    }
//}