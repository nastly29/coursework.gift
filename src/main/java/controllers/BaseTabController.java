package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class BaseTabController {
    private static final Logger log = LoggerFactory.getLogger(BaseTabController.class);

    @FXML public StackPane introPane;
    @FXML private StackPane contentPane;

    @FXML
    void initialize() {
        contentPane.getChildren().setAll(introPane);
    }

    @FXML
    private void onAddNew() {
        loadScreen("/fxml/menuForSweetsBase/AddNewSweetToBase.fxml");
    }

    @FXML
    private void onViewBase() {
        loadScreen("/fxml/menuForSweetsBase/DisplaySweetsBase.fxml");
    }

    @FXML
    private void onSearchPrice() {
        loadScreen("/fxml/menuForSweetsBase/FindSweetByPrice.fxml");
    }

    private void loadScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            log.error("Не вдалося завантажити FXML: {}", fxmlPath, e);
        }
    }
}