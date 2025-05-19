package domain.types;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChocolateTypeTest {

    @Test
    void testToString() {
        assertEquals("Чорний", ChocolateType.DARK.toString());
        assertEquals("Молочний", ChocolateType.MILK.toString());
        assertEquals("Білий", ChocolateType.WHITE.toString());
    }

    @Test
    void testEnumValuesCount() {
        ChocolateType[] values = ChocolateType.values();
        assertEquals(3, values.length);
    }

    @Test
    void testEnumByName() {
        assertEquals(ChocolateType.DARK, ChocolateType.valueOf("DARK"));
        assertEquals(ChocolateType.MILK, ChocolateType.valueOf("MILK"));
        assertEquals(ChocolateType.WHITE, ChocolateType.valueOf("WHITE"));
    }
}