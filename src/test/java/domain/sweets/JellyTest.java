package domain.sweets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JellyTest {

    @Test
    void testConstructorAndGetters() {
        byte[] img = {1, 2, 3};
        Jelly j = new Jelly(1, "Желейка", 30.0, 15.5, 7.0, "Полуниця", "Коло", img);

        assertEquals(1, j.getCode());
        assertEquals("Желейка", j.getName());
        assertEquals(30.0, j.getWeight());
        assertEquals(15.5, j.getSugarContent());
        assertEquals(7.0, j.getPrice());
        assertEquals("jelly", j.getSweetType());
        assertEquals("Полуниця", j.getFruityTaste());
        assertEquals("Коло", j.getShape());
        assertArrayEquals(img, j.getImageData());
    }

    @Test
    void testSetters() {
        Jelly j = new Jelly(2, "Желе", 25, 10, 5, "Лимон", "Куб", null);

        j.setFruityTaste("Апельсин");
        j.setShape("Куля");

        assertEquals("Апельсин", j.getFruityTaste());
        assertEquals("Куля", j.getShape());
    }
}