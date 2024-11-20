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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemoveSweetFromGiftTest {
    private RemoveSweetFromGift removeSweetFromGift;
    private MockedStatic<UserInputHelper> mockedInput;
    private MockedStatic<Gift> mockedGift;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        removeSweetFromGift = new RemoveSweetFromGift();

        mockedInput = mockStatic(UserInputHelper.class);
        mockedGift = mockStatic(Gift.class);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown(){
        mockedGift.close();
        mockedInput.close();
        System.setOut(originalOut);
    }

    @Test
    void name() {
        String expectedName = "Видалити солодощі з подарунка";
        assertEquals(expectedName, removeSweetFromGift.name(), "Метод name() повинен повертати 'Видалити солодощі з подарунка'.");
    }

    @Test
    void testExecute_removeExistingSweet() {
         Gift gift = mock(Gift.class);
         mockedGift.when(()-> Gift.getInstance(null, null)).thenReturn(gift);

        List<Sweets> sweets = new ArrayList<>();
        Sweets sweet1 = new Candy(101, "Цукерка", 200.0, 10.0, 50.0, "ваніль", "шоколадна");
        sweets.add(sweet1);
        when(gift.getSweets()).thenReturn(sweets);

        mockedInput.when(() -> UserInputHelper.promptInt("Введіть код солодощів для видалення: "))
                .thenReturn(101);

        removeSweetFromGift.execute();
        assertTrue(sweets.isEmpty(),"Список солодощів повинен бути порожнім після видалення.");
        String output = outContent.toString().trim();
        assertTrue(output.contains("Солодощі з кодом 101 успішно видалено"),"Повідомлення повинно підтверджувати успішне видалення.");
    }

    @Test
    void execute_removeNonExistingSweet() {
        Gift gift = mock(Gift.class);
        mockedGift.when(()-> Gift.getInstance(null, null)).thenReturn(gift);

        List<Sweets> sweets = new ArrayList<>();
        Sweets sweet1 = new Candy(101, "Цукерка", 200.0, 10.0, 50.0, "ваніль", "шоколадна");
        sweets.add(sweet1);
        when(gift.getSweets()).thenReturn(sweets);

        mockedInput.when(() -> UserInputHelper.promptInt("Введіть код солодощів для видалення: "))
                .thenReturn(102);

        removeSweetFromGift.execute();
        assertEquals(1, sweets.size(), "Список солодощів не повинен змінюватися, якщо код не знайдено.");
        String output = outContent.toString().trim();
        assertTrue(output.contains("Солодощі з кодом 102 не знайдено"), "Повідомлення повинно повідомляти, що солодощі не знайдено.");
    }
}