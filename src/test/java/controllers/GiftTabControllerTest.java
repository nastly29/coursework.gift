package controllers;

import javafx.scene.layout.StackPane;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import service.GiftService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class GiftTabControllerTest {

    private GiftTabController controller;
    private GiftService giftService;
    private StackPane introPane;
    private StackPane contentPane;
    private Button btnAddSweet, btnDisplay, btnSearchSugar, btnSortWeight, btnFinish;

    @BeforeEach
    public void setUp() {
        giftService = Mockito.mock(GiftService.class);
        controller = new GiftTabController(giftService);

        introPane = new StackPane();
        contentPane = new StackPane();
        btnAddSweet = new Button();
        btnDisplay = new Button();
        btnSearchSugar = new Button();
        btnSortWeight = new Button();
        btnFinish = new Button();

        controller.introPane = introPane;
        setPrivateField(controller, "contentPane", contentPane);
        setPrivateField(controller, "btnAddSweet", btnAddSweet);
        setPrivateField(controller, "btnDisplay", btnDisplay);
        setPrivateField(controller, "btnSearchSugar", btnSearchSugar);
        setPrivateField(controller, "btnSortWeight", btnSortWeight);
        setPrivateField(controller, "btnFinish", btnFinish);
        callInitialize(controller);
    }

    private void callInitialize(GiftTabController c) {
        try {
            var method = GiftTabController.class.getDeclaredMethod("initialize");
            method.setAccessible(true);
            method.invoke(c);
        } catch (Exception e) {
            fail("Не вдалося викликати метод initialize: " + e.getMessage());
        }
    }

    private void setPrivateField(Object obj, String fieldName, Object value) {
        try {
            var field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj, value);
        } catch (Exception e) {
            fail("Не вдалося встановити поле: " + fieldName);
        }
    }

    @Test
    public void testGiftServiceIsSet() {
        assertNotNull(controller);
    }

    @Test
    public void testAllButtonsDisabledOnInit() {
        assertTrue(btnAddSweet.isDisabled());
        assertTrue(btnDisplay.isDisabled());
        assertTrue(btnSearchSugar.isDisabled());
        assertTrue(btnSortWeight.isDisabled());
        assertTrue(btnFinish.isDisabled());
    }

    @Test
    public void testOnFinishResetsServiceAndUI() {
        controller.onFinish();

        verify(giftService).reset();
        assertEquals(1, contentPane.getChildren().size());
        assertSame(introPane, contentPane.getChildren().get(0));

        assertTrue(btnAddSweet.isDisabled());
        assertTrue(btnDisplay.isDisabled());
        assertTrue(btnSearchSugar.isDisabled());
        assertTrue(btnSortWeight.isDisabled());
        assertTrue(btnFinish.isDisabled());
    }
}