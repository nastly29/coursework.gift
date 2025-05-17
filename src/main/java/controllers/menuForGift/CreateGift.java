package controllers.menuForGift;

import domain.Package;
import domain.types.ColorType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.GiftService;
import java.util.Arrays;
import java.util.List;

public class CreateGift {
    private final GiftService giftService;
    private Runnable onGiftCreated;

    @FXML private TextField nameField;
    @FXML private TextField messageField;
    @FXML private ComboBox<String> boxColorCB;
    @FXML private ComboBox<String> ribbonColorCB;
    @FXML private Button createBtn;
    @FXML private Button saveBtn;

    public CreateGift(GiftService giftService) {
        this.giftService = giftService;
    }

    @FXML
    public void initialize() {
        List<String> colors = Arrays.stream(ColorType.values()).map(ColorType::getColorName).toList();
        boxColorCB.setItems(FXCollections.observableArrayList(colors));
        ribbonColorCB.setItems(FXCollections.observableArrayList(colors));

        // якщо вже є подарунок — заповнити поля
        if (giftService.exists()) {
            var g = giftService.getGift();
            nameField.setText(g.getName());
            messageField.setText(g.getPack().getMessage());
            boxColorCB.setValue(g.getPack().getBoxColor());
            ribbonColorCB.setValue(g.getPack().getRibbonColor());
            createBtn.setDisable(true);
            saveBtn.setDisable(false);
        }
    }

    @FXML
    private void onCreate() {
        if (validate()) {
            giftService.createOrUpdateGift(nameField.getText().trim(),
                    new Package(boxColorCB.getValue(), ribbonColorCB.getValue(), messageField.getText().trim()));
            new Alert(Alert.AlertType.INFORMATION, "Подарунок створено!").showAndWait();
            createBtn.setDisable(true);
            saveBtn.setDisable(false);
            if (onGiftCreated != null) onGiftCreated.run();
        } else {
            new Alert(Alert.AlertType.ERROR, "Заповніть усі поля!").showAndWait();
        }
    }

    @FXML
    private void onSave() {
        giftService.createOrUpdateGift(nameField.getText().trim(),
                new Package(boxColorCB.getValue(), ribbonColorCB.getValue(), messageField.getText().trim()));
        new Alert(Alert.AlertType.INFORMATION, "Зміни збережено!").showAndWait();
    }

    private boolean validate() {
        return !nameField.getText().trim().isEmpty()
                && !messageField.getText().trim().isEmpty()
                && boxColorCB.getValue() != null
                && ribbonColorCB.getValue() != null;
    }

    public void setOnGiftCreated(Runnable handler) {
        this.onGiftCreated = handler;
    }
}