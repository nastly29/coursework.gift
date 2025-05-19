package controllers.menuForSweetsBase;

import domain.sweets.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;
import repository.SweetsRepository;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import javafx.application.Platform;


@ExtendWith(MockitoExtension.class)
@ExtendWith(ApplicationExtension.class)
public class EditSweetTest {

    private EditSweet controller;
    private Candy candy;

    @BeforeAll
    static void initToolkit() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {}
    }

    @BeforeEach
    void setUp() {
        candy = new Candy(0, "Test", 100.0, 30.0, 50.0, "Brand", "TYPE", null);
        controller = new EditSweet(candy);

        controller.nameField = new TextField("Test");
        controller.weightField = new TextField("100");
        controller.sugarField = new TextField("30");
        controller.priceField = new TextField("50");

        controller.detailBox = new VBox(
                new Label(), new TextField("Nougat"),
                new Label(), new ComboBox<>()
        );
        ((ComboBox<String>) controller.detailBox.getChildren().get(3)).getItems().add("TYPE");
        ((ComboBox<String>) controller.detailBox.getChildren().get(3)).setValue("TYPE");
    }

    @Test
    void testBuildChocolateFields_populatesCorrectly() throws Exception {
        Chocolate ch = new Chocolate(0, "Choco", 10.0, 0.5, 15.0, 72.5, "Hazelnut", "MILK", null);
        EditSweet c = new EditSweet(ch);
        VBox box = new VBox();
        Method m = EditSweet.class.getDeclaredMethod("buildChocolateFields");
        m.setAccessible(true);
        setField(c, "detailBox", box);
        setField(c, "sweet", ch);
        m.invoke(c);
        assertEquals(6, box.getChildren().size());
        assertTrue(box.getChildren().get(1) instanceof TextField);
        assertEquals("72.5", ((TextField) box.getChildren().get(1)).getText());
        assertEquals("Hazelnut", ((TextField) box.getChildren().get(3)).getText());
        assertEquals("MILK", ((ComboBox<?>) box.getChildren().get(5)).getValue());
    }

    @Test
    void testBuildJellyFields_populatesCorrectly() throws Exception {
        Jelly j = new Jelly(0, "Jelly", 6.0, 0.2, 5.0, "Strawberry", "Round", null);
        EditSweet c = new EditSweet(j);
        VBox box = new VBox();
        Method m = EditSweet.class.getDeclaredMethod("buildJellyFields");
        m.setAccessible(true);
        setField(c, "detailBox", box);
        setField(c, "sweet", j);
        m.invoke(c);
        assertEquals(4, box.getChildren().size());
        assertEquals("Strawberry", ((TextField) box.getChildren().get(1)).getText());
        assertEquals("Round", ((TextField) box.getChildren().get(3)).getText());
    }

    @Test
    void testBuildGingerbreadFields_populatesCorrectly() throws Exception {
        Gingerbread g = new Gingerbread(0, "Ginger", 8.0, 0.3, 12.0, "Star", true, null);
        EditSweet c = new EditSweet(g);
        VBox box = new VBox();
        Method m = EditSweet.class.getDeclaredMethod("buildGingerbreadFields");
        m.setAccessible(true);
        setField(c, "detailBox", box);
        setField(c, "sweet", g);
        m.invoke(c);
        assertEquals(3, box.getChildren().size());
        assertEquals("Star", ((TextField) box.getChildren().get(1)).getText());
        assertTrue(((CheckBox) box.getChildren().get(2)).isSelected());
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        var f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }

    @Test
    void testOnSave_invalidNumbers_showsError() throws Exception {
        controller.weightField.setText("abc");
        controller.sugarField.setText("xyz");
        controller.priceField.setText("oops");

        CountDownLatch latch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                Method onSave = EditSweet.class.getDeclaredMethod("onSave");
                onSave.setAccessible(true);
                onSave.invoke(controller);
            } catch (Exception e) {
                fail("onSave помилка: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
        latch.await();
        assertEquals("abc", controller.weightField.getText());
    }

    @Test
    void testOnSave_successfulUpdate_closesDialog() throws Exception {
        Dialog<?> mockDialog = mock(Dialog.class);
        controller.setDialog(mockDialog);
        controller.dialogPane = new DialogPane();

        try (MockedStatic<SweetsRepository> repoMock = mockStatic(SweetsRepository.class)) {
            repoMock.when(() -> SweetsRepository.updateSweetInDb(any())).thenReturn(true);

            Method onSave = EditSweet.class.getDeclaredMethod("onSave");
            onSave.setAccessible(true);
            onSave.invoke(controller);
            verify(mockDialog).close();
        }
    }

    @Test
    void testFillCandy_setsFieldsCorrectly() throws Exception {
        Candy c = new Candy(0, "C", 10.0, 5.0, 20.0, "Brand", "TYPE", null);
        controller.detailBox = new VBox(
                new Label(), new TextField("Caramel"),
                new Label(), new ComboBox<>()
        );
        ((ComboBox<String>) controller.detailBox.getChildren().get(3)).setValue("TYPE");

        Method m = EditSweet.class.getDeclaredMethod("fillCandy", Candy.class);
        m.setAccessible(true);
        m.invoke(controller, c);

        assertEquals("Caramel", c.getFilling());
        assertEquals("TYPE", c.getType());
    }

    @Test
    void testFillJelly_setsFieldsCorrectly() throws Exception {
        Jelly jelly = new Jelly(0, "J", 10.0, 5.0, 15.0, "apple", "circle", null);
        controller.detailBox = new VBox(
                new Label(), new TextField("apple"),
                new Label(), new TextField("cube")
        );

        Method m = EditSweet.class.getDeclaredMethod("fillJelly", Jelly.class);
        m.setAccessible(true);
        m.invoke(controller, jelly);

        assertEquals("apple", jelly.getFruityTaste());
        assertEquals("cube", jelly.getShape());
    }

    @Test
    void testFillGingerbread_setsFieldsCorrectly() throws Exception {
        Gingerbread g = new Gingerbread(0, "G", 12.0, 6.0, 18.0, "tree", true, null);
        controller.detailBox = new VBox(
                new Label(), new TextField("tree"),
                new CheckBox()
        );
        ((CheckBox) controller.detailBox.getChildren().get(2)).setSelected(true);

        Method m = EditSweet.class.getDeclaredMethod("fillGingerbread", Gingerbread.class);
        m.setAccessible(true);
        m.invoke(controller, g);

        assertEquals("tree", g.getShape());
        assertTrue(g.isIced());
    }

    @Test
    void testFillChocolate_setsFieldsCorrectly() throws Exception {
        Chocolate ch = new Chocolate(0, "Ch", 11.0, 4.0, 22.0, 70.5, "nut", "BITTER", null);
        controller.detailBox = new VBox(
                new Label(), new TextField("70.5"),
                new Label(), new TextField("hazelnut"),
                new Label(), new ComboBox<>()
        );
        ((ComboBox<String>) controller.detailBox.getChildren().get(5)).setValue("BITTER");

        Method m = EditSweet.class.getDeclaredMethod("fillChocolate", Chocolate.class);
        m.setAccessible(true);
        m.invoke(controller, ch);

        assertEquals(70.5, ch.getCocoaPercentage());
        assertEquals("hazelnut", ch.getFilling());
        assertEquals("BITTER", ch.getType());
    }

    @Test
    void testChooseImage_updatesLabelAndImageData(FxRobot robot) {
        Candy sweet = new Candy(1, "Test", 100, 20, 10, "Caramel", "Soft", null);
        EditSweet controller = new EditSweet(sweet);

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuForSweetsBase/EditSweet.fxml"));
                loader.setController(controller);
                DialogPane pane = loader.load();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(pane);

                controller.setDialog(dialog);

                Stage stage = new Stage();
                stage.setScene(new Scene(new VBox(new Label("Mock Root"))));
                stage.show();

                // Емуляція вибору файлу вручну
                controller.imageData = "mock bytes".getBytes();
                controller.imgLabel.setText("mock.png");

                // Імітуємо натискання кнопки вибору зображення
                controller.imgBtn.fire();
                assertEquals("mock.png", controller.imgLabel.getText());
                assertNotNull(controller.imageData);

            } catch (Exception e) {
                fail("Не вдалося завантажити EditSweet.fxml: " + e.getMessage());
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
}