package domain.sweets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GingerbreadTest {

    @Test
    void testConstructorAndGetters() {
        byte[] img = new byte[]{1, 2, 3};
        Gingerbread g = new Gingerbread(1, "Медівник", 120, 25.5, 18.0, "Зірка", true, img);

        assertEquals(1, g.getCode());
        assertEquals("Медівник", g.getName());
        assertEquals(120, g.getWeight());
        assertEquals(25.5, g.getSugarContent());
        assertEquals(18.0, g.getPrice());
        assertEquals("gingerbread", g.getSweetType());
        assertEquals("Зірка", g.getShape());
        assertTrue(g.isIced());
        assertArrayEquals(img, g.getImageData());
    }

    @Test
    void testSetters() {
        Gingerbread g = new Gingerbread(2, "Пряник", 100, 20, 15, "Коло", false, null);

        g.setShape("Серце");
        g.setIced(true);

        assertEquals("Серце", g.getShape());
        assertTrue(g.isIced());
    }
}