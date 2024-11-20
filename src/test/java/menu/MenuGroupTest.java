package menu;

import gift.Gift;
import menu.helpers.UserInputHelper;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuGroupTest {
    private MenuGroup menuGroup;
    private MockedStatic<UserInputHelper> mockedInput;
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        menuGroup = new MenuGroup("Тестове меню");
        mockedInput = mockStatic(UserInputHelper.class);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        mockedInput.close();
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testName() {
        assertEquals("Тестове меню", menuGroup.name(), "Назва меню повинна бути 'Тестове меню'.");
    }

    @Test
    void testAddItem() {
        MenuItem item1 = mock(MenuItem.class);
        MenuItem item2 = mock(MenuItem.class);

        menuGroup.addItem(item1);
        menuGroup.addItem(item2);

        List<MenuItem> items = menuGroup.getItems();
        assertEquals(2, items.size(), "Кількість елементів меню повинна дорівнювати 2.");
        assertSame(item1, items.get(0), "Перший елемент має відповідати доданому.");
        assertSame(item2, items.get(1), "Другий елемент має відповідати доданому.");
    }

    @Test
    void testGetItems_Empty() {
        assertTrue(menuGroup.getItems().isEmpty(), "Список елементів меню повинен бути порожнім.");
    }

    @Test
    void testPrintMenu() {
        MenuItem item1 = mock(MenuItem.class);
        MenuItem item2 = mock(MenuItem.class);
        when(item1.name()).thenReturn("Пункт 1");
        when(item2.name()).thenReturn("Пункт 2");

        menuGroup.addItem(item1).addItem(item2);
        menuGroup.printMenu();

        String output = outContent.toString();
        assertTrue(output.contains("====================================="), "Вивід повинен містити розділові лінії.");
        assertTrue(output.contains("Тестове меню:"), "Вивід повинен містити назву меню 'Тестове меню:'.");
        assertTrue(output.contains("1. Пункт 1"), "Вивід повинен містити пункт '1. Пункт 1'.");
        assertTrue(output.contains("2. Пункт 2"), "Вивід повинен містити пункт '2. Пункт 2'.");
    }

    @Test
    void testProcessChoice_Valid() {
        MenuItem item1 = mock(MenuItem.class);
        menuGroup.addItem(item1);

        boolean result = menuGroup.processChoice(1);

        verify(item1).execute();
        assertFalse(result, "Метод повинен повернути false, якщо меню не завершується.");
    }

    @Test
    void testProcessChoice_Invalid() {
        boolean result = menuGroup.processChoice(99);

        assertFalse(result, "Метод повинен повернути false для некоректного вибору.");
        assertTrue(outContent.toString().contains("Невірний вибір!"), "Повідомлення про помилковий вибір повинно з'явитися.");
    }

    @Test
    void testExecute_MainMenuExit() {
        menuGroup = new MenuGroup("Головне меню", true);
        mockedInput.when(() -> UserInputHelper.promptInt("Ваш вибір (0 для виходу) -> ")).thenReturn(0);

        menuGroup.execute();

        assertTrue(outContent.toString().contains("Програму завершено."), "Повідомлення про завершення програми повинно з'явитися.");
    }

    @Test
    void testExecute_NavigateAndExecuteItem() {
        MenuItem item1 = mock(MenuItem.class);
        menuGroup.addItem(item1);

        mockedInput.when(() -> UserInputHelper.promptInt("Ваш вибір (0 для виходу) -> ")).thenReturn(1, 0);

        menuGroup.execute();

        verify(item1, times(1)).execute();
    }

    @Test
    void testExecute_GiftMenuWithoutGift() {
        MenuGroup giftMenu = new MenuGroup("Меню дій з подарунком");

        try (MockedStatic<Gift> mockedGift = mockStatic(Gift.class)) {
            mockedGift.when(Gift::exists).thenReturn(false);

            giftMenu.execute();

            String output = outContent.toString();
            assertTrue(output.contains("Подарунок ще не створено. Створіть подарунок, щоб отримати доступ до цього меню."),
                    "Повідомлення про відсутність подарунка повинно з'явитися.");
        }
    }
}
