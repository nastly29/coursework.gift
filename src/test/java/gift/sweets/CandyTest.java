package gift.sweets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandyTest {
    private Candy candy;

    @BeforeEach
    void setUp() {
        candy = new Candy(5, "Цукерка", 30.0, 5.5, 11.0,
                "горіхова", "Шоколадна");
    }

    @Test
    void testToString() {
        String expectedString = "5| тип:candy| назва:Цукерка| ціна(грн):11.0| вага(г):30.0| рівень цукру(%):5.5| начинка:горіхова| вид:Шоколадна";
        assertEquals(expectedString, candy.toString(), "Результат toString() не відповідає очікуваному формату.");
    }

}