package main;

import controllers.BaseTabController;
import controllers.GiftTabController;
import domain.Gift;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import service.GiftService;
import static org.junit.jupiter.api.Assertions.*;

public class MainAppTest extends ApplicationTest {

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        new MainApp().start(stage);
    }

    @Test
    public void testAppLaunchesSuccessfully() {
        assertNotNull(stage);
        assertEquals("Система подарунків", stage.getTitle());

        Scene scene = stage.getScene();
        assertNotNull(scene);

        assertTrue(scene.getRoot() instanceof javafx.scene.layout.BorderPane);

        javafx.scene.layout.BorderPane borderPane = (javafx.scene.layout.BorderPane) scene.getRoot();
        javafx.scene.control.TabPane tabPane = (javafx.scene.control.TabPane) borderPane.getCenter();
        assertNotNull(tabPane);
        assertEquals(2, tabPane.getTabs().size());

        assertEquals("Подарунок", tabPane.getTabs().get(0).getText());
        assertEquals("База солодощів", tabPane.getTabs().get(1).getText());
    }


    @Test
    public void testGiftTabControllerCanBeConstructed() {
        assertDoesNotThrow(() -> new GiftTabController(new GiftService(new Gift())));
    }

    @Test
    public void testBaseTabControllerInstantiation() {
        BaseTabController controller = new BaseTabController();
        assertNotNull(controller);
    }

    @Test
    void testMissingFXML_throwsException() {
        MainApp app = new MainApp() {
            @Override
            public void start(Stage stage) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DoesNotExist.fxml"));
                assertThrows(Exception.class, loader::load);
            }
        };

        assertThrows(Exception.class, () -> app.start(new Stage()));
    }

    @Test
    void testUnknownController_throwsIllegalState() {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(cls -> {
            if (cls.equals(String.class)) {
                return "invalid";
            }
            throw new IllegalStateException("Невідомий контролер: " + cls);
        });
        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> loader.getControllerFactory().call(Double.class));
        assertTrue(ex.getMessage().contains("Невідомий контролер"));
    }
}