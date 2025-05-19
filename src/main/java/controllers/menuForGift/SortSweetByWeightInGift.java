package controllers.menuForGift;

import domain.sweets.Sweets;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import service.GiftService;
import controllers.SweetsViewFactory;
import java.util.List;

public class SortSweetByWeightInGift {
    private final GiftService giftService;

    @FXML public ScrollPane scrollPane;
    @FXML public Button sortBtn;
    @FXML private RadioButton ascBtn;
    @FXML private FlowPane flowPane;

    public SortSweetByWeightInGift(GiftService giftService) {
        this.giftService = giftService;
    }

    @FXML
    public void initialize() {
        scrollPane.setVisible(false);
        scrollPane.setManaged(false);
    }

    @FXML
    private void onSort() {
        populateSorted();
    }

    private void populateSorted() {
        flowPane.getChildren().clear();
        boolean asc = ascBtn.isSelected();
        List<Sweets> sorted = giftService.getSweetsSortedByWeight(asc);
        for (Sweets s : sorted) {
            flowPane.getChildren().add(
                    SweetsViewFactory.createCard(s, giftService)
            );
        }
        scrollPane.setManaged(true);
        scrollPane.setVisible(true);
    }
}
