package gift.sweets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChocolateTest {
    private Chocolate chocolate;

    @BeforeEach
    void setUp() {
        chocolate = new Chocolate(5, "Шоколад", 100.0, 25.0, 30.0,
                45.0, "горіх", "Молочний");
    }

    @Test
    void testToString() {
        String expectedString = "5| тип:chocolate| назва:Шоколад| ціна(грн):30.0" +
                "| вага(г):100.0| рівень цукру(%):25.0| відсоток какао(%):45.0| начинка:горіх| вид:Молочний";
        assertEquals(expectedString, chocolate.toString(), "Результат toString() не відповідає очікуваному формату.");
    }
}