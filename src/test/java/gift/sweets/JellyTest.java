package gift.sweets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JellyTest {
    private Jelly jelly;

    @BeforeEach
    void setUp() {
        jelly = new Jelly(5, "Желейка", 50.0,
                10.0, 12.0, "полуниця", "кільце");
    }
    @Test
    void testToString() {
        String expectedString = "5| тип:jelly| назва:Желейка| ціна(грн):12.0| вага(г):50.0" +
                "| рівень цукру(%):10.0| смак:полуниця| форма:кільце";
        assertEquals(expectedString, jelly.toString(), "Результат toString() не відповідає очікуваному формату.");
    }
}