package controllers.menuForGift;

import domain.sweets.Sweets;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import service.GiftService;
import controllers.SweetsViewFactory;
import utils.TableAction;
import repository.SweetsRepository;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddSweetToGift implements Initializable {
    private final GiftService giftService;

    @FXML private Label infoLabel;
    @FXML private ScrollPane scrollPane;
    @FXML private FlowPane flowPane;

    public AddSweetToGift(GiftService giftService) {
        this.giftService = giftService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Якщо подарунок ще не створений
        if (!giftService.exists()) {
            infoLabel.setText("Спершу створіть подарунок!");
            scrollPane.setVisible(false);
            return;
        }

        List<Sweets> all = SweetsRepository.loadSweetsFromDb();
        if (all.isEmpty()) {
            infoLabel.setText("У базі немає солодощів.");
            scrollPane.setVisible(false);
            return;
        }
        infoLabel.setText("");
        scrollPane.setVisible(true);
        populateCards(all);
    }

    private void populateCards(List<Sweets> sweets) {
        flowPane.getChildren().clear();
        for (Sweets s : sweets) {
            flowPane.getChildren().add(
                    SweetsViewFactory.createCard(
                            s,
                            giftService,
                            new TableAction("Додати до подарунка", sw -> {
                                giftService.addSweetToGift(sw);
                                populateCards(SweetsRepository.loadSweetsFromDb());
                            })
                    )
            );
        }
    }
}