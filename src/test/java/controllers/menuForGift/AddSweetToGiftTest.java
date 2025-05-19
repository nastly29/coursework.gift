package controllers.menuForGift;

import domain.Gift;
import domain.Package;
import domain.sweets.Candy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import repository.SweetsRepository;
import service.GiftService;
import utils.TableAction;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class AddSweetToGiftTest extends ApplicationTest {

    private GiftService giftService;

    @Override
    public void start(Stage stage) throws Exception {
        giftService = new GiftService(new Gift());
        giftService.createOrUpdateGift("MyGift", new Package("Red", "Blue", "Hi"));
        Candy testCandy = new Candy(1, "Test Candy", 100, 25, 10, "Caramel", "Soft", null);
        try (MockedStatic<SweetsRepository> mockedRepo = Mockito.mockStatic(SweetsRepository.class)) {
            mockedRepo.when(SweetsRepository::loadSweetsFromDb).thenReturn(List.of(testCandy));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuForGift/AddSweetToGift.fxml"));
            loader.setControllerFactory(_ -> new AddSweetToGift(giftService));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @Test
    void testAddToGiftButtonAppears() {
        verifyThat(".button", isVisible());
    }

    @Test
    void testInitialize_emptyDatabase_showsNoSweetsMessage() {
        GiftService service = mock(GiftService.class);
        when(service.exists()).thenReturn(true);

        try (MockedStatic<SweetsRepository> mocked = Mockito.mockStatic(SweetsRepository.class)) {
            mocked.when(SweetsRepository::loadSweetsFromDb).thenReturn(List.of());

            AddSweetToGift controller = new AddSweetToGift(service);
            controller.infoLabel = new Label();
            controller.scrollPane = new ScrollPane();
            controller.flowPane = new FlowPane();
            controller.initialize(null, null);
            assertEquals("У базі немає солодощів.", controller.infoLabel.getText());
            assertFalse(controller.scrollPane.isVisible());
        }
    }

    @Test
    void testAddSweetButton_addsSweetToGift() {
        GiftService service = mock(GiftService.class);
        when(service.exists()).thenReturn(true);
        Candy sweet = new Candy(1, "Test", 100, 20, 10, "Caramel", "Soft", null);
        try (MockedStatic<SweetsRepository> mocked = Mockito.mockStatic(SweetsRepository.class)) {
            mocked.when(SweetsRepository::loadSweetsFromDb).thenReturn(List.of(sweet));

            AddSweetToGift controller = new AddSweetToGift(service);
            controller.infoLabel = new Label();
            controller.scrollPane = new ScrollPane();
            controller.flowPane = new FlowPane();

            controller.initialize(null, null);

            TableAction action = new TableAction("Додати до подарунка", service::addSweetToGift);
            action.getHandler().accept(sweet);
            verify(service).addSweetToGift(sweet);
        }
    }
}
