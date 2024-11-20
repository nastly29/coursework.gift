package gift;

import gift.sweets.Candy;
import gift.sweets.Sweets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GiftTest {
    private Gift gift;
    private Package pack;

    @BeforeEach
    void setUp() {
        Gift.instance = null;
        pack = new Package("Червоний", "Червоний", "Повідомлення");
        gift = Gift.getInstance("Подарунок 'Солодощі'",pack);
    }

    @Test
    void testExists() {
        assertTrue(Gift.exists(), "Метод exists() повинен повертати true, якщо екземпляр існує.");
    }

    @Test
    void testToString() {
        Sweets sweet1 = new Sweets(101, "Цукерка", 10.0, 5.0, 2.0, "chocolate");
        Sweets sweet2 = new Sweets(102, "Іриска", 15.0, 8.0, 3.0, "caramel");
        gift.addSweet(sweet1);
        gift.addSweet(sweet2);

        String expectedString = "Подарунок 'Солодощі'\n"+
                "Пакування: Колір коробки: Червоний, Колір стрічки: Червоний, Записка: Повідомлення\n"+
                "Вміст:\n"+
                "101| тип:chocolate| назва:Цукерка| ціна(грн):2.0| вага(г):10.0| рівень цукру(%):5.0\n"+
                "102| тип:caramel| назва:Іриска| ціна(грн):3.0| вага(г):15.0| рівень цукру(%):8.0\n";
        assertEquals(expectedString, gift.toString(), "Результат toString() не відповідає очікуваному формату.");
    }

    @Test
    void testGetName() {
        assertEquals("Подарунок 'Солодощі'", gift.getName(), "Назва подарунка повинна бути 'Подарунок 'Солодощі''.");
    }

    @Test
    void testGetPackage() {
        assertNotNull(gift.getPackage(), "Метод getPackage() не повинен повертати null.");
        assertEquals(pack, gift.getPackage(), "Метод getPackage() повинен повертати коректний об'єкт Package.");
    }

    @Test
    void testGetSweets_NonEmptyList() {
        Sweets sweet1 = new Candy(101, "Цукерка", 200.0, 10.0, 50.0, "ваніль", "шоколадна");
        Sweets sweet2 = new Candy(102, "Цукерка2", 300.0, 15.0, 75.0, "полуниця", "жувальна");

        gift.addSweet(sweet1);
        gift.addSweet(sweet2);

        List<Sweets> sweets = gift.getSweets();
        assertNotNull(sweets, "Список солодощів не повинен бути null.");
        assertEquals(2, sweets.size(), "Список повинен містити 2 елементи.");
        assertTrue(sweets.contains(sweet1), "Список повинен містити перший елемент.");
        assertTrue(sweets.contains(sweet2), "Список повинен містити другий елемент.");
    }

    @Test
    void testGetSweets_EmptyList() {
        List<Sweets> sweets = gift.getSweets();
        assertNotNull(sweets, "Метод getSweets() не повинен повертати null.");
        assertTrue(sweets.isEmpty(), "Список солодощів повинен бути порожнім, якщо жоден об'єкт не було додано.");
    }
}