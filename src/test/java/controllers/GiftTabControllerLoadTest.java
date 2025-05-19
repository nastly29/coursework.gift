package controllers;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import service.GiftService;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class GiftTabControllerLoadTest {

    private GiftTabController controller;

    @BeforeEach
    public void setup() {
        GiftService giftService = new GiftService(new domain.Gift());
        controller = new GiftTabController(giftService);
        controller.introPane = new StackPane();
        controller.contentPane = new StackPane();
    }

    @Test
    public void testLoadCreateGift() {
        Platform.runLater(controller::onParams);
        WaitForAsyncUtils.waitForFxEvents();
        assertContentPaneNotEmpty("CreateGift");
    }

    @Test
    public void testLoadAddSweetToGift() {
        Platform.runLater(controller::onAddSweet);
        WaitForAsyncUtils.waitForFxEvents();
        assertContentPaneNotEmpty("AddSweetToGift");
    }

    @Test
    public void testLoadDisplayGiftInfo() {
        Platform.runLater(controller::onDisplay);
        WaitForAsyncUtils.waitForFxEvents();
        assertContentPaneNotEmpty("DisplayGiftInfo");
    }

    @Test
    public void testLoadFindSweetBySugarInGift() {
        Platform.runLater(controller::onSearchSugar);
        WaitForAsyncUtils.waitForFxEvents();
        assertContentPaneNotEmpty("FindSweetBySugarInGift");
    }

    @Test
    public void testLoadSortSweetByWeightInGift() {
        Platform.runLater(controller::onSortWeight);
        WaitForAsyncUtils.waitForFxEvents();
        assertContentPaneNotEmpty("SortSweetByWeightInGift");
    }

    private void assertContentPaneNotEmpty(String fxmlName) {
        assertFalse(controller.contentPane.getChildren().isEmpty(),
                fxmlName + " повинен був бути завантажений у contentPane");
    }
}