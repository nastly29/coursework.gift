package controllers.menuForGift;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import service.GiftService;
import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.util.NodeQueryUtils.hasText;

public class DisplayGiftInfoEmptySweetsTest extends ApplicationTest {

    private GiftService giftService;

    @Override
    public void start(Stage stage) throws Exception {
        giftService = mock(GiftService.class);
        when(giftService.exists()).thenReturn(true);
        when(giftService.listSweets()).thenReturn(Collections.emptyList());
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/menuForGift/DisplayGiftInfo.fxml")
        );
        loader.setControllerFactory(_ -> new DisplayGiftInfo(giftService));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void showsEmptyGiftMessageAndHidesContent() {
        verifyThat("#infoLabel", hasText("Подарунок порожній — додайте солодощі!"));
        verifyThat("#titleLabel",     isInvisible());
        verifyThat("#boxColorLabel",  isInvisible());
        verifyThat("#ribbonColorLabel", isInvisible());
        verifyThat("#messageLabel",   isInvisible());
        verifyThat("#scrollPane",     isInvisible());
        verifyThat("#totalsLabel",    isInvisible());
    }
}