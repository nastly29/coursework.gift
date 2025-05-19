package controllers.menuForSweetsBase;

import domain.sweets.Sweets;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import repository.SweetsRepository;
import controllers.SweetsViewFactory;
import utils.TableAction;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FindSweetByPrice implements Initializable {

    @FXML public ScrollPane scrollPane;
    @FXML TextField minField;
    @FXML TextField maxField;
    @FXML private Button    searchBtn;
    @FXML private FlowPane  flowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        scrollPane.setVisible(false);
        scrollPane.setManaged(false);
        searchBtn.setOnAction(_ -> onSearch());
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
        onSearch(min,max);
    }

    @FXML
    public void onSearch(double min, double max) {
        flowPane.getChildren().clear();
        List<Sweets> found = SweetsRepository.findSweetsByPrice(min, max);
        if (found.isEmpty()) {
            flowPane.getChildren().add(new Label("Нічого не знайдено."));
            scrollPane.setVisible(false);
            scrollPane.setManaged(false);
        } else {
            for (Sweets s : found) {
                flowPane.getChildren().add(
                        SweetsViewFactory.createCard(s, null, new TableAction("Видалити", sw -> {
                            try {
                                if (SweetsRepository.deleteSweetFromDb(sw.getCode())) {
                                    onSearch(min, max); // повторно
                                } else {
                                    new Alert(Alert.AlertType.ERROR, "Не вдалося видалити солодощі.").showAndWait();
                                }
                            } catch (SQLException ex) {
                                new Alert(Alert.AlertType.ERROR, "Помилка при видаленні: " + ex.getMessage()).showAndWait();
                            }
                        }))
                );
            }
            scrollPane.setVisible(true);
            scrollPane.setManaged(true);
        }
    }
}