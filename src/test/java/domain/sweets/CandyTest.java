package domain.sweets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CandyTest {

    @Test
    void testConstructorAndGetters() {
        byte[] img = new byte[]{1, 2, 3};
        Candy candy = new Candy(1, "ChocoBar", 50.0, 30.0, 10.5, "Caramel", "Soft", img);

        assertEquals(1, candy.getCode());
        assertEquals("ChocoBar", candy.getName());
        assertEquals(50.0, candy.getWeight());
        assertEquals(30.0, candy.getSugarContent());
        assertEquals(10.5, candy.getPrice());
        assertEquals("candy", candy.getSweetType());
        assertEquals("Caramel", candy.getFilling());
        assertEquals("Soft", candy.getType());
        assertArrayEquals(img, candy.getImageData());
    }

    @Test
    void testSetters() {
        Candy candy = new Candy(2, "BonBon", 25.0, 15.0, 5.0, "Vanilla", "Hard", null);

        candy.setFilling("Strawberry");
        candy.setType("Chewy");

        assertEquals("Strawberry", candy.getFilling());
        assertEquals("Chewy", candy.getType());
    }
}