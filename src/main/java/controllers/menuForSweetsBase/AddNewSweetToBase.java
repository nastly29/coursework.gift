package controllers.menuForSweetsBase;

import domain.sweets.*;
import domain.types.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import repository.SweetsRepository;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class AddNewSweetToBase implements Initializable {

    @FXML public ButtonType saveButtonType;
    @FXML private GridPane form;
    @FXML TextField nameField;
    @FXML TextField weightField;
    @FXML TextField sugarField;
    @FXML TextField priceField;
    @FXML ComboBox<String> typeCB;
    @FXML VBox detailBox;
    @FXML private Button imgBtn, saveBtn;
    @FXML Label imgLabel;
    public DialogPane dialogPane;
    private byte[] imageData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        typeCB.setItems(FXCollections.observableArrayList("Candy","Chocolate","Jelly","Gingerbread"));
        typeCB.valueProperty().addListener((_, _, newV) -> {
            detailBox.getChildren().clear();
            if (newV == null) return;
            switch (newV) {
                case "Candy" -> buildCandyFields();
                case "Chocolate" -> buildChocolateFields();
                case "Jelly" -> buildJellyFields();
                case "Gingerbread" -> buildGingerbreadFields();
            }
        });

        //Обробник кнопки вибору зображення
        imgBtn.setOnAction(_ -> {
            File file = new FileChooser()
                    .showOpenDialog(form.getScene().getWindow());
            if (file != null) {
                try {
                    imageData = Files.readAllBytes(file.toPath());
                    imgLabel.setText(file.getName());
                } catch (IOException ex) {
                    new Alert(Alert.AlertType.ERROR,"Не вдалося прочитати файл: " + ex.getMessage()).showAndWait();
                }
            }
        });
        saveBtn.setOnAction(_ -> onSave());
    }

    private void buildCandyFields() {
        TextField fillingF = new TextField();
        ComboBox<CandyType> cb = new ComboBox<>(
                FXCollections.observableArrayList(CandyType.values())
        );
        cb.setPromptText("Вид цукерки");
        detailBox.getChildren().addAll(
                new Label("Начинка:"), fillingF,
                new Label("Вид цукерки:"), cb
        );
    }

    private void buildChocolateFields() {
        TextField cocoaF   = new TextField();
        TextField fillingF = new TextField();
        ComboBox<ChocolateType> cb =
                new ComboBox<>(FXCollections.observableArrayList(ChocolateType.values()));
        cb.setPromptText("Тип шоколаду");
        detailBox.getChildren().addAll(
                new Label("% какао:"), cocoaF,
                new Label("Начинка:"), fillingF,
                new Label("Тип шоколаду:"), cb
        );
    }

    private void buildJellyFields() {
        TextField tasteF = new TextField();
        TextField shapeF = new TextField();
        detailBox.getChildren().addAll(
                new Label("Смак:"), tasteF,
                new Label("Форма:"), shapeF
        );
    }

    private void buildGingerbreadFields() {
        TextField shapeF = new TextField();
        CheckBox icedCB  = new CheckBox("Глазурований");
        detailBox.getChildren().addAll(
                new Label("Форма:"), shapeF,
                icedCB
        );
    }

    private void onSave() {
        try {
            String nm = nameField.getText().trim();
            double wt = Double.parseDouble(weightField.getText().trim());
            double sc = Double.parseDouble(sugarField.getText().trim());
            double pr = Double.parseDouble(priceField.getText().trim());
            String tp = typeCB.getValue();
            Sweets sweet = switch (tp) {
                case "Candy" -> {
                    TextField f = (TextField) detailBox.getChildren().get(1);
                    CandyType ct = (CandyType)
                            ((ComboBox<?>) detailBox.getChildren().get(3)).getValue();
                    yield new Candy(0, nm, wt, sc, pr,
                            f.getText(), ct.toString(), imageData);
                }
                case "Chocolate" -> {
                    TextField c = (TextField) detailBox.getChildren().get(1);
                    TextField f = (TextField) detailBox.getChildren().get(3);
                    ChocolateType cht = (ChocolateType)
                            ((ComboBox<?>) detailBox.getChildren().get(5)).getValue();
                    yield new Chocolate(0, nm, wt, sc, pr,
                            Double.parseDouble(c.getText()),
                            f.getText(), cht.toString(), imageData);
                }
                case "Jelly" -> {
                    TextField t = (TextField) detailBox.getChildren().get(1);
                    TextField s = (TextField) detailBox.getChildren().get(3);
                    yield new Jelly(0, nm, wt, sc, pr, t.getText(), s.getText(), imageData);
                }
                case "Gingerbread" -> {
                    TextField s = (TextField) detailBox.getChildren().get(1);
                    CheckBox iced = (CheckBox) detailBox.getChildren().get(2);
                    yield new Gingerbread(0, nm, wt, sc, pr, s.getText(), iced.isSelected(), imageData);
                }
                default -> throw new IllegalStateException("Unexpected value: " + tp);
            };

            boolean ok = SweetsRepository.saveSweetToDb(sweet);
            new Alert(ok ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                    ok ? "Солодощі додано: " + sweet.getName()
                            : "Не вдалося додати солодощі до бази.")
                    .showAndWait();
            if (ok) clearForm();
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Помилка: " + ex.getMessage()).showAndWait();
        }
    }

    private void clearForm() {
        nameField.clear();
        weightField.clear();
        sugarField.clear();
        priceField.clear();
        typeCB.getSelectionModel().clearSelection();
        detailBox.getChildren().clear();
        imageData = null;
        imgLabel.setText("");
    }
}