package domain.sweets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ChocolateTest {

    @Test
    void testConstructorAndGetters() {
        byte[] img = new byte[]{10, 20, 30};
        Chocolate chocolate = new Chocolate(
                101, "Milka", 100.0, 45.0, 22.5,
                75.0, "Hazelnut", "Dark", img
        );

        assertEquals(101, chocolate.getCode());
        assertEquals("Milka", chocolate.getName());
        assertEquals(100.0, chocolate.getWeight());
        assertEquals(45.0, chocolate.getSugarContent());
        assertEquals(22.5, chocolate.getPrice());
        assertEquals("chocolate", chocolate.getSweetType());
        assertEquals(75.0, chocolate.getCocoaPercentage());
        assertEquals("Hazelnut", chocolate.getFilling());
        assertEquals("Dark", chocolate.getType());
        assertArrayEquals(img, chocolate.getImageData());
    }

    @Test
    void testSetters() {
        Chocolate chocolate = new Chocolate(
                102, "Lindt", 80.0, 30.0, 18.0,
                60.0, "None", "Milk", null
        );

        chocolate.setCocoaPercentage(85.0);
        chocolate.setFilling("Almond");
        chocolate.setType("White");

        assertEquals(85.0, chocolate.getCocoaPercentage());
        assertEquals("Almond", chocolate.getFilling());
        assertEquals("White", chocolate.getType());
    }
}