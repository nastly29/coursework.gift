package gift.sweets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GingerbreadTest {
    @Test
    void testToString_Iced() {
        Gingerbread gingerbread = new Gingerbread(101, "Імбирний пряник",
                200.0, 15.0, 50.0, "Коло", true);
        String expected = "101| тип:gingerbread| назва:Імбирний пряник| ціна(грн):50.0| " +
                "вага(г):200.0| рівень цукру(%):15.0| форма:Коло| глазурований:так";
        assertEquals(expected, gingerbread.toString(), "Результат toString() не відповідає очікуваному формату.");
    }
    @Test
    void testToString_NotIced() {
        Gingerbread gingerbread = new Gingerbread(102, "Звичайний пряник",
                150.0, 10.0, 30.0, "Квадрат", false);
        String expected = "102| тип:gingerbread| назва:Звичайний пряник| ціна(грн):30.0| " +
                "вага(г):150.0| рівень цукру(%):10.0| форма:Квадрат| глазурований:ні";
        assertEquals(expected, gingerbread.toString(), "Результат toString() не відповідає очікуваному формату.");
    }
}