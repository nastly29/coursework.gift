package controllers.menuForSweetsBase;

import domain.sweets.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import service.GiftService;
import utils.TableAction;
import java.io.ByteArrayInputStream;
import java.util.Locale;

public class SweetsCardController {
    @FXML private ImageView imageView;
    @FXML private Label nameLbl, typeLbl, priceLbl, weightLbl, sugarLbl, detailLbl, qtyLbl;
    @FXML private HBox actionsBox;

    private final Sweets s;
    private final GiftService giftService;
    private final TableAction[] actions;

    public SweetsCardController(Sweets s, GiftService giftService, TableAction... actions) {
        this.s = s;
        this.giftService = giftService;
        this.actions = actions;
    }

    @FXML
    private void initialize() {
        setupImage();
        setupBasicInfo();
        setupDetail();
        setupQuantity();
        setupActions();
    }

    private void setupImage() {
        byte[] data = s.getImageData();
        if (data != null && data.length > 0) {
            imageView.setImage(new Image(new ByteArrayInputStream(data)));
        }
    }

    private void setupBasicInfo() {
        nameLbl.setText(s.getName());
        typeLbl.setText(s.getSweetType());
        priceLbl.setText(String.format(Locale.US, "Ціна: %.2f грн", s.getPrice()));
        weightLbl.setText(String.format(Locale.US, "Вага: %.2f г", s.getWeight()));
        sugarLbl.setText(String.format(Locale.US, "Цукор: %.2f%%", s.getSugarContent()));
    }

    private void setupDetail() {
        switch (s) {
            case Candy c ->
                    detailLbl.setText(
                            "Начинка: " + c.getFilling() +
                                    "\nВид цукерки: " + c.getType()
                    );
            case Chocolate ch ->
                    detailLbl.setText(String.format(
                            "%% какао: %.0f\nНачинка: %s\nВид шоколаду: %s",
                            ch.getCocoaPercentage(), ch.getFilling(), ch.getType()
                    ));
            case Jelly j ->
                    detailLbl.setText(String.format(
                            "Смак: %s\nФорма: %s",
                            j.getFruityTaste(), j.getShape()
                    ));
            case Gingerbread g ->
                    detailLbl.setText(String.format(
                            "Форма: %s\nГлазурований: %s",
                            g.getShape(), g.isIced() ? "так" : "ні"
                    ));
            default ->
                    detailLbl.setText("");
        }
    }

    private void setupQuantity() {
        if (giftService != null) {
            int qty = giftService.getQuantity(s);
            if (qty > 0) {
                qtyLbl.setText("Кількість: " + qty);
                qtyLbl.setVisible(true);
            } else {
                qtyLbl.setVisible(false);
            }
        } else {
            qtyLbl.setVisible(false);
        }
    }

    private void setupActions() {
        actionsBox.getChildren().clear();
        for (TableAction act : actions) {
            // для дії "Додати до подарунка" показуємо напис "Додано" замість кнопки, якщо вже є
            if (giftService != null && "Додати до подарунка".equals(act.getName())) {
                boolean added = giftService.getGift().getSweets()
                        .stream().anyMatch(sw -> sw.getCode() == s.getCode());
                if (added) {
                    Label done = new Label("Додано");
                    done.getStyleClass().add("added-label");
                    actionsBox.getChildren().add(done);
                    continue;
                }
            }
            Button b = new Button(act.getName());
            b.setOnAction(_ -> act.getHandler().accept(s));
            actionsBox.getChildren().add(b);
        }
    }
}