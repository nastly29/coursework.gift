package gift.types;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ColorTypeTest {

    @Test
    void testGetColorName() {
        assertEquals("Фіолетовий", ColorType.PURPLE.getColorName(), "Назва для PURPLE повинна бути 'Фіолетовий'.");
    }

    @Test
    void testToString() {
        assertEquals("Червоний", ColorType.RED.toString(), "Метод toString() повинен повертати 'Червоний' для RED.");
        assertEquals("Зелений", ColorType.GREEN.toString(), "Метод toString() повинен повертати 'Зелений' для GREEN.");
        assertEquals("Синій", ColorType.BLUE.toString(), "Метод toString() повинен повертати 'Синій' для BLUE.");
        assertEquals("Жовтий", ColorType.YELLOW.toString(), "Метод toString() повинен повертати 'Жовтий' для YELLOW.");
        assertEquals("Білий", ColorType.WHITE.toString(), "Метод toString() повинен повертати 'Білий' для WHITE.");
        assertEquals("Чорний", ColorType.BLACK.toString(), "Метод toString() повинен повертати 'Чорний' для BLACK.");
        assertEquals("Помаранчевий", ColorType.ORANGE.toString(), "Метод toString() повинен повертати 'Помаранчевий' для ORANGE.");
        assertEquals("Фіолетовий", ColorType.PURPLE.toString(), "Метод toString() повинен повертати 'Фіолетовий' для PURPLE.");
        assertEquals("Рожевий", ColorType.PINK.toString(), "Метод toString() повинен повертати 'Рожевий' для PINK.");
    }
}