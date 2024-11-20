package gift;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PackageTest {
    private Package pack;

    @BeforeEach
    void setUp() {
        pack = new Package("Червоний", "Червоний", "Повідомлення");
    }
    @Test
    void testToString() {
        String expectedString = "Колір коробки: Червоний, Колір стрічки: Червоний, Записка: Повідомлення";
        assertEquals(expectedString, pack.toString(), "Результат toString() не відповідає очікуваному формату.");
    }
}