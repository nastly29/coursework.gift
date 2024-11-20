package gift.types;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CandyTypeTest {

    @Test
    void testGetCandyName() {
        assertEquals("Шоколадна", CandyType.CHOCOLATE.getCandyName(), "Назва для CHOCOLATE повинна бути 'Шоколадна'.");
    }

    @Test
    void testToString() {
        assertEquals("Шоколадна", CandyType.CHOCOLATE.toString(), "Метод toString() повинен повертати 'Шоколадна' для CHOCOLATE.");
        assertEquals("Іриска", CandyType.TOFFEE.toString(), "Метод toString() повинен повертати 'Іриска' для TOFFEE.");
        assertEquals("Льодяник", CandyType.LOLLIPOP.toString(), "Метод toString() повинен повертати 'Льодяник' для LOLLIPOP.");
        assertEquals("Карамель", CandyType.CARAMEL.toString(), "Метод toString() повинен повертати 'Карамель' для CARAMEL.");

    }
}