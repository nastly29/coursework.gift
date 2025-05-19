package domain.sweets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SweetsTest {

    @Test
    void testConstructorAndGetters() {
        byte[] image = new byte[]{1, 2, 3};
        Sweets sweet = new Sweets(1, "Candy", 50.5, 20.0, 15.0, "candy", image);

        assertEquals(1, sweet.getCode());
        assertEquals("Candy", sweet.getName());
        assertEquals(50.5, sweet.getWeight());
        assertEquals(20.0, sweet.getSugarContent());
        assertEquals(15.0, sweet.getPrice());
        assertEquals("candy", sweet.getSweetType());
        assertArrayEquals(image, sweet.getImageData());
    }

    @Test
    void testSetters() {
        Sweets sweet = new Sweets(0, "", 0, 0, 0, "", null);

        sweet.setCode(42);
        sweet.setName("Chocolate");
        sweet.setWeight(75.0);
        sweet.setSugarContent(30.0);
        sweet.setPrice(12.5);
        sweet.setType("chocolate");
        sweet.setImageData(new byte[]{9, 9});

        assertEquals(42, sweet.getCode());
        assertEquals("Chocolate", sweet.getName());
        assertEquals(75.0, sweet.getWeight());
        assertEquals(30.0, sweet.getSugarContent());
        assertEquals(12.5, sweet.getPrice());
        assertEquals("chocolate", sweet.getSweetType());
        assertArrayEquals(new byte[]{9, 9}, sweet.getImageData());
    }
}