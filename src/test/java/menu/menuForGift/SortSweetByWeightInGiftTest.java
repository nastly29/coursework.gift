package menu.menuForGift;

import gift.Gift;
import gift.sweets.Candy;
import gift.sweets.Sweets;

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

class SortSweetByWeightInGiftTest {
    private SortSweetByWeightInGift sortSweetByWeightInGift;
    private MockedStatic<Gift> mockedGift;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        sortSweetByWeightInGift = new SortSweetByWeightInGift();
        mockedGift = mockStatic(Gift.class);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        mockedGift.close();
        System.setOut(originalOut);
    }

    @Test
    void name() {
        String expectedName = "Сортувати солодощі в подарунку за вагою";
        assertEquals(expectedName, sortSweetByWeightInGift.name(), "Метод name() повинен повертати правильну назву.");
    }

    @Test
    void execute() {
      Gift gift = mock(Gift.class);
      mockedGift.when(() -> Gift.getInstance(null,null)).thenReturn(gift);

      Sweets sweet1 = new Candy(101, "Цукерка", 200.0, 10.0, 50.0, "ваніль", "шоколадна");
      Sweets sweet2 = new Candy(102, "Цукерка2", 300.0, 20.0, 80.0, "карамель", "молочна");
      Sweets sweet3 = new Candy(103, "Цукерка3", 100.0, 5.0, 30.0, "фруктова", "жувальна");
      List<Sweets> sweets = new ArrayList<>(List.of(sweet1, sweet2, sweet3));
      when(gift.getSweets()).thenReturn(sweets);

      sortSweetByWeightInGift.execute();

      String output = outContent.toString().trim();
      String[] lines = output.split("\n");
      assertTrue(lines[0].contains("Солодощі в подарунку відсортовано за вагою:"), "Перше повідомлення повинно бути про сортування.");
      assertTrue(lines[1].contains("Цукерка3"), "Перша цукерка повинна бути 'Цукерка3'.");
      assertTrue(lines[2].contains("Цукерка"), "Друга цукерка повинна бути 'Цукерка'.");
      assertTrue(lines[3].contains("Цукерка2"), "Третя цукерка повинна бути 'Цукерка2'.");
    }
}