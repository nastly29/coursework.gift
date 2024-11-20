package menu.menuForGift;

import gift.Gift;

import menu.helpers.UserInputHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateGiftTest {
    private CreateGift createGift;
    private MockedStatic<UserInputHelper> mockedInput;
    private MockedStatic<Gift> mockedGift;

    @BeforeEach
    void setUp() {
        createGift = new CreateGift();
        mockedInput = mockStatic(UserInputHelper.class);
        mockedGift = mockStatic(Gift.class);
    }

    @AfterEach
    void tearDown() {
        mockedInput.close();
        mockedGift.close();
    }

    @Test
    void testName() {
        String expectedName = "Створити подарунок";
        assertEquals(expectedName, createGift.name(), "Метод name() повинен повертати 'Створити подарунок'.");
    }

    @Test
    void testExecute_GiftAlreadyExists() {
        mockedGift.when(Gift::exists).thenReturn(true);
        createGift.execute();
        mockedGift.verify(Gift::exists, times(1));
        mockedGift.verifyNoMoreInteractions();
    }

    @Test
    void testExecute_GiftNotExists(){
        mockedGift.when(Gift::exists).thenReturn(false);

        mockedInput.when(() -> UserInputHelper.promptString("Введіть назву подарунка: "))
                .thenReturn("Святковий подарунок");
        mockedInput.when(() -> UserInputHelper.promptColor("Введіть колір коробки: "))
                .thenReturn("Червоний");
        mockedInput.when(() -> UserInputHelper.promptColor("Введіть колір стрічки: "))
                .thenReturn("Білий");
        mockedInput.when(() -> UserInputHelper.promptString("Введіть повідомлення на упаковці: "))
                .thenReturn("З днем народження!");

        createGift.execute();
        mockedGift.verify(() -> Gift.getInstance(
                eq("Святковий подарунок"),
                argThat(pack -> "Червоний".equals(pack.getBoxColor()) &&
                                "Білий".equals(pack.getRibbonColor()) &&
                                "З днем народження!".equals(pack.getMessage())
                )
        ));
    }
}