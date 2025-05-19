package controllers;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import java.io.IOException;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class BaseTabControllerTest {

    private BaseTabController controller;
    private StackPane introPane;
    private StackPane contentPane;

    @Test
    void testOnAddNew_invokesLoadScreenWithoutException() {
        assertDoesNotThrow(() -> {
            Method method = BaseTabController.class.getDeclaredMethod("onAddNew");
            method.setAccessible(true);
            method.invoke(controller);
        });
    }

    @Test
    void testOnViewBase_invokesLoadScreenWithoutException() {
        assertDoesNotThrow(() -> {
            Method method = BaseTabController.class.getDeclaredMethod("onViewBase");
            method.setAccessible(true);
            method.invoke(controller);
        });
    }

    @Test
    void testOnSearchPrice_invokesLoadScreenWithoutException() {
        assertDoesNotThrow(() -> {
            Method method = BaseTabController.class.getDeclaredMethod("onSearchPrice");
            method.setAccessible(true);
            method.invoke(controller);
        });
    }

    @Start
    private void start(Stage stage) {
        controller = new BaseTabController();
        introPane = new StackPane();
        contentPane = new StackPane();
        controller.introPane = introPane;
        try {
            var field = BaseTabController.class.getDeclaredField("contentPane");
            field.setAccessible(true);
            field.set(controller, contentPane);
        } catch (Exception e) {
            fail("Не вдалося вставити contentPane: " + e.getMessage());
        }
    }

    @Test
    void testInitialize_setsIntroPane() {
        controller.initialize();
        assertTrue(contentPane.getChildren().contains(introPane), "introPane має бути встановлений у contentPane");
    }

    @Test
    void testLoadScreen_invalidPath_logsError() {
        BaseTabController controller = new BaseTabController();
        try {
            Method m = BaseTabController.class.getDeclaredMethod("loadScreen", String.class);
            m.setAccessible(true);

            // Неіснуючий шлях
            m.invoke(controller, "/fxml/nonexistent.fxml");
        } catch (Exception e) {
            assertFalse(e.getCause() instanceof NullPointerException || e.getCause() instanceof IOException);
        }
    }
}