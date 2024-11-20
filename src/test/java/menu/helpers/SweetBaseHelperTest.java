package menu.helpers;

import gift.sweets.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SweetBaseHelperTest {
    private static final String TEST_DATABASE_FILE = "test_sweets_database.txt";
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        SweetBaseHelper.setDatabaseFile(TEST_DATABASE_FILE);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        java.io.File file = new java.io.File(TEST_DATABASE_FILE);
        if (file.exists()) {
            file.delete();
        }
        System.setOut(originalOut);
    }

    @Test
    void testLoadSweetsFromFile_ValidData() {
        List<Sweets> testSweets = List.of(
                new Candy(101, "Цукерка", 200.0, 10.0, 50.0, "ваніль", "шоколадна"),
                new Chocolate(102, "Шоколад", 300.0, 15.0, 80.0, 70.0, "горіх", "молочний")
        );

        SweetBaseHelper.updateContent(testSweets);
        List<Sweets> sweets = SweetBaseHelper.loadSweetsFromFile();

        assertEquals(2, sweets.size(), "Кількість завантажених об'єктів повинна дорівнювати 2.");
        assertEquals("candy", sweets.get(0).getSweetType(), "Перший об'єкт повинен бути типу 'candy'.");
        assertEquals("chocolate", sweets.get(1).getSweetType(), "Другий об'єкт повинен бути типу 'chocolate'.");
    }

    @Test
    void testLoadSweetsFromFile_EmptyFile() {
        SweetBaseHelper.updateContent(List.of());
        List<Sweets> sweets = SweetBaseHelper.loadSweetsFromFile();
        assertTrue(sweets.isEmpty(), "Список солодощів повинен бути порожнім.");
    }

    @Test
    void testParseSweetFromLine_InvalidFormat() {
        String invalidLine = "101| тип:невідомий| назва:Щось| ціна:50.0| вага:200.0";
        Sweets sweet = SweetBaseHelper.parseSweetFromLine(invalidLine);
        assertNull(sweet, "Для неправильного формату повинен повертатися null.");
    }

    @Test
    void testParseSweetFromLine_Jelly() {
        String line = "201| тип:jelly| назва:Мармелад| ціна:30.0| вага:150.0| рівень цукру:5.0| фруктовий смак:вишня| форма:серце";

        Sweets sweet = SweetBaseHelper.parseSweetFromLine(line);

        assertNotNull(sweet, "Об'єкт для 'jelly' не повинен бути null.");
        assertEquals("jelly", sweet.getSweetType(), "Тип повинен бути 'jelly'.");
        assertEquals("Мармелад", sweet.getName(), "Назва має бути 'Мармелад'.");
    }

    @Test
    void testParseSweetFromLine_Gingerbread() {
        String line = "202| тип:gingerbread| назва:Пряник| ціна:40.0| вага:100.0| рівень цукру:15.0| форма:зірка| глазурований:так";

        Sweets sweet = SweetBaseHelper.parseSweetFromLine(line);

        assertNotNull(sweet, "Об'єкт для 'gingerbread' не повинен бути null.");
        assertEquals("gingerbread", sweet.getSweetType(), "Тип повинен бути 'gingerbread'.");
        assertEquals("Пряник", sweet.getName(), "Назва має бути 'Пряник'.");
    }

    @Test
    void testUpdateContent() {
        List<Sweets> sweets = List.of(
                new Candy(101, "Цукерка", 200.0, 10.0, 50.0, "ваніль", "шоколадна"),
                new Chocolate(102, "Шоколад", 300.0, 15.0, 80.0, 70.0, "горіх", "молочний")
        );

        SweetBaseHelper.updateContent(sweets);
        List<Sweets> loadedSweets = SweetBaseHelper.loadSweetsFromFile();

        assertEquals(sweets.size(), loadedSweets.size(), "Вміст файлу повинен збігатися з оновленими даними.");
        assertEquals("candy", loadedSweets.get(0).getSweetType(), "Перший об'єкт повинен бути типу 'candy'.");
        assertEquals("chocolate", loadedSweets.get(1).getSweetType(), "Другий об'єкт повинен бути типу 'chocolate'.");
    }

    @Test
    void testSaveSweet() {
        Sweets sweet = new Candy(103, "Нова цукерка", 150.0, 5.0, 30.0, "полуниця", "жувальна");
        SweetBaseHelper.saveSweet(sweet);

        List<Sweets> loadedSweets = SweetBaseHelper.loadSweetsFromFile();

        assertEquals(1, loadedSweets.size(), "Файл повинен містити одну солодощу.");
        assertEquals("candy", loadedSweets.getFirst().getSweetType(), "Об'єкт повинен бути типу 'candy'.");
        assertEquals("Нова цукерка", loadedSweets.getFirst().getName(), "Назва повинна бути 'Нова цукерка'.");
    }

    @Test
    void testParseSweetFromLine_CatchBlock() {
        String invalidLine = "101| тип:candy| назва:Цукерка| ціна:50.0| вага:не_число| рівень цукру:10.0";
        Sweets sweet = SweetBaseHelper.parseSweetFromLine(invalidLine);

        assertNull(sweet, "Для неправильного формату має повертатися null.");
        assertTrue(outContent.toString().contains("Помилка розбору"), "Повідомлення про помилку має бути виведено.");
    }

    @Test
    void testLoadSweetsFromFile_CatchBlock() {
        SweetBaseHelper.setDatabaseFile("non_existing_file.txt");
        List<Sweets> sweets = SweetBaseHelper.loadSweetsFromFile();

        assertTrue(sweets.isEmpty(), "Список солодощів має бути порожнім.");
        assertTrue(outContent.toString().contains("Помилка читання з файлу"), "Повідомлення про помилку читання має бути виведено.");
    }

    @Test
    void testParseCandy_CatchBlock() {
        String[] parts = {
                "101", "тип:candy", "назва:Цукерка", "ціна:50.0", "вага:200.0", "рівень цукру:10.0", "начинка:", "вид:"
        };
        Sweets generalSweets = new Sweets(101, "Цукерка", 200.0, 10.0, 50.0, "candy");

        Candy candy = SweetBaseHelper.parseCandy(parts, generalSweets);

        assertNull(candy, "Якщо дані некоректні, метод має повертати null.");
        assertTrue(outContent.toString().contains("Помилка розбору цукерок"), "Повідомлення про помилку має бути виведено.");
    }

    @Test
    void testParseChocolate_CatchBlock() {
        String[] parts = {
                "101", "тип:chocolate", "назва:Цукерка", "ціна:50.0", "вага:200.0", "рівень цукру:10.0", "начинка:", "вид:"
        };
        Sweets generalSweets = new Sweets(101, "Цукерка", 200.0, 10.0, 50.0, "chocolate");

        Chocolate chocolate = SweetBaseHelper.parseChocolate(parts, generalSweets);

        assertNull(chocolate, "Якщо дані некоректні, метод має повертати null.");
        assertTrue(outContent.toString().contains("Помилка розбору шоколаду"), "Повідомлення про помилку має бути виведено.");
    }

    @Test
    void testParseJelly_CatchBlock() {
        String[] parts = {
                "101", "тип:jelly", "назва:Цукерка", "ціна:50.0", "вага:200.0", "рівень цукру:10.0", "начинка:", "вид:"
        };
        Sweets generalSweets = new Sweets(101, "Цукерка", 200.0, 10.0, 50.0, "jelly");

        Chocolate jelly = SweetBaseHelper.parseChocolate(parts, generalSweets);

        assertNull(jelly, "Якщо дані некоректні, метод має повертати null.");
        assertTrue(outContent.toString().contains("Помилка розбору шоколаду"), "Повідомлення про помилку має бути виведено.");
    }

    @Test
    void testParseGingerBread_CatchBlock() {
        String[] parts = {
                "101", "тип:gingerbread", "назва:Цукерка", "ціна:50.0", "вага:200.0", "рівень цукру:10.0", "начинка:", "вид:"
        };
        Sweets generalSweets = new Sweets(101, "Цукерка", 200.0, 10.0, 50.0, "gingerbread");

        Chocolate gingerbread = SweetBaseHelper.parseChocolate(parts, generalSweets);

        assertNull(gingerbread, "Якщо дані некоректні, метод має повертати null.");
        assertTrue(outContent.toString().contains("Помилка розбору шоколаду"), "Повідомлення про помилку має бути виведено.");
    }

}
