package controllers.menuForGift;

import domain.Gift;
import domain.Package;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.testfx.framework.junit5.ApplicationTest;
import service.GiftService;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;

public class CreateGiftExistsTest extends ApplicationTest {
    private GiftService giftService;

    @Override
    public void start(Stage stage) throws Exception {
        giftService = mock(GiftService.class);
        Package pack = new Package("Green","Yellow","Привіт!");
        Gift g = new Gift();
        g.setName("MyExistingGift");
        g.setPack(pack);
        when(giftService.exists()).thenReturn(true);
        when(giftService.getGift()).thenReturn(g);
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/menuForGift/CreateGift.fxml")
        );
        loader.setControllerFactory(_ -> new CreateGift(giftService));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void prefilledForm_onStartup() {
        verifyThat("#nameField",    hasText("MyExistingGift"));
        verifyThat("#messageField", hasText("Привіт!"));
        ComboBox<String> box    = lookup("#boxColorCB").query();
        ComboBox<String> ribbon = lookup("#ribbonColorCB").query();
        assertEquals("Green",  box.getValue());
        assertEquals("Yellow", ribbon.getValue());
        verifyThat("#createBtn", isDisabled());
        verifyThat("#saveBtn",   isEnabled());
    }

    @Test
    void onSave_shouldCallServiceAndShowAlert() {
        clickOn("#nameField").eraseText(16).write("UpdatedName");
        clickOn("#messageField").eraseText(7).write("UpdatedMsg");

        ComboBox<String> box    = lookup("#boxColorCB").query();
        ComboBox<String> ribbon = lookup("#ribbonColorCB").query();
        interact(() -> {
            box.setValue("Orange");
            ribbon.setValue("Black");
        });

        clickOn("#saveBtn");
        clickOn("OK");

        ArgumentCaptor<Package> capt = ArgumentCaptor.forClass(Package.class);
        verify(giftService, times(1))
                .createOrUpdateGift(eq("UpdatedName"), capt.capture());

        Package pkg = capt.getValue();
        assertEquals("Orange", pkg.getBoxColor());
        assertEquals("Black",  pkg.getRibbonColor());
        assertEquals("UpdatedMsg", pkg.getMessage());

        verifyThat("#createBtn", isDisabled());
        verifyThat("#saveBtn",   isEnabled());
    }
}