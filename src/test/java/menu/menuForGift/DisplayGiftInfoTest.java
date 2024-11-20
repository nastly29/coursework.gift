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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisplayGiftInfoTest {
    private DisplayGiftInfo displayGiftInfo;
    private MockedStatic<Gift> mockedGift;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        displayGiftInfo = new DisplayGiftInfo();
        mockedGift = mockStatic(Gift.class);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown(){
        mockedGift.close();
        System.setOut(originalOut);
    }

    @Test
    void testName() {
        String expectedName = "Відобразити інформацію про подарунок";
        assertEquals(expectedName, displayGiftInfo.name(), "Метод name() повинен повертати 'Відобразити інформацію про подарунок'.");
    }

    @Test
    void execute() {
        Gift gift = mock(Gift.class);
        mockedGift.when(() -> Gift.getInstance(null,null)).thenReturn(gift);

        Candy candy = new Candy(101, "Цукерка", 200.0, 10.0, 50.0, "ваніль", "шоколадна");
        Candy candy2 = new Candy(102, "Цукерка2", 300.0, 20.0, 80.0, "карамель", "молочна");
        when(gift.getSweets()).thenReturn(List.of(candy, candy2));
        when(gift.toString()).thenReturn("Святковий подарунок");

        displayGiftInfo.execute();

        String output = outContent.toString();
        assertTrue(output.contains("Святковий подарунок"), "Вивід повинен містити інформацію про подарунок.");
        assertTrue(output.contains("Загальна ціна:130.0"), "Вивід повинен містити правильну загальну ціну.");
        assertTrue(output.contains("Загальна вага:500.0"), "Вивід повинен містити правильну загальну вагу.");
    }

    @Test
    void getTotalPrice() {
        Sweets sweet1 = new Candy(101, "Цукерка", 20.0, 10.0, 5.0, "ваніль", "шоколадна");
        Sweets sweet2 = new Candy(102, "Цукерка2", 30.0, 20.0, 8.0, "карамель", "молочна");

        displayGiftInfo.getTotalPrice(List.of(sweet1,sweet2));
        String output = outContent.toString().trim();
        assertTrue(output.contains("13.0"), "Вивід повинен містити загальну ціну 13.0 грн.");
    }

    @Test
    void getTotalWeight() {
        Sweets sweet1 = new Candy(101, "Цукерка", 20.0, 10.0, 5.0, "ваніль", "шоколадна");
        Sweets sweet2 = new Candy(102, "Цукерка2", 30.0, 20.0, 8.0, "карамель", "молочна");

        displayGiftInfo.getTotalWeight(List.of(sweet1,sweet2));
        String output = outContent.toString().trim();
        assertTrue(output.contains("50.0"), "Вивід повинен містити загальну ціну 50.0 г.");
    }
}