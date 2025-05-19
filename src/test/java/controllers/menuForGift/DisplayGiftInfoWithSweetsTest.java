package controllers.menuForGift;

import domain.Gift;
import domain.Package;
import domain.sweets.Candy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import service.GiftService;
import controllers.SweetsViewFactory;
import utils.TableAction;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

public class DisplayGiftInfoWithSweetsTest extends ApplicationTest {

    private static MockedStatic<SweetsViewFactory> sf;
    private GiftService giftService;
    private Candy candy;

    @BeforeAll
    static void mockFactoryOnce() {
        sf = Mockito.mockStatic(SweetsViewFactory.class);
        sf.when(() -> SweetsViewFactory.createCard(
                        any(), any(),
                        any(TableAction.class),
                        any(TableAction.class),
                        any(TableAction.class)
                ))
                .thenAnswer(inv -> {
                    @SuppressWarnings("unchecked")
                    TableAction dec = inv.getArgument(2);
                    TableAction del = inv.getArgument(3);
                    TableAction add = inv.getArgument(4);

                    Button minus  = new Button(dec.getName());
                    minus.setOnAction(_ -> dec.getHandler().accept((Candy)((List<?>)inv.getMock()).get(0)));
                    Button remove = new Button(del.getName());
                    remove.setOnAction(_ -> del.getHandler().accept((Candy)((List<?>)inv.getMock()).get(0)));
                    Button plus   = new Button(add.getName());
                    plus.setOnAction(_ -> add.getHandler().accept((Candy)((List<?>)inv.getMock()).get(0)));
                    return new VBox(minus, remove, plus);
                });
    }

    @AfterAll
    static void unmockFactory() {
        sf.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        giftService = mock(GiftService.class);
        when(giftService.exists()).thenReturn(true);

        Gift gift = new Gift();
        gift.setName("MyGift");
        gift.setPack(new Package("Red", "Blue", "Hello!"));
        when(giftService.getGift()).thenReturn(gift);

        candy = new Candy(1, "TestCandy", 50.0, 20.0, 5.0, "Caramel", "Soft", null);
        when(giftService.listSweets()).thenReturn(List.of(candy));

        when(giftService.totalPrice()).thenReturn(50.00);
        when(giftService.totalWeight()).thenReturn(20.00);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/menuForGift/DisplayGiftInfo.fxml")
        );
        loader.setControllerFactory(_ -> new DisplayGiftInfo(giftService));
        Parent root = loader.load();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    @Test
    void showsAllDetailsAndPopulatesCards() {
        verifyThat("#infoLabel",    hasText(""));
        verifyThat("#titleLabel",   hasText("Подарунок: MyGift"));
        verifyThat("#boxColorLabel",   hasText("Колір коробки: Red"));
        verifyThat("#ribbonColorLabel",hasText("Колір стрічки: Blue"));
        verifyThat("#messageLabel", hasText("Повідомлення: Hello!"));

        verifyThat("#scrollPane",  isVisible());
        verifyThat("#totalsLabel", isVisible());
        verifyThat("#totalsLabel",
                hasText("Загальна ціна: 50,00 грн    Загальна вага: 20,00 г")
        );

        FlowPane flow = lookup("#flowPane").query();
        assertEquals(1, flow.getChildren().size());
    }

    @Test
    void actionButtons_fireCorrectServiceCalls() {
        WaitForAsyncUtils.waitForFxEvents();

        // переконаємося, що картка в UI взагалі є
        FlowPane flow = lookup("#flowPane").query();
        assertEquals(1, flow.getChildren().size());

        // мінус
        Button btnMinus  = lookup("−").queryButton();
        moveTo(btnMinus);
        clickOn(btnMinus);
        verify(giftService, times(1)).decreaseSweet(candy);

        // видалити
        Button btnRemove = lookup("Видалити").queryButton();
        moveTo(btnRemove);
        clickOn(btnRemove);
        verify(giftService, times(1)).removeSweet(candy);

        // плюс
        Button btnPlus   = lookup("+").queryButton();
        moveTo(btnPlus);
        clickOn(btnPlus);
        verify(giftService, times(1)).addSweetToGift(candy);
    }
}