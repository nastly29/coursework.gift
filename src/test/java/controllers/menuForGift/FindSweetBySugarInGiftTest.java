package controllers.menuForGift;

import domain.sweets.Candy;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import service.GiftService;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class FindSweetBySugarInGiftTest {

    private FindSweetBySugarInGift controller;
    private GiftService mockService;
    private TextField minField;
    private TextField maxField;
    private FlowPane flowPane;
    private ScrollPane resultPane;

    @Start
    private void start(Stage stage) {
        mockService = mock(GiftService.class);
        controller = new FindSweetBySugarInGift(mockService);

        minField = new TextField();
        maxField = new TextField();
        flowPane = new FlowPane();
        resultPane = new ScrollPane(flowPane);
        Button searchBtn = new Button("Пошук");
        searchBtn.setId("searchBtn");

        controller.minField = minField;
        controller.maxField = maxField;
        controller.flowPane = flowPane;
        controller.resultPane = resultPane;
        controller.searchBtn = searchBtn;

        searchBtn.setOnAction(e -> controller.onSearch());

        VBox root = new VBox(minField, maxField, searchBtn, resultPane);
        stage.setScene(new Scene(root, 400, 300));
        stage.show();
    }

    @BeforeEach
    void setup() {
        Platform.runLater(() -> controller.initialize());
    }

    @Test
    void testSearch_withValidRange_findsSweets(FxRobot robot) {
        Candy c = new Candy(0, "Candy", 12.0, 0.5, 20.0, "Caramel", "HARD", null);
        when(mockService.findBySugar(10.0, 30.0)).thenReturn(List.of(c));

        Platform.runLater(() -> {
            minField.setText("10.0");
            maxField.setText("30.0");
        });

        robot.clickOn("#searchBtn");

        assertTrue(resultPane.isVisible());
        assertFalse(flowPane.getChildren().isEmpty());
    }

    @Test
    void testSearch_withInvalidNumbers_showsError(FxRobot robot) {
        Platform.runLater(() -> {
            minField.setText("abc");
            maxField.setText("xyz");
        });

        robot.clickOn("#searchBtn");
    }

    @Test
    void testSearch_noResults_showsLabel(FxRobot robot) throws Exception {
        when(mockService.findBySugar(10.0, 11.0)).thenReturn(Collections.emptyList());

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            minField.setText("10.0");
            maxField.setText("11.0");
            latch.countDown();
        });
        latch.await();

        robot.clickOn("#searchBtn");

        Platform.runLater(() -> {
            assertEquals(1, flowPane.getChildren().size());
            assertTrue(flowPane.getChildren().get(0) instanceof Label);
        });
    }

}
