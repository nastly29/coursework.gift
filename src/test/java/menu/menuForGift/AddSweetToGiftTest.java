package menu.menuForGift;

import gift.Gift;

import gift.sweets.Candy;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AddSweetToGiftTest {
    private AddSweetToGift addSweetToGift;
    private Gift gift;
    private MockedStatic<SweetBaseHelper> mockedHelper;
    private MockedStatic<UserInputHelper> mockedInput;
    private MockedStatic<Gift> mockedGift;

    @BeforeEach
    void setUp() {
        addSweetToGift = new AddSweetToGift();

        gift = Mockito.mock(Gift.class);
        mockedHelper = mockStatic(SweetBaseHelper.class);
        mockedInput = mockStatic(UserInputHelper.class);
        mockedGift = mockStatic(Gift.class);

        mockedGift.when(() -> Gift.getInstance(null, null)).thenReturn(gift);
    }

    @AfterEach
    void tearDown() {
        mockedHelper.close();
        mockedInput.close();
        mockedGift.close();
    }

    @Test
    void testName() {
        String expectedName = "Додати солодощі до подарунка";
        assertEquals(expectedName, addSweetToGift.name(), "Метод name() повинен повертати 'Додати солодощі до подарунка'.");
    }

    @Test
    void testExecute_SweetFound(){
        Candy candy = new Candy(101, "Цукерка", 15.0,5.0,10.0, "ваніль", "іриска");
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(List.of(candy));

        mockedInput.when(() -> UserInputHelper.promptInt("\nВведіть код товару (або 0 для виходу): "))
                .thenReturn(101, 0);

        addSweetToGift.execute();
        verify(gift).addSweet(candy);
    }

    @Test
    void  testExecute_SweetNotFound(){
        Candy candy = new Candy(101, "Цукерка", 15.0,5.0,10.0, "ваніль", "іриска");
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(List.of(candy));

        mockedInput.when(() -> UserInputHelper.promptInt("\nВведіть код товару (або 0 для виходу): "))
                .thenReturn(102,0);

        addSweetToGift.execute();
        verify(gift,never()).addSweet(any());
    }
}
