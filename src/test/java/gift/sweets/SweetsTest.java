package gift.sweets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SweetsTest {
    private Sweets sweet;

    @BeforeEach
    void setUp() {
        sweet = new Sweets(4, "Шоколад", 200.0, 15.5, 50.0, "chocolate");
    }

    @Test
    void testSetName() {
        sweet.setName("Цукерка");
        assertEquals("Цукерка", sweet.getName(), "Назва солодощів повинна бути змінена на 'Цукерка'.");
    }

    @Test
    void testGetName() {
        assertEquals("Шоколад", sweet.getName(), "Назва солодощів повинна бути 'Шоколад'.");
    }

    @Test
    void testGetPrice() {
        assertEquals(50.0 , sweet.getPrice(), "Ціна солодощів повинна дорівнювати 50.0.");
    }

    @Test
    void testGetWeight() {
        assertEquals(200.0, sweet.getWeight(), "Вага солодощів повинна дорівнювати 200.0.");
    }

    @Test
    void testGetCode() {
        assertEquals(4, sweet.getCode(), "Код солодощів повинен дорівнювати 4.");
    }

    @Test
    void testGetSugarContent() {
        assertEquals(15.5, sweet.getSugarContent(), "Рівень цукру повинен дорівнювати 15.5 %.");
    }

    @Test
    void testToString() {
        String expectedString = "4| тип:chocolate| назва:Шоколад| ціна(грн):50.0| вага(г):200.0| рівень цукру(%):15.5";
        assertEquals(expectedString, sweet.toString(), "Результат toString() не відповідає очікуваному формату.");
    }
}