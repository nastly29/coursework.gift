package menu.menuForSweetsBase;

import gift.sweets.Candy;
import gift.sweets.Sweets;
import menu.helpers.SweetBaseHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisplaySweetsBaseTest {
    private DisplaySweetsBase displaySweetsBase;
    private MockedStatic<SweetBaseHelper> mockedHelper;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        displaySweetsBase = new DisplaySweetsBase();
        mockedHelper = mockStatic(SweetBaseHelper.class);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        mockedHelper.close();
        System.setOut(originalOut);
    }

    @Test
    void name() {
        assertEquals("Переглянути наявні солодощі", displaySweetsBase.name());
    }

    @Test
    void execute_EmptyBase() {
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(List.of());

        displaySweetsBase.execute();

        String output = outContent.toString().trim();
        assertEquals("База даних порожня.", output, "Повідомлення повинно повідомляти, що база порожня.");
    }

    @Test
    void execute_NonEmptyBase() {
        Candy candy = new Candy(101, "Цукерка", 100.0, 10.0, 10.0, "ваніль", "шоколадна");

        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(List.of(candy));

        displaySweetsBase.execute();

        String expectedOutput = "101| тип:candy| назва:Цукерка| ціна(грн):10.0| вага(г):100.0| рівень цукру(%):10.0| начинка:ваніль| вид:шоколадна";

        String output = outContent.toString().trim();
        assertEquals(expectedOutput, output, "Вивід повинен збігатися з вмістом бази даних.");
    }
}