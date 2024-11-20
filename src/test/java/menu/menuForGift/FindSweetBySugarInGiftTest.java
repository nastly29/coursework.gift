package menu.menuForGift;

import gift.Gift;
import gift.sweets.Candy;
import gift.sweets.Sweets;
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

class FindSweetBySugarInGiftTest {
    private FindSweetBySugarInGift findSweetBySugarInGift;
    private MockedStatic<UserInputHelper> mockedInput;
    private MockedStatic<Gift> mockedGift;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        findSweetBySugarInGift = new FindSweetBySugarInGift();
        mockedInput=mockStatic(UserInputHelper.class);
        mockedGift = mockStatic(Gift.class);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown(){
        mockedInput.close();
        mockedGift.close();
        System.setOut(originalOut);
    }

    @Test
    void name() {
        String expectedName = "Пошук солодощів за рівнем цукру";
        assertEquals(expectedName, findSweetBySugarInGift.name(), "Метод name() повинен повертати 'Пошук солодощів за рівнем цукру'.");
    }

    @Test
    void testExecute_SweetsFound(){
        Gift gift = mock(Gift.class);
        mockedGift.when(() -> Gift.getInstance(null,null)).thenReturn(gift);

        Sweets sweet1 = new Candy(101, "Цукерка", 200.0, 10.0, 50.0, "ваніль", "шоколадна");
        Sweets sweet2 = new Candy(102, "Цукерка2", 300.0, 20.0, 80.0, "карамель", "молочна");
        when(gift.getSweets()).thenReturn(List.of(sweet1, sweet2));

        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть мінімальний відсоток цукру: "))
                .thenReturn(10.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть максимальний відсоток цукру: "))
                .thenReturn(20.0);

        findSweetBySugarInGift.execute();
        String output = outContent.toString();
        assertTrue(output.contains("Цукерка"), "Вивід повинен містити інформацію про 'Цукерка'.");
        assertTrue(output.contains("Цукерка2"), "Вивід повинен містити інформацію про 'Цукерка2'.");
    }

    @Test
    void testExecute_SweetsNotFound(){
        Gift gift = mock(Gift.class);
        mockedGift.when(() -> Gift.getInstance(null,null)).thenReturn(gift);
        when(gift.getSweets()).thenReturn(List.of());

        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть мінімальний відсоток цукру: "))
                .thenReturn(10.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть максимальний відсоток цукру: "))
                .thenReturn(20.0);

        findSweetBySugarInGift.execute();
        String output = outContent.toString().trim();
        assertTrue(output.contains("Не знайдено солодощів"), "Повідомлення повинно бути про те, що нічого не знайдено.");
    }
}