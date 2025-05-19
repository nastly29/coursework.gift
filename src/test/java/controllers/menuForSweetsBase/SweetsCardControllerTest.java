package controllers.menuForSweetsBase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.concurrent.atomic.AtomicBoolean;
import domain.sweets.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import service.GiftService;
import utils.TableAction;
import domain.Gift;

public class SweetsCardControllerTest extends ApplicationTest {
    private Stage stage;

    @Start
    public void start(Stage stage) {
        this.stage = stage;
    }

    private Parent loadCard(Sweets sweet, GiftService giftService, TableAction... actions) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/menuForSweetsBase/SweetsCard.fxml")
        );
        loader.setControllerFactory(_ -> new SweetsCardController(sweet, giftService, actions));
        return loader.load();
    }

    private void showRoot(Parent root) {
        interact(() -> {
            stage.setScene(new Scene(root));
            stage.show();
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testCandyDisplaysBasicInfoAndDetail() throws Exception {
        Candy candy = new Candy(1, "CandyName", 12.5, 30.0, 8.0, "Honey", "Soft", new byte[0]);
        Parent root = loadCard(candy, null);
        showRoot(root);

        Label nameLbl = lookup("#nameLbl").query();
        Label typeLbl = lookup("#typeLbl").query();
        Label priceLbl = lookup("#priceLbl").query();
        Label weightLbl = lookup("#weightLbl").query();
        Label sugarLbl = lookup("#sugarLbl").query();
        Label detailLbl = lookup("#detailLbl").query();
        Label qtyLbl = lookup("#qtyLbl").query();
        HBox actionsBox = lookup("#actionsBox").query();

        assertEquals("CandyName", nameLbl.getText());
        assertEquals("candy", typeLbl.getText());
        assertEquals("Ціна: 8.00 грн", priceLbl.getText());
        assertEquals("Вага: 12.50 г", weightLbl.getText());
        assertEquals("Цукор: 30.00%", sugarLbl.getText());
        assertEquals("Начинка: Honey\nВид цукерки: Soft", detailLbl.getText());
        assertFalse(qtyLbl.isVisible(), "Quantity label should be hidden when no GiftService");
        assertTrue(actionsBox.getChildren().isEmpty(), "No actions provided => no buttons");
    }

    @Test
    public void testChocolateDisplaysBasicInfoAndDetail() throws Exception {
        Chocolate choco = new Chocolate(
                10, "ChocoName",
                50.0,
                100.0,
                20.0,
                70.0,
                "Caramel",
                "Dark",
                new byte[0]
        );
        Parent root = loadCard(choco, null);
        showRoot(root);

        Label nameLbl   = lookup("#nameLbl").query();
        Label typeLbl   = lookup("#typeLbl").query();
        Label priceLbl  = lookup("#priceLbl").query();
        Label weightLbl = lookup("#weightLbl").query();
        Label sugarLbl  = lookup("#sugarLbl").query();
        Label detailLbl = lookup("#detailLbl").query();
        Label qtyLbl    = lookup("#qtyLbl").query();
        HBox actionsBox = lookup("#actionsBox").query();

        assertEquals("ChocoName", nameLbl.getText());
        assertEquals("chocolate", typeLbl.getText());
        assertEquals("Ціна: 20.00 грн",   priceLbl.getText());
        assertEquals("Вага: 50.00 г",    weightLbl.getText());
        assertEquals("Цукор: 100.00%",     sugarLbl.getText());
        assertEquals(
                "% какао: 70\n" +
                        "Начинка: Caramel\n" +
                        "Вид шоколаду: Dark",
                detailLbl.getText()
        );
        assertFalse(qtyLbl.isVisible());
        assertTrue(actionsBox.getChildren().isEmpty());
    }

    @Test
    public void testJellyDisplaysBasicInfoAndDetail() throws Exception {
        Jelly jelly = new Jelly(
                20, "JellyName",
                25.0,
                200.0,
                5.0,
                "Lemon",
                "Star",
                new byte[0]
        );
        Parent root = loadCard(jelly, null);
        showRoot(root);

        Label nameLbl   = lookup("#nameLbl").query();
        Label typeLbl   = lookup("#typeLbl").query();
        Label priceLbl  = lookup("#priceLbl").query();
        Label weightLbl = lookup("#weightLbl").query();
        Label sugarLbl  = lookup("#sugarLbl").query();
        Label detailLbl = lookup("#detailLbl").query();
        Label qtyLbl    = lookup("#qtyLbl").query();
        HBox actionsBox = lookup("#actionsBox").query();

        assertEquals("JellyName", nameLbl.getText());
        assertEquals("jelly",    typeLbl.getText());
        assertEquals("Ціна: 5.00 грн",    priceLbl.getText());
        assertEquals("Вага: 25.00 г",   weightLbl.getText());
        assertEquals("Цукор: 200.00%",    sugarLbl.getText());
        assertEquals(
                "Смак: Lemon\n" +
                        "Форма: Star",
                detailLbl.getText()
        );
        assertFalse(qtyLbl.isVisible());
        assertTrue(actionsBox.getChildren().isEmpty());
    }

    @Test
    public void testGingerbreadDisplaysBasicInfoAndDetail() throws Exception {
        Gingerbread g = new Gingerbread(
                30, "GingerName",
                10.0,
                150.0,
                7.5,
                "Heart",
                true,
                new byte[0]
        );
        Parent root = loadCard(g, null);
        showRoot(root);

        Label nameLbl   = lookup("#nameLbl").query();
        Label typeLbl   = lookup("#typeLbl").query();
        Label priceLbl  = lookup("#priceLbl").query();
        Label weightLbl = lookup("#weightLbl").query();
        Label sugarLbl  = lookup("#sugarLbl").query();
        Label detailLbl = lookup("#detailLbl").query();
        Label qtyLbl    = lookup("#qtyLbl").query();
        HBox actionsBox = lookup("#actionsBox").query();

        assertEquals("GingerName", nameLbl.getText());
        assertEquals("gingerbread", typeLbl.getText());
        assertEquals("Ціна: 7.50 грн",    priceLbl.getText());
        assertEquals("Вага: 10.00 г",   weightLbl.getText());
        assertEquals("Цукор: 150.00%",    sugarLbl.getText());
        assertEquals(
                "Форма: Heart\n" +
                        "Глазурований: так",
                detailLbl.getText()
        );
        assertFalse(qtyLbl.isVisible());
        assertTrue(actionsBox.getChildren().isEmpty());
    }

    @Test
    public void testImageViewLoadsImageWhenDataPresent() throws Exception {
        byte[] imgData = { (byte)137, (byte)80, (byte)78, (byte)71 };
        Candy candy = new Candy(1, "ImgCandy", 10.0, 20.0, 5.0, "F", "T", imgData);
        Parent root = loadCard(candy, null);
        showRoot(root);

        ImageView imageView = lookup("#imageView").query();
        Image img = imageView.getImage();
        assertNotNull(img, "ImageView should have image set");
    }

    @Test
    public void testActionsFireHandlers() throws Exception {
        Candy candy = new Candy(2, "ActionCandy", 5.0, 10.0, 3.0, "F", "T", new byte[0]);
        AtomicBoolean handlerCalled = new AtomicBoolean(false);
        TableAction action = new TableAction("DoIt", sw -> {
            assertSame(candy, sw);
            handlerCalled.set(true);
        });
        Parent root = loadCard(candy, null, action);
        showRoot(root);

        Button btn = lookup("DoIt").queryButton();
        clickOn(btn);
        assertTrue(handlerCalled.get(), "Action handler should be called on button click");
    }

    @Test
    public void testQuantityLabelAndAddLabelForGiftService() throws Exception {
        Candy candy = new Candy(3, "QtyCandy", 8.0, 15.0, 2.0, "F", "T", new byte[0]);

        GiftService gs = mock(GiftService.class);
        when(gs.getQuantity(candy)).thenReturn(1);
        Gift gift = new Gift();
        gift.getSweets().add(candy);
        when(gs.getGift()).thenReturn(gift);

        Parent root = loadCard(candy, gs,
                new TableAction("Додати до подарунка", _ -> {})
        );
        showRoot(root);

        Label qtyLbl = lookup("#qtyLbl").query();
        assertTrue(qtyLbl.isVisible());
        assertEquals("Кількість: 1", qtyLbl.getText());
        Label addedLbl = lookup(l -> l instanceof Label && "Додано".equals(((Label)l).getText())).query();
        assertNotNull(addedLbl);
    }
}