package gift.types;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChocolateTypeTest {

    @Test
    void testGetChocolateName() {
        assertEquals("Молочний", ChocolateType.MILK.getChocolateName(),"Назва для MILK повинна бути 'Молочний'.");
    }

    @Test
    void testToString() {
        assertEquals("Чорний", ChocolateType.DARK.toString(),"Метод toString() повинен повертати 'Чорний' для DARK.");
        assertEquals("Молочний", ChocolateType.MILK.toString(),"Метод toString() повинен повертати 'Молочний' для MILK.");
        assertEquals("Білий", ChocolateType.WHITE.toString(),"Метод toString() повинен повертати 'Білий' для WHITE.");
    }
}