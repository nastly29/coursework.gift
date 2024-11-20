package menu.menuForSweetsBase;

import gift.sweets.*;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddNewSweetToBaseTest {
    private AddNewSweetToBase addNewSweetToBase;
    private MockedStatic<SweetBaseHelper> mockedHelper;
    private MockedStatic<UserInputHelper> mockedInput;

    @BeforeEach
    void setUp() {
        addNewSweetToBase = new AddNewSweetToBase();
        mockedHelper = mockStatic(SweetBaseHelper.class);
        mockedInput = mockStatic(UserInputHelper.class);
    }

    @AfterEach
    void tearDown() {
        mockedHelper.close();
        mockedInput.close();
    }

    @Test
    void Name(){
        assertEquals("Додати нові солодощі", addNewSweetToBase.name());
    }

    @Test
    void testIsCodeUnique_UniqueCode() {
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(List.of());
        assertTrue(addNewSweetToBase.isCodeUnique(101), "Код повинен бути унікальним.");
    }

    @Test
    void testIsCodeUnique_NonUniqueCode() {
        mockedHelper.when(SweetBaseHelper::loadSweetsFromFile).thenReturn(List.of(
                new Candy(101, "Цукерка", 200, 10, 50, "ваніль", "шоколадна")));
        assertFalse(addNewSweetToBase.isCodeUnique(101), "Код не повинен бути унікальним, якщо він вже існує в базі.");
    }

    @Test
    void testCreateCandy() {
        mockedInput.when(() -> UserInputHelper.promptString("Введіть начинку: ")).thenReturn("ваніль");
        mockedInput.when(UserInputHelper::promptCandyType).thenReturn("шоколадна");
        Candy candy = addNewSweetToBase.createCandy(101, "Цукерка", 200, 10, 50);
        assertNotNull(candy);
        assertEquals("Цукерка", candy.getName());
        assertEquals(200, candy.getWeight());
        assertEquals(10, candy.getSugarContent());
        assertEquals(50, candy.getPrice());
        assertEquals("ваніль", candy.getFilling());
        assertEquals("шоколадна", candy.getType());
    }

    @Test
    void testCreateChocolate(){
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть відсоток какао(%): ")).thenReturn(70.0);
        mockedInput.when(() -> UserInputHelper.promptString("Введіть начинку: ")).thenReturn("горіх");
        mockedInput.when(UserInputHelper::promptChocolateType).thenReturn("молочний");

        Chocolate chocolate = addNewSweetToBase.createChocolate(201, "Шоколад", 150.0, 20.0, 80.0);

        assertNotNull(chocolate);
        assertEquals(201, chocolate.getCode());
        assertEquals("Шоколад", chocolate.getName());
        assertEquals(150.0, chocolate.getWeight());
        assertEquals(20.0, chocolate.getSugarContent());
        assertEquals(80.0, chocolate.getPrice());
        assertEquals(70.0, chocolate.getCocoaPercentage());
        assertEquals("горіх", chocolate.getFilling());
        assertEquals("молочний", chocolate.getType());
    }

    @Test
    void testCreateJelly(){
        mockedInput.when(() -> UserInputHelper.promptString("Введіть фруктовий смак: ")).thenReturn("вишня");
        mockedInput.when(() -> UserInputHelper.promptString("Введіть форму: ")).thenReturn("зірка");

        Jelly jelly = addNewSweetToBase.createJelly(202, "Мармелад", 120.0, 15.0, 50.0);

        assertNotNull(jelly);
        assertEquals(202, jelly.getCode());
        assertEquals("Мармелад", jelly.getName());
        assertEquals(120.0, jelly.getWeight());
        assertEquals(15.0, jelly.getSugarContent());
        assertEquals(50.0, jelly.getPrice());
        assertEquals("вишня", jelly.getFruityTaste());
        assertEquals("зірка", jelly.getShape());
    }

    @Test
    void testCreateGingerbread() {
        mockedInput.when(() -> UserInputHelper.promptString("Введіть форму: ")).thenReturn("зірка");
        mockedInput.when(() -> UserInputHelper.promptString("Чи глазурований пряник? (так/ні): ")).thenReturn("так");

        Gingerbread gingerbread = addNewSweetToBase.createGingerbread(203, "Пряник", 100.0, 25.0, 40.0);

        assertEquals(203, gingerbread.getCode());
        assertEquals("Пряник", gingerbread.getName());
        assertEquals(100.0, gingerbread.getWeight());
        assertEquals(25.0, gingerbread.getSugarContent());
        assertEquals(40.0, gingerbread.getPrice());
        assertEquals("зірка", gingerbread.getShape());
        assertTrue(gingerbread.isIced());
    }

    @Test
    void testExecute_ExitWithoutAdding() {
        mockedInput.when(() -> UserInputHelper.promptInt("Ваш вибір -> ")).thenReturn(0);
        addNewSweetToBase.execute();
        mockedHelper.verify(() -> SweetBaseHelper.saveSweet(any()), never());
    }

    @Test
    void testExecute_AddCandy() {
        mockedInput.when(() -> UserInputHelper.promptInt("Ваш вибір -> "))
                .thenReturn(1, 0);
        mockedInput.when(() -> UserInputHelper.promptInt("Введіть код товару: "))
                .thenReturn(101);
        mockedInput.when(() -> UserInputHelper.promptString("Введіть назву: "))
                .thenReturn("Цукерка");
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть вагу(г): "))
                .thenReturn(200.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть відсоток цукру(%): "))
                .thenReturn(10.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть ціну(грн): "))
                .thenReturn(50.0);
        mockedInput.when(() -> UserInputHelper.promptString("Введіть начинку: "))
                .thenReturn("ваніль");
        mockedInput.when(UserInputHelper::promptCandyType)
                .thenReturn("шоколадна");

        addNewSweetToBase.execute();

        mockedHelper.verify(() -> SweetBaseHelper.saveSweet(argThat(candy ->
                candy.getSweetType().equals("candy") &&
                        candy.getCode() == 101 &&
                        candy.getName().equals("Цукерка") &&
                        candy.getWeight() == 200.0 &&
                        candy.getSugarContent() == 10.0 &&
                        candy.getPrice() == 50.0 &&
                        ((Candy) candy).getFilling().equals("ваніль") &&
                        ((Candy) candy).getType().equals("шоколадна")
        )), times(1));
    }

    @Test
    void testExecute_AddChocolate() {
        mockedInput.when(() -> UserInputHelper.promptInt("Ваш вибір -> "))
                .thenReturn(2, 0);
        mockedInput.when(() -> UserInputHelper.promptInt("Введіть код товару: "))
                .thenReturn(201);
        mockedInput.when(() -> UserInputHelper.promptString("Введіть назву: "))
                .thenReturn("Шоколад");
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть вагу(г): "))
                .thenReturn(150.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть відсоток цукру(%): "))
                .thenReturn(20.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть ціну(грн): "))
                .thenReturn(80.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть відсоток какао(%): "))
                .thenReturn(70.0);
        mockedInput.when(() -> UserInputHelper.promptString("Введіть начинку: "))
                .thenReturn("горіх");
        mockedInput.when(UserInputHelper::promptChocolateType)
                .thenReturn("молочний");

        addNewSweetToBase.execute();

        mockedHelper.verify(() -> SweetBaseHelper.saveSweet(argThat(chocolate ->
                chocolate.getSweetType().equals("chocolate") &&
                        chocolate.getCode() == 201 &&
                        chocolate.getName().equals("Шоколад") &&
                        chocolate.getWeight() == 150.0 &&
                        chocolate.getSugarContent() == 20.0 &&
                        chocolate.getPrice() == 80.0 &&
                        ((Chocolate) chocolate).getCocoaPercentage() == 70.0 &&
                        ((Chocolate) chocolate).getFilling().equals("горіх") &&
                        ((Chocolate) chocolate).getType().equals("молочний")
        )), times(1));
    }

    @Test
    void testExecute_AddJelly() {
        mockedInput.when(() -> UserInputHelper.promptInt("Ваш вибір -> "))
                .thenReturn(3, 0);
        mockedInput.when(() -> UserInputHelper.promptInt("Введіть код товару: "))
                .thenReturn(301);
        mockedInput.when(() -> UserInputHelper.promptString("Введіть назву: "))
                .thenReturn("Мармелад");
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть вагу(г): "))
                .thenReturn(120.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть відсоток цукру(%): "))
                .thenReturn(15.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть ціну(грн): "))
                .thenReturn(50.0);
        mockedInput.when(() -> UserInputHelper.promptString("Введіть фруктовий смак: "))
                .thenReturn("вишня");
        mockedInput.when(() -> UserInputHelper.promptString("Введіть форму: "))
                .thenReturn("серце");

        addNewSweetToBase.execute();

        mockedHelper.verify(() -> SweetBaseHelper.saveSweet(argThat(jelly ->
                jelly.getSweetType().equals("jelly") &&
                        jelly.getCode() == 301 &&
                        jelly.getName().equals("Мармелад") &&
                        jelly.getWeight() == 120.0 &&
                        jelly.getSugarContent() == 15.0 &&
                        jelly.getPrice() == 50.0 &&
                        ((Jelly) jelly).getFruityTaste().equals("вишня") &&
                        ((Jelly) jelly).getShape().equals("серце")
        )), times(1));
    }

    @Test
    void testExecute_AddGingerbread() {
        mockedInput.when(() -> UserInputHelper.promptInt("Ваш вибір -> "))
                .thenReturn(4, 0);
        mockedInput.when(() -> UserInputHelper.promptInt("Введіть код товару: "))
                .thenReturn(401);
        mockedInput.when(() -> UserInputHelper.promptString("Введіть назву: "))
                .thenReturn("Пряник");
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть вагу(г): "))
                .thenReturn(100.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть відсоток цукру(%): "))
                .thenReturn(25.0);
        mockedInput.when(() -> UserInputHelper.promptDouble("Введіть ціну(грн): "))
                .thenReturn(40.0);
        mockedInput.when(() -> UserInputHelper.promptString("Введіть форму: "))
                .thenReturn("зірка");
        mockedInput.when(() -> UserInputHelper.promptString("Чи глазурований пряник? (так/ні): "))
                .thenReturn("так");

        addNewSweetToBase.execute();

        mockedHelper.verify(() -> SweetBaseHelper.saveSweet(argThat(gingerbread ->
                gingerbread.getSweetType().equals("gingerbread") &&
                        gingerbread.getCode() == 401 &&
                        gingerbread.getName().equals("Пряник") &&
                        gingerbread.getWeight() == 100.0 &&
                        gingerbread.getSugarContent() == 25.0 &&
                        gingerbread.getPrice() == 40.0 &&
                        ((Gingerbread) gingerbread).getShape().equals("зірка") &&
                        ((Gingerbread) gingerbread).isIced()
        )), times(1));
    }
}
