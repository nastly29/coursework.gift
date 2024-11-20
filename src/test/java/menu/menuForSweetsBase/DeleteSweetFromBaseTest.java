package menu.menuForSweetsBase;

import gift.sweets.Candy;
import gift.sweets.Sweets;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class DeleteSweetFromBaseTest {
    private DeleteSweetFromBase deleteSweetFromBase;
    private MockedStatic<UserInputHelper> mockedInput;
    private MockedStatic<SweetBaseHelper> mockedHelper;


    @BeforeEach
    void setUp() {
        deleteSweetFromBase = new DeleteSweetFromBase();
        mockedInput = mockStatic(UserInputHelper.class);
        mockedHelper = mockStatic(SweetBaseHelper.class);
    }

    @AfterEach
    void tearDown() {
        mockedHelper.close();
        mockedInput.close();
    }

    @Test
    void name() {
        assertEquals("Видалити солодощі з бази", deleteSweetFromBase.name());
    }

    @Test
    void testExecute_DeleteNonExistingSweet() {
        Sweets sweet1 = new Candy(101, "Цукерка1", 200.0, 10.0, 50.0, "ваніль", "шоколадна");
        List<Sweets> sweetsList = new ArrayList<>(List.of(sweet1));

        mockedInput.when(() -> UserInputHelper.promptInt("Введіть код солодощів, які потрібно видалити: "))
                .thenReturn(102);
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(sweetsList);

        deleteSweetFromBase.execute();
        mockedHelper.verify(() -> SweetBaseHelper.updateContent(any()), never());
    }

    @Test
    void testExecute_DeleteExistingSweet() {
        Sweets sweet1 = new Candy(101, "Цукерка1", 200.0, 10.0, 50.0, "ваніль", "шоколадна");
        Sweets sweet2 = new Candy(102, "Цукерка2", 300.0, 15.0, 70.0, "горіх", "молочна");
        List<Sweets> sweetsList = new ArrayList<>(List.of(sweet1, sweet2));

        mockedInput.when(() -> UserInputHelper.promptInt("Введіть код солодощів, які потрібно видалити: "))
                .thenReturn(101);
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(sweetsList);

        deleteSweetFromBase.execute();

        mockedHelper.verify(() -> SweetBaseHelper.updateContent(argThat(list ->
                list.size() == 1 && list.getFirst().getCode() == 102
        )), times(1));
    }

    @Test
    void testExecute_EmptyBase(){
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(new ArrayList<>());
        mockedInput.when(() -> UserInputHelper.promptInt("Введіть код солодощів, які потрібно видалити: "))
                .thenReturn(101);
        deleteSweetFromBase.execute();
        mockedHelper.verify(()->SweetBaseHelper.updateContent(any()), never());
    }
}