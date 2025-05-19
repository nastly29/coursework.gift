package controllers.menuForSweetsBase;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;
import repository.SweetsRepository;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.util.WaitForAsyncUtils.waitForFxEvents;

public class AddNewSweetToBaseTest extends ApplicationTest {

    private MockedStatic<SweetsRepository> repoMock;

    @BeforeEach
    public void mockRepository() {
        repoMock = Mockito.mockStatic(SweetsRepository.class);
    }

    @AfterEach
    public void cleanupRepository() {
        repoMock.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/menuForSweetsBase/AddNewSweetToBase.fxml")
        );
        loader.setControllerFactory(c -> new AddNewSweetToBase());
        Parent root = loader.load();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    @Test
    public void initialize_typeComboBoxHasAllOptions() {
        ComboBox<String> typeCB = lookup("#typeCB").queryComboBox();
        List<String> expected = List.of("Candy", "Chocolate", "Jelly", "Gingerbread");
        assertEquals(expected, typeCB.getItems());
    }

    @Test
    public void selectingCandy_buildsCandyFields() {
        clickOn("#typeCB").clickOn("Candy");
        waitForFxEvents();

        VBox detailBox = lookup("#detailBox").queryAs(VBox.class);
        assertEquals(4, detailBox.getChildren().size());

        Label lbl1 = (Label) detailBox.getChildren().get(0);
        assertEquals("Начинка:", lbl1.getText());

        assertTrue(detailBox.getChildren().get(1) instanceof TextField);
        Label lbl2 = (Label) detailBox.getChildren().get(2);

        assertEquals("Вид цукерки:", lbl2.getText());
        assertTrue(detailBox.getChildren().get(3) instanceof ComboBox);
    }

    @Test
    public void selectingChocolate_buildsChocolateFields() {
        clickOn("#typeCB").clickOn("Chocolate");
        waitForFxEvents();

        VBox detailBox = lookup("#detailBox").queryAs(VBox.class);
        assertEquals(6, detailBox.getChildren().size());

        Label lbl1 = (Label) detailBox.getChildren().get(0);
        assertEquals("% какао:", lbl1.getText());

        Label lbl2 = (Label) detailBox.getChildren().get(2);
        assertEquals("Начинка:", lbl2.getText());

        Label lbl3 = (Label) detailBox.getChildren().get(4);
        assertEquals("Тип шоколаду:", lbl3.getText());
    }

    @Test
    public void selectingJelly_buildsJellyFields() {
        clickOn("#typeCB").clickOn("Jelly");
        waitForFxEvents();

        VBox detailBox = lookup("#detailBox").queryAs(VBox.class);
        assertEquals(4, detailBox.getChildren().size());

        Label lbl1 = (Label) detailBox.getChildren().get(0);
        assertEquals("Смак:", lbl1.getText());

        Label lbl2 = (Label) detailBox.getChildren().get(2);
        assertEquals("Форма:", lbl2.getText());
    }

    @Test
    public void selectingGingerbread_buildsGingerbreadFields() {
        clickOn("#typeCB").clickOn("Gingerbread");
        waitForFxEvents();

        VBox detailBox = lookup("#detailBox").queryAs(VBox.class);
        assertEquals(3, detailBox.getChildren().size());

        Label lbl = (Label) detailBox.getChildren().get(0);
        assertEquals("Форма:", lbl.getText());

        assertTrue(detailBox.getChildren().get(2) instanceof CheckBox);
    }
}