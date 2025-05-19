package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PackageTest {

    @Test
    void testConstructorAndGetters() {
        String boxColor = "Red";
        String ribbonColor = "Gold";
        String message = "З Днем народження!";

        Package pack = new Package(boxColor, ribbonColor, message);

        assertEquals(boxColor, pack.getBoxColor());
        assertEquals(ribbonColor, pack.getRibbonColor());
        assertEquals(message, pack.getMessage());
    }

    @Test
    void testEmptyFields() {
        Package pack = new Package("", "", "");
        assertEquals("", pack.getBoxColor());
        assertEquals("", pack.getRibbonColor());
        assertEquals("", pack.getMessage());
    }

    @Test
    void testNullValues() {
        Package pack = new Package(null, null, null);
        assertNull(pack.getBoxColor());
        assertNull(pack.getRibbonColor());
        assertNull(pack.getMessage());
    }
}