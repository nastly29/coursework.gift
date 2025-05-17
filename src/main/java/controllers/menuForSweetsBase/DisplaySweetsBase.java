package controllers.menuForSweetsBase;

import domain.sweets.Sweets;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import repository.SweetsRepository;
import controllers.SweetsViewFactory;
import utils.TableAction;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class DisplaySweetsBase implements Initializable {
    private static final Logger log = LoggerFactory.getLogger(DisplaySweetsBase.class);

    @FXML private FlowPane flowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateCards();
    }

    private void populateCards() {
        flowPane.getChildren().clear();
        List<Sweets> sweets = SweetsRepository.loadSweetsFromDb();
        if (sweets.isEmpty()) {
            flowPane.getChildren().add(new Label("База порожня"));
            return;
        }

        for (Sweets s : sweets) {
            flowPane.getChildren().add(
                    SweetsViewFactory.createCard(
                            s,
                            null,
                            new TableAction("Редагувати", this::openEditDialog),
                            new TableAction("Видалити", sw -> {
                                try {
                                    boolean deleted = SweetsRepository.deleteSweetFromDb(sw.getCode());
                                    if (deleted) {
                                        populateCards();
                                    } else {
                                        new Alert(Alert.AlertType.ERROR, "Не вдалося видалити.").showAndWait();
                                    }
                                } catch (SQLException ex) {
                                    new Alert(Alert.AlertType.ERROR, "Помилка: " + ex.getMessage()).showAndWait();
                                }
                            })
                    )
            );
        }
    }

    private void openEditDialog(Sweets sw) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menuForSweetsBase/EditSweet.fxml"));
            loader.setController(new EditSweet(sw));

            DialogPane pane = loader.load();
            pane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(pane);
            dialog.setOnHidden(_ -> populateCards());

            EditSweet controller = loader.getController();
            controller.setDialog(dialog);
            dialog.setTitle("Редагувати «" + sw.getName() + "»");
            dialog.show();
        } catch (IOException e) {
            log.warn("Не вдалось відкрити вікно редагування для {}", sw.getName(), e);
        }
    }
}