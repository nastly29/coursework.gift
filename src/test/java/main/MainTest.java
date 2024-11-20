package main;

import menu.helpers.UserInputHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

class MainTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private MockedStatic<UserInputHelper> mockedInput;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
        mockedInput = mockStatic(UserInputHelper.class);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        mockedInput.close();
    }

    @Test
    void testMainMenuWithValidInput() {
        mockedInput.when(() -> UserInputHelper.promptInt("Ваш вибір -> "))
                .thenReturn(1, 0);

        Main.main(new String[]{});

        String output = outContent.toString();
        assertTrue(output.contains("Головне меню"), "Меню повинно містити заголовок 'Головне меню'.");
        assertTrue(output.contains("Створити подарунок"), "Меню повинно містити пункт 'Створити подарунок'.");
    }
}
