package domain.types;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CandyTypeTest {

    @Test
    void testToString() {
        assertEquals("Шоколадна", CandyType.CHOCOLATE.toString());
        assertEquals("Іриска", CandyType.TOFFEE.toString());
        assertEquals("Льодяник", CandyType.LOLLIPOP.toString());
        assertEquals("Карамель", CandyType.CARAMEL.toString());
    }

    @Test
    void testEnumValuesCount() {
        CandyType[] values = CandyType.values();
        assertEquals(4, values.length);
    }

    @Test
    void testEnumByName() {
        assertEquals(CandyType.CARAMEL, CandyType.valueOf("CARAMEL"));
        assertEquals(CandyType.LOLLIPOP, CandyType.valueOf("LOLLIPOP"));
    }
}