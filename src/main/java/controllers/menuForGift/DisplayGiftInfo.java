package controllers.menuForGift;

import domain.sweets.Sweets;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import service.GiftService;
import controllers.SweetsViewFactory;
import utils.TableAction;
import java.util.List;

public class DisplayGiftInfo {
    private final GiftService giftService;

    @FXML public Label boxColorLabel;
    @FXML public Label ribbonColorLabel;
    @FXML public Label messageLabel;
    @FXML private Label infoLabel;
    @FXML private Label titleLabel;
    @FXML private ScrollPane scrollPane;
    @FXML private FlowPane flowPane;
    @FXML private Label totalsLabel;

    public DisplayGiftInfo(GiftService giftService) {
        this.giftService = giftService;
    }

    @FXML
    public void initialize() {
        // Якщо подарунок ще не створений
        if (!giftService.exists()) {
            infoLabel.setText("Спершу створіть подарунок!");
            hideContent();
            return;
        }

        // Список солодощів у подарунку
        List<Sweets> sweets = giftService.listSweets();
        if (sweets.isEmpty()) {
            infoLabel.setText("Подарунок порожній — додайте солодощі!");
            hideContent();
            return;
        }
        infoLabel.setText("");
        titleLabel.setText("Подарунок: " + giftService.getGift().getName());
        boxColorLabel.setText("Колір коробки: " + giftService.getGift().getPack().getBoxColor());
        ribbonColorLabel.setText("Колір стрічки: " + giftService.getGift().getPack().getRibbonColor());
        messageLabel.setText("Повідомлення: " + giftService.getGift().getPack().getMessage());
        scrollPane.setVisible(true);
        totalsLabel.setVisible(true);
        populateCards();
    }

    private void hideContent() {
        titleLabel.setVisible(false);
        boxColorLabel.setVisible(false);
        ribbonColorLabel.setVisible(false);
        messageLabel.setVisible(false);
        scrollPane.setVisible(false);
        totalsLabel.setVisible(false);
    }

    private void populateCards() {
        flowPane.getChildren().clear();
        for (Sweets s : giftService.listSweets()) {
            flowPane.getChildren().add(
                    SweetsViewFactory.createCard(
                            s,
                            giftService,
                            new TableAction("−", ss -> {
                                giftService.decreaseSweet(ss);
                                populateCards();
                            }),
                            new TableAction("Видалити", ss -> {
                                giftService.removeSweet(ss);
                                populateCards();
                            }),
                            new TableAction("+", ss -> {
                                giftService.addSweetToGift(ss);
                                populateCards();
                            })
                    )
            );
        }
        totalsLabel.setText(String.format(
                "Загальна ціна: %.2f грн    Загальна вага: %.2f г",
                giftService.totalPrice(),
                giftService.totalWeight()
        ));
    }
}