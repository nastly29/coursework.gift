package controllers.menuForSweetsBase;

import domain.sweets.*;
import domain.types.CandyType;
import domain.types.ChocolateType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import repository.SweetsRepository;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class EditSweet implements Initializable {
    @FXML public ButtonType saveButtonType;
    @FXML private TextField nameField, weightField, sugarField, priceField;
    @FXML private ComboBox<String> typeCB;
    @FXML private VBox detailBox;
    @FXML private Button imgBtn;
    @FXML private Label imgLabel;
    @FXML private DialogPane dialogPane;

    private final Sweets sweet;
    private byte[] imageData;
    private Dialog<?> dialog;

    public EditSweet(Sweets sweet) {
        this.sweet = sweet;
    }

    public void setDialog(Dialog<?> dialog) {
        this.dialog = dialog;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCB.getItems().addAll("Candy", "Chocolate", "Jelly", "Gingerbread");
        typeCB.setDisable(true);

        nameField.setText(sweet.getName());
        weightField.setText(String.valueOf(sweet.getWeight()));
        sugarField.setText(String.valueOf(sweet.getSugarContent()));
        priceField.setText(String.valueOf(sweet.getPrice()));

        String dbType = sweet.getSweetType();
        String uiType = capitalize(dbType);
        typeCB.setValue(uiType);

        switch (dbType.toLowerCase()) {
            case "candy"       -> buildCandyFields();
            case "chocolate"   -> buildChocolateFields();
            case "jelly"       -> buildJellyFields();
            case "gingerbread" -> buildGingerbreadFields();
        }

        //зображення
        imageData = sweet.getImageData();
        imgLabel.setText(imageData != null ? "(є зображення)" : "");

        //Обробник кнопки «Оберіть зображення»
        imgBtn.setOnAction(_ -> {
            Stage stage = (Stage) dialogPane.getScene().getWindow();
            File f = new FileChooser().showOpenDialog(stage);
            if (f != null) {
                try {
                    imageData = Files.readAllBytes(f.toPath());
                    imgLabel.setText(f.getName());
                } catch (IOException ex) {
                    new Alert(Alert.AlertType.ERROR, "Не вдалося зчитати файл").showAndWait();
                }
            }
        });
        Button saveBtn = (Button) dialogPane.lookupButton(saveButtonType);
        saveBtn.setOnAction(_ -> onSave());
    }

    private void onSave() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Назва не може бути порожньою").showAndWait();
            return;
        }
        double weight, sugar, price;
        try {
            weight = Double.parseDouble(weightField.getText().trim());
            sugar  = Double.parseDouble(sugarField.getText().trim());
            price  = Double.parseDouble(priceField.getText().trim());
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "Поля «Вага», «Цукор» та «Ціна» повинні містити дійсні числа!").showAndWait();
            return;
        }

        sweet.setName(name);
        sweet.setWeight(weight);
        sweet.setSugarContent(sugar);
        sweet.setPrice(price);
        sweet.setImageData(imageData);

        switch (sweet.getSweetType().toLowerCase()) {
            case "candy" -> fillCandy((Candy) sweet);
            case "chocolate" -> fillChocolate((Chocolate) sweet);
            case "jelly" -> fillJelly((Jelly) sweet);
            case "gingerbread" -> fillGingerbread((Gingerbread) sweet);
        }

        boolean ok = SweetsRepository.updateSweetInDb(sweet);
        if (ok && dialog != null) {
            dialog.close();
        } else {
            new Alert(Alert.AlertType.ERROR, "Помилка при збереженні!").showAndWait();
        }
    }

    // Допоміжні методи для побудови/зчитування полів деталізації
    private void buildCandyFields() {
        TextField f = new TextField(sweet instanceof Candy ? ((Candy) sweet).getFilling() : "");
        ComboBox<String> cb = new ComboBox<>();
        for (CandyType t : CandyType.values()) cb.getItems().add(t.toString());
        cb.setValue(sweet instanceof Candy ? ((Candy) sweet).getType() : null);
        detailBox.getChildren().addAll(
                new Label("Начинка:"), f,
                new Label("Тип цукерки:"), cb
        );
    }

    @SuppressWarnings("unchecked")
    private void fillCandy(Candy c) {
        TextField f = (TextField) detailBox.getChildren().get(1);
        ComboBox<String> cb = (ComboBox<String>) detailBox.getChildren().get(3);
        c.setFilling(f.getText());
        c.setType(cb.getValue());
    }

    private void buildChocolateFields() {
        TextField p = new TextField(sweet instanceof Chocolate
                ? String.valueOf(((Chocolate) sweet).getCocoaPercentage()) : "");
        TextField f = new TextField(sweet instanceof Chocolate
                ? ((Chocolate) sweet).getFilling() : "");
        ComboBox<String> cb = new ComboBox<>();
        for (ChocolateType t : ChocolateType.values()) cb.getItems().add(t.toString());
        cb.setValue(sweet instanceof Chocolate ? ((Chocolate) sweet).getType() : null);
        detailBox.getChildren().addAll(
                new Label("% какао:"), p,
                new Label("Начинка:"), f,
                new Label("Тип шоколаду:"), cb
        );
    }

    @SuppressWarnings("unchecked")
    private void fillChocolate(Chocolate c) {
        TextField p  = (TextField) detailBox.getChildren().get(1);
        TextField f  = (TextField) detailBox.getChildren().get(3);
        ComboBox<String> cb = (ComboBox<String>) detailBox.getChildren().get(5);
        c.setCocoaPercentage(Double.parseDouble(p.getText()));
        c.setFilling(f.getText());
        c.setType(cb.getValue());
    }

    private void buildJellyFields() {
        TextField t = new TextField(sweet instanceof Jelly ? ((Jelly) sweet).getFruityTaste() : "");
        TextField s = new TextField(sweet instanceof Jelly ? ((Jelly) sweet).getShape() : "");
        detailBox.getChildren().addAll(
                new Label("Смак:"), t,
                new Label("Форма:"), s
        );
    }
    private void fillJelly(Jelly j) {
        TextField t = (TextField) detailBox.getChildren().get(1);
        TextField s = (TextField) detailBox.getChildren().get(3);
        j.setFruityTaste(t.getText());
        j.setShape(s.getText());
    }

    private void buildGingerbreadFields() {
        TextField s = new TextField(sweet instanceof Gingerbread
                ? ((Gingerbread) sweet).getShape() : "");
        CheckBox cb = new CheckBox("Глазурований");
        cb.setSelected(sweet instanceof Gingerbread && ((Gingerbread) sweet).isIced());
        detailBox.getChildren().addAll(
                new Label("Форма:"), s,
                cb
        );
    }
    private void fillGingerbread(Gingerbread g) {
        TextField s = (TextField) detailBox.getChildren().get(1);
        CheckBox cb = (CheckBox) detailBox.getChildren().get(2);
        g.setShape(s.getText());
        g.setIced(cb.isSelected());
    }

    private String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}