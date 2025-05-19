package controllers.menuForSweetsBase;

import domain.sweets.Sweets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.SweetsRepository;
import controllers.SweetsViewFactory;
import utils.TableAction;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FindSweetByPriceTest extends ApplicationTest {

    private FindSweetByPrice controller;
    private FlowPane flowPane;
    private ScrollPane scrollPane;
    private static MockedStatic<SweetsRepository> repoMock;
    private static MockedStatic<SweetsViewFactory> factoryMock;

    @BeforeAll
    public static void initMocks() {
        repoMock = mockStatic(SweetsRepository.class);
        factoryMock = mockStatic(SweetsViewFactory.class);

        factoryMock.when(() -> SweetsViewFactory.createCard(any(Sweets.class), any(), any(TableAction.class)))
                .thenAnswer(inv -> {
                    Sweets s = inv.getArgument(0);
                    TableAction action = inv.getArgument(2);
                    VBox box = new VBox();
                    box.setId("mock-card");
                    Button deleteBtn = new Button("Видалити");
                    deleteBtn.setOnAction(e -> action.getHandler().accept(s));
                    box.getChildren().add(deleteBtn);
                    return box;
                });
    }

    @AfterAll
    public static void tearDownAll() {
        repoMock.close();
        factoryMock.close();
    }

    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuForSweetsBase/FindSweetByPrice.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        flowPane = (FlowPane) loader.getNamespace().get("flowPane");
        scrollPane = (ScrollPane) loader.getNamespace().get("scrollPane");

        stage.setScene(new Scene(root));
        stage.show();
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void testResultShowsNone() {
        Sweets dummySweet = mock(Sweets.class);

        repoMock.when(() -> SweetsRepository.findSweetsByPrice(1.0, 2.0))
                .thenReturn(List.of(dummySweet));

        runOnFxThread(() -> controller.onSearch(1.0, 2.0));
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals(1, flowPane.getChildren().size());
        assertTrue(flowPane.getChildren().get(0) instanceof Label);
    }

    @Test
    public void testInvalidInputShowsAlert() {
        runOnFxThread(() -> {
            controller.minField.setText("abc");
            controller.maxField.setText("10.0");
            controller.onSearch();
        });

        assertEquals(0, flowPane.getChildren().size());
    }


    private void runOnFxThread(Runnable action) {
        javafx.application.Platform.runLater(action);
        WaitForAsyncUtils.waitForFxEvents();
    }
}