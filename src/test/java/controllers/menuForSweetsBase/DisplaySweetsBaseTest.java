package controllers.menuForSweetsBase;

import domain.sweets.Sweets;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.testfx.framework.junit5.ApplicationExtension;
import repository.SweetsRepository;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class DisplaySweetsBaseTest {

    private DisplaySweetsBase controller;

    @BeforeEach
    public void setUp() {
        controller = new DisplaySweetsBase();
        controller.flowPane = new FlowPane();
    }

    @Test
    void testPopulateCards_emptyBase_showsMessage() {
        try (MockedStatic<SweetsRepository> mockedRepo = mockStatic(SweetsRepository.class)) {
            mockedRepo.when(SweetsRepository::loadSweetsFromDb).thenReturn(Collections.emptyList());
            controller.initialize(null, null);
            assertEquals(1, controller.flowPane.getChildren().size());
            assertTrue(controller.flowPane.getChildren().get(0) instanceof Label);
            assertTrue(((Label) controller.flowPane.getChildren().get(0)).getText().contains("База порожня"));
        }
    }

    @Test
    void testOpenEditDialog_doesNotThrow() {
        Sweets sweet = mock(Sweets.class);
        when(sweet.getName()).thenReturn("Test");
        assertDoesNotThrow(() -> controller.openEditDialog(sweet));
    }
}
