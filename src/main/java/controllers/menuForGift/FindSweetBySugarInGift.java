package controllers.menuForGift;

import domain.sweets.Sweets;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import service.GiftService;
import controllers.SweetsViewFactory;
import utils.TableAction;
import java.util.List;

public class FindSweetBySugarInGift {
    private final GiftService giftService;

    @FXML public ScrollPane resultPane;
    @FXML public Button searchBtn;
    @FXML TextField minField;
    @FXML TextField maxField;
    @FXML FlowPane flowPane;

    public FindSweetBySugarInGift(GiftService giftService) {
        this.giftService = giftService;
    }

    @FXML
    public void initialize() {
        resultPane.setVisible(false);
        resultPane.setManaged(false);
        flowPane.getChildren().clear();
    }

    @FXML
    public void onSearch() {
        flowPane.getChildren().clear();
        double min, max;

        try {
            min = Double.parseDouble(minField.getText().trim());
            max = Double.parseDouble(maxField.getText().trim());
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Невірний формат числа!").showAndWait();
            return;
        }

        List<Sweets> found = giftService.findBySugar(min, max);
        if (found.isEmpty()) {
            flowPane.getChildren().add(new Label("Нічого не знайдено."));
            return;
        }

        for (Sweets s : found) {
            flowPane.getChildren().add(
                    SweetsViewFactory.createCard(
                            s,
                            giftService,
                            new TableAction("Видалити", ss -> {
                                giftService.removeSweet(ss);
                                onSearch();
                            })
                    )
            );
        }
        resultPane.setManaged(true);
        resultPane.setVisible(true);
    }
}