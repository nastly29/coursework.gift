package controllers.menuForSweetsBase;

import domain.types.CandyType;
import domain.types.ChocolateType;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import repository.SweetsRepository;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class OnSaveTest {

    private AddNewSweetToBase controller;
    private DialogPane dialogPane;
    private MockedStatic<SweetsRepository> sweetsRepoMock;

    @BeforeEach
    void mockRepository() {
        if (sweetsRepoMock != null) {
            sweetsRepoMock.close();
        }
        sweetsRepoMock = mockStatic(SweetsRepository.class);
        sweetsRepoMock.when(() -> SweetsRepository.saveSweetToDb(any())).thenReturn(true);
    }

    @AfterEach
    void releaseMock() {
        if (sweetsRepoMock != null) {
            sweetsRepoMock.close();
        }
    }

    @Start
    private void start(Stage stage) {
        controller = new AddNewSweetToBase();
        controller.nameField = new TextField();
        controller.weightField = new TextField();
        controller.sugarField = new TextField();
        controller.priceField = new TextField();
        controller.typeCB = new ComboBox<>();
        controller.imgLabel = new Label();
        controller.detailBox = new VBox();
        controller.saveButtonType = ButtonType.OK;

        dialogPane = new DialogPane();
        controller.dialogPane = dialogPane;

        Button saveButton = new Button("Зберегти");
        saveButton.setId("saveButton");
        saveButton.setOnAction(_ -> {
            try {
                var m = AddNewSweetToBase.class.getDeclaredMethod("onSave");
                m.setAccessible(true);
                m.invoke(controller);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox root = new VBox(
                controller.nameField,
                controller.weightField,
                controller.sugarField,
                controller.priceField,
                controller.typeCB,
                controller.detailBox,
                controller.imgLabel,
                saveButton
        );
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }

    @Test
    void testOnSave_successCandy(FxRobot robot) {
        Platform.runLater(() -> {
            controller.nameField.setText("CandyName");
            controller.weightField.setText("10.5");
            controller.sugarField.setText("25");
            controller.priceField.setText("5.5");
            controller.typeCB.getItems().add("Candy");
            controller.typeCB.setValue("Candy");

            TextField filling = new TextField("caramel");
            ComboBox<CandyType> typeBox = new ComboBox<>();
            typeBox.getItems().add(CandyType.CHOCOLATE);
            typeBox.setValue(CandyType.CHOCOLATE);

            controller.detailBox.getChildren().addAll(new Label(), filling, new Label(), typeBox);
        });
        robot.clickOn("#saveButton");
        robot.clickOn("OK");
    }

    @Test
    void testOnSave_successChocolate(FxRobot robot) {
        Platform.runLater(() -> {
            controller.nameField.setText("ChocoName");
            controller.weightField.setText("12.0");
            controller.sugarField.setText("30");
            controller.priceField.setText("6.5");
            controller.typeCB.getItems().add("Chocolate");
            controller.typeCB.setValue("Chocolate");

            TextField cocoa = new TextField("75.0");
            TextField filling = new TextField("hazelnut");
            ComboBox<ChocolateType> chType = new ComboBox<>();
            chType.getItems().add(ChocolateType.DARK);
            chType.setValue(ChocolateType.DARK);

            controller.detailBox.getChildren().addAll(
                    new Label(), cocoa,
                    new Label(), filling,
                    new Label(), chType
            );
        });
        robot.clickOn("#saveButton");
        robot.clickOn("OK");
    }

    @Test
    void testOnSave_successJelly(FxRobot robot) {
        Platform.runLater(() -> {
            controller.nameField.setText("JellyName");
            controller.weightField.setText("8.0");
            controller.sugarField.setText("20");
            controller.priceField.setText("4.5");
            controller.typeCB.getItems().add("Jelly");
            controller.typeCB.setValue("Jelly");

            TextField taste = new TextField("apple");
            TextField shape = new TextField("round");

            controller.detailBox.getChildren().addAll(new Label(), taste, new Label(), shape);
        });
        robot.clickOn("#saveButton");
        robot.clickOn("OK");
    }

    @Test
    void testOnSave_successGingerbread(FxRobot robot) {
        Platform.runLater(() -> {
            controller.nameField.setText("GingerName");
            controller.weightField.setText("9.0");
            controller.sugarField.setText("28");
            controller.priceField.setText("5.0");
            controller.typeCB.getItems().add("Gingerbread");
            controller.typeCB.setValue("Gingerbread");

            TextField shape = new TextField("star");
            CheckBox iced = new CheckBox();
            iced.setSelected(true);

            controller.detailBox.getChildren().addAll(new Label(), shape, iced);
        });
        robot.clickOn("#saveButton");
        robot.clickOn("OK");
    }

    @Test
    void testOnSave_invalidFields_showsError(FxRobot robot) {
        Platform.runLater(() -> {
            controller.nameField.setText("CandyName");
            controller.weightField.setText("bad");
            controller.sugarField.setText("text");
            controller.priceField.setText("invalid");
            controller.typeCB.getItems().add("Candy");
            controller.typeCB.setValue("Candy");
            controller.detailBox.getChildren().addAll(new Label(), new TextField("f"), new Label(), new ComboBox<CandyType>());
        });
        robot.clickOn("#saveButton");
        robot.clickOn("OK");
    }

    @Test
    void testOnSave_saveFails_showsErrorAlert(FxRobot robot) {
        sweetsRepoMock.close();
        sweetsRepoMock = mockStatic(SweetsRepository.class);
        sweetsRepoMock.when(() -> SweetsRepository.saveSweetToDb(any())).thenReturn(false);

        Platform.runLater(() -> {
            controller.nameField.setText("FailCandy");
            controller.weightField.setText("10.5");
            controller.sugarField.setText("25");
            controller.priceField.setText("5.5");
            controller.typeCB.getItems().add("Candy");
            controller.typeCB.setValue("Candy");

            TextField filling = new TextField("caramel");
            ComboBox<CandyType> typeBox = new ComboBox<>();
            typeBox.getItems().add(CandyType.CHOCOLATE);
            typeBox.setValue(CandyType.CHOCOLATE);

            controller.detailBox.getChildren().addAll(new Label(), filling, new Label(), typeBox);
        });
        robot.clickOn("#saveButton");
        robot.clickOn("OK");
    }
}
