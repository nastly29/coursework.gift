package controllers;

import controllers.menuForGift.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.GiftService;
import java.io.IOException;
import java.util.function.Consumer;

public class GiftTabController {
    private static final Logger log = LoggerFactory.getLogger(GiftTabController.class);

    @FXML public StackPane introPane;
    @FXML private StackPane contentPane;
    @FXML private Button btnAddSweet, btnDisplay, btnSearchSugar, btnSortWeight, btnFinish;

    private final GiftService giftService;

    public GiftTabController(GiftService giftService) {
        this.giftService = giftService;
    }

    @FXML
    private void initialize() {
        disableGiftActions();
    }

    @FXML private void onParams() { load("/menuForGift/CreateGift.fxml", c -> ((CreateGift)c).setOnGiftCreated(this::enableGiftActions)); }
    @FXML private void onAddSweet() { load("/menuForGift/AddSweetToGift.fxml",null); }
    @FXML private void onDisplay() { load("/menuForGift/DisplayGiftInfo.fxml",null); }
    @FXML private void onSearchSugar() { load("/menuForGift/FindSweetBySugarInGift.fxml",null); }
    @FXML private void onSortWeight() { load("/menuForGift/SortSweetByWeightInGift.fxml",null); }

    @FXML private void onFinish() {
        giftService.reset();
        contentPane.getChildren().setAll(introPane);
        disableGiftActions();
    }

    private <T> void load(String fxml, Consumer<T> after) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml" + fxml));
            loader.setControllerFactory(t -> switch (t.getSimpleName()) {
                case "CreateGift" -> new CreateGift(giftService);
                case "AddSweetToGift" -> new AddSweetToGift(giftService);
                case "DisplayGiftInfo" -> new DisplayGiftInfo(giftService);
                case "FindSweetBySugarInGift" -> new FindSweetBySugarInGift(giftService);
                case "SortSweetByWeightInGift" -> new SortSweetByWeightInGift(giftService);
                default -> throw new IllegalStateException("Unknown: "+t);
            });
            Parent view = loader.load();
            log.info("Екран {} успішно завантажено", fxml);

            if (after != null) {
                after.accept(loader.getController());
                log.debug("Виконано after-обробку для контролера: {}", loader.getController().getClass().getSimpleName());
            }
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            log.error("Не вдалося завантажити FXML: {}", fxml, e);
        }
    }

    private void enableGiftActions() {
        btnAddSweet.setDisable(false);
        btnDisplay.setDisable(false);
        btnSearchSugar.setDisable(false);
        btnSortWeight.setDisable(false);
        btnFinish.setDisable(false);
    }
    private void disableGiftActions() {
        btnAddSweet.setDisable(true);
        btnDisplay.setDisable(true);
        btnSearchSugar.setDisable(true);
        btnSortWeight.setDisable(true);
        btnFinish.setDisable(true);
    }
}
