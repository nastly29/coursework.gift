package controllers;

import domain.Gift;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import service.GiftService;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class GiftTabControllerDisTest {

    private GiftTabController controller;

    @Start
    private void start(Stage stage) {
        controller = new GiftTabController(new GiftService(new Gift()));

        controller.btnAddSweet = new Button();
        controller.btnDisplay = new Button();
        controller.btnSearchSugar = new Button();
        controller.btnSortWeight = new Button();
        controller.btnFinish = new Button();

        VBox root = new VBox(
                controller.btnAddSweet,
                controller.btnDisplay,
                controller.btnSearchSugar,
                controller.btnSortWeight,
                controller.btnFinish
        );
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testEnableGiftActions() {
        Platform.runLater(() -> {
            controller.btnAddSweet.setDisable(true);
            controller.btnDisplay.setDisable(true);
            controller.btnSearchSugar.setDisable(true);
            controller.btnSortWeight.setDisable(true);
            controller.btnFinish.setDisable(true);

            controller.enableGiftActions();

            assertFalse(controller.btnAddSweet.isDisabled());
            assertFalse(controller.btnDisplay.isDisabled());
            assertFalse(controller.btnSearchSugar.isDisabled());
            assertFalse(controller.btnSortWeight.isDisabled());
            assertFalse(controller.btnFinish.isDisabled());
        });
    }
}