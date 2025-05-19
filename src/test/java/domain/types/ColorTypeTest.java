package domain.types;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColorTypeTest {

    @Test
    void testToString() {
        assertEquals("Червоний", ColorType.RED.toString());
        assertEquals("Зелений", ColorType.GREEN.toString());
        assertEquals("Синій", ColorType.BLUE.toString());
        assertEquals("Жовтий", ColorType.YELLOW.toString());
        assertEquals("Білий", ColorType.WHITE.toString());
        assertEquals("Чорний", ColorType.BLACK.toString());
        assertEquals("Помаранчевий", ColorType.ORANGE.toString());
        assertEquals("Фіолетовий", ColorType.PURPLE.toString());
        assertEquals("Рожевий", ColorType.PINK.toString());
    }

    @Test
    void testGetColorName() {
        assertEquals("Червоний", ColorType.RED.getColorName());
        assertEquals("Фіолетовий", ColorType.PURPLE.getColorName());
    }

    @Test
    void testEnumValuesCount() {
        assertEquals(9, ColorType.values().length);
    }

    @Test
    void testValueOfByName() {
        assertEquals(ColorType.YELLOW, ColorType.valueOf("YELLOW"));
        assertEquals(ColorType.BLACK, ColorType.valueOf("BLACK"));
    }
}