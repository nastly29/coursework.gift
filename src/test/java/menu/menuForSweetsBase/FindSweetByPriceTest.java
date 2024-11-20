package menu.menuForSweetsBase;

import gift.sweets.Candy;
import gift.sweets.Sweets;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindSweetByPriceTest {
    private FindSweetByPrice findSweetByPrice;
    private MockedStatic<SweetBaseHelper> mockedHelper;
    private MockedStatic<UserInputHelper> mockedInput;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        findSweetByPrice = new FindSweetByPrice();
        mockedHelper = mockStatic(SweetBaseHelper.class);
        mockedInput = mockStatic(UserInputHelper.class);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        mockedHelper.close();
        mockedInput.close();
        System.setOut(originalOut);
    }

    @Test
    void name() {
        assertEquals("Пошук солодощів за ціною", findSweetByPrice.name());
    }

    @Test
    void testExecute_EmptyBase() {
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(List.of());

        findSweetByPrice.execute();

        String output = outContent.toString().trim();
        assertTrue(output.contains("База даних солодощів порожня."), "Повідомлення повинно інформувати про порожню базу.");
    }

    @Test
    void testExecute_SweetsFoundInPriceRange() {
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть мінімальну ціну: ")).thenReturn(50.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть максимальну ціну: ")).thenReturn(80.0);

        Sweets sweet1 = new Candy(101, "Цукерка1", 200.0, 10.0, 75.0, "ваніль", "шоколадна");
        Sweets sweet2 = new Candy(102, "Цукерка2", 300.0, 20.0, 85.0, "карамель", "шоколадна");
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(List.of(sweet1, sweet2));

        findSweetByPrice.execute();

        String output = outContent.toString().trim();
        assertTrue(output.contains("Цукерка1"), "Вивід повинен містити інформацію про 'Цукерка1'.");
    }

    @Test
    void execute_NoSweetsInPriceRange() {
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть мінімальну ціну: ")).thenReturn(200.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть максимальну ціну: ")).thenReturn(300.0);

        Sweets sweet1 = new Candy(101, "Цукерка1", 200.0, 10.0, 75.0, "ваніль", "шоколадна");
        Sweets sweet2 = new Candy(102, "Цукерка2", 300.0, 20.0, 85.0, "карамель", "шоколадна");
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(List.of(sweet1, sweet2));

        findSweetByPrice.execute();

        String output = outContent.toString().trim();
        assertTrue(output.contains("Не знайдено солодощів у вказаному діапазоні цін."), "Повідомлення повинно інформувати про відсутність солодощів.");
    }
}
