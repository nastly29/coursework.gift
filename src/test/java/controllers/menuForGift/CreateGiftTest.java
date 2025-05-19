package controllers.menuForGift;

import domain.Package;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import service.GiftService;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;

public class CreateGiftTest extends ApplicationTest {

    private static GiftService giftService;

    @Override
    public void start(Stage stage) throws Exception {
        giftService = mock(GiftService.class);
        when(giftService.exists()).thenReturn(false);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/menuForGift/CreateGift.fxml")
        );
        loader.setControllerFactory(_ -> new CreateGift(giftService));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void createAndSaveButtons_initialState_beforeCreate() {
        verifyThat("#createBtn", isVisible());
        verifyThat("#createBtn", isEnabled());
        verifyThat("#saveBtn",   isVisible());
        verifyThat("#saveBtn",   isDisabled());
    }

    @Test
    void onCreate_shouldCallServiceAndToggleButtons() {
        clickOn("#nameField").write("MyTestGift");
        clickOn("#messageField").write("Hello!");

        ComboBox<String> box    = lookup("#boxColorCB").query();
        ComboBox<String> ribbon = lookup("#ribbonColorCB").query();

        interact(() -> {
            box.setValue("Red");
            ribbon.setValue("Blue");
        });

        clickOn("#createBtn");
        clickOn("OK");
        verifyThat("#createBtn", isDisabled());
        verifyThat("#saveBtn",   isEnabled());
    }

    @Test
    void onCreate_whenValidationFails_showsErrorAndDoesNothing() {
        clickOn("#createBtn");
        verifyThat(".alert", isVisible());
        verifyThat("Заповніть усі поля!", isVisible());
        clickOn("OK");
        verify(giftService, never())
                .createOrUpdateGift(anyString(), any(Package.class));
        verifyThat("#createBtn", isEnabled());
        verifyThat("#saveBtn",   isDisabled());
    }
}