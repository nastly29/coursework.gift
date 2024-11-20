package menu.helpers;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class UserInputHelperTest {

    @Test
    void setScanner() {
        UserInputHelper.setScanner(new Scanner(""));
    }

    @Test
    void promptDouble_valid() {
        UserInputHelper.setScanner(new Scanner("10,5\n"));
        double result = UserInputHelper.promptDouble("Введіть число: ");
        assertEquals(10.5, result, "Метод promptDouble повинен повертати 10.5.");
    }

    @Test
    void promptDouble_invalid1() {
        UserInputHelper.setScanner(new Scanner("-10,5\n10,5\n"));
        double result = UserInputHelper.promptDouble("Введіть число: ");
        assertEquals(10.5, result, "Метод promptDouble повинен пропустити -10.5 і повернути 10.5.");
    }

    @Test
    void promptDouble_invalid2() {
        UserInputHelper.setScanner(new Scanner("hhh\n10,5\n"));
        double result = UserInputHelper.promptDouble("Введіть число: ");
        assertEquals(10.5, result, "Метод promptDouble повинен пропустити hhh і повернути 10.5.");
    }

    @Test
    void promptInt_valid() {
        UserInputHelper.setScanner(new Scanner("10\n"));
        int result = UserInputHelper.promptInt("Введіть число: ");
        assertEquals(10, result, "Метод promptInt повинен повертати 10.");
    }

    @Test
    void promptInt_invalid1() {
        UserInputHelper.setScanner(new Scanner("-10\n10\n"));
        int result = UserInputHelper.promptInt("Введіть число: ");
        assertEquals(10, result, "Метод promptInt повинен пропустити -10 і повернути 10.");
    }

    @Test
    void promptInt_invalid2() {
        UserInputHelper.setScanner(new Scanner("10.5\n10\n"));
        int result = UserInputHelper.promptInt("Введіть число: ");
        assertEquals(10, result, "Метод promptInt повинен пропустити 10.5 і повернути 10.");
    }

    @Test
    void promptString() {
        UserInputHelper.setScanner(new Scanner("Привіт\n"));
        String result = UserInputHelper.promptString("Введіть рядок: ");
        assertEquals("Привіт", result, "Метод promptString повинен повертати 'Привіт'.");
    }

    @Test
    void promptString_invalid() {
        UserInputHelper.setScanner(new Scanner(" \nПривіт\n"));
        String result = UserInputHelper.promptString("Введіть рядок: ");
        assertEquals("Привіт", result, "Метод promptString повинен пропустити ' ' і повернути 'Привіт'.");
    }

    @Test
    void promptChocolateType_valid() {
        UserInputHelper.setScanner(new Scanner("Молочний\n"));
        String result = UserInputHelper.promptChocolateType();
        assertEquals("Молочний", result, "Метод promptChocolateType повинен повертати 'Молочний'.");
    }

    @Test
    void promptChocolateType_invalid() {
        UserInputHelper.setScanner(new Scanner("Невідомий\nМолочний\n"));
        String result = UserInputHelper.promptChocolateType();
        assertEquals("Молочний", result, "Метод promptChocolateType повинен пропустити 'Невідомий' і повернути 'Молочний'.");
    }

    @Test
    void promptCandyType_valid() {
        UserInputHelper.setScanner(new Scanner("Шоколадна\n"));
        String result = UserInputHelper.promptCandyType();
        assertEquals("Шоколадна", result, "Метод promptCandyType повинен повертати 'Шоколадна'.");
    }

    @Test
    void promptCandyType_invalid() {
        UserInputHelper.setScanner(new Scanner("Невідомий\nШоколадна\n"));
        String result = UserInputHelper.promptCandyType();
        assertEquals("Шоколадна", result, "Метод promptCandyType повинен пропустити 'Невідомий' і повернути 'Шоколадна'.");
    }

    @Test
    void promptColor_valid() {
        UserInputHelper.setScanner(new Scanner("Червоний\n"));
        String result = UserInputHelper.promptColor("Введіть колір: ");
        assertEquals("Червоний", result, "Метод promptColor повинен повертати 'Червоний'.");

    }

    @Test
    void promptColor_invalid() {
        UserInputHelper.setScanner(new Scanner("Невідомий\nЧервоний\n"));
        String result = UserInputHelper.promptColor("Введіть колір: ");
        assertEquals("Червоний", result, "Метод promptColor повинен пропустити 'Невідомий' і повернути 'Червоний'.");
    }
}