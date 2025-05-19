package controllers.menuForGift;

import controllers.SweetsViewFactory;
import domain.sweets.Candy;
import domain.sweets.Sweets;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import service.GiftService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class SortSweetByWeightInGiftTest extends ApplicationTest {

    private GiftService giftService;
    private MockedStatic<SweetsViewFactory> sf;

    @BeforeEach
    void mockFactory() {
        // Підміна фабрики на весь час тесту
        sf = Mockito.mockStatic(SweetsViewFactory.class);
        sf.when(() -> SweetsViewFactory.createCard(
                any(Sweets.class),
                any(GiftService.class)
        )).thenAnswer(invocation -> new VBox(new Label("CARD")));
    }

    @AfterEach
    void cleanupFactory() {
        sf.close();
    }

    @Start
    public void start(Stage stage) throws Exception {
        // Мок GiftService
        giftService = mock(GiftService.class);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/menuForGift/SortSweetByWeightInGift.fxml")
        );
        loader.setControllerFactory(c -> new SortSweetByWeightInGift(giftService));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void initialize_scrollPaneHiddenAndFlowEmpty() {
        // після initialize() scrollPane має бути прихованим, flowPane — порожнім
        verifyThat("#scrollPane", isInvisible());
        FlowPane flow = lookup("#flowPane").query();
        assertTrue(flow.getChildren().isEmpty(), "flowPane має бути порожнім після initialize");
    }

    @Test
    void onSort_withAscOrder_populatesCardsAndShowsPane() {
        Candy c1 = new Candy(1, "A", 10.0, 5.0, 2.0, "T1", "Type", null);
        Candy c2 = new Candy(2, "B", 20.0, 8.0, 3.0, "T2", "Type", null);
        when(giftService.getSweetsSortedByWeight(true))
                .thenReturn(List.of(c1, c2));

        interact(() -> lookup("#ascBtn").queryAs(RadioButton.class).setSelected(true));

        clickOn("#sortBtn");
        waitForFxEvents();

        verifyThat("#scrollPane", isVisible());
        FlowPane flow = lookup("#flowPane").query();
        assertEquals(2, flow.getChildren().size(),
                "має бути дві карточки при asc=true");
    }


    @Test
    void onSort_withDescOrder_passesFalseToService() {
        // 1) Заглушимо сервіс
        Candy c3 = new Candy(3, "C", 15.0, 6.0, 1.0, "T3", "Type", null);
        when(giftService.getSweetsSortedByWeight(false))
                .thenReturn(List.of(c3));

        // 2) Переконаємося, що ascBtn не вибраний
        RadioButton asc = lookup("#ascBtn").query();
        interact(() -> asc.setSelected(false));

        // 3) Клікаємо по кнопці сортування
        clickOn("#sortBtn");
        waitForFxEvents();  // чекаємо оновлення UI

        // 4) Після сортування scrollPane має стати видимим
        verifyThat("#scrollPane", isVisible());

        // 5) А в flowPane — одна карточка
        FlowPane flow = lookup("#flowPane").query();
        assertEquals(1, flow.getChildren().size(),
                "має бути одна карточка при asc=false");

        // 6) Перевіряємо, що сервіс дійсно отримав false
        verify(giftService, times(1)).getSweetsSortedByWeight(false);
    }

}
