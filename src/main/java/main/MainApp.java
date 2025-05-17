package main;

import domain.Gift;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.GiftService;
import java.util.Objects;

public class MainApp extends Application {
    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    @Override
    public void start(Stage stage) throws Exception {
        GiftService giftService = new GiftService(new Gift());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainLayout.fxml"));
            loader.setControllerFactory(cls -> {
                if (cls.equals(controllers.GiftTabController.class)) {
                    return new controllers.GiftTabController(giftService);
                }
                if (cls.equals(controllers.BaseTabController.class)) {
                    return new controllers.BaseTabController();
                }
                throw new IllegalStateException("Невідомий контролер: " + cls);
            });
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 650);
            scene.getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource("/styles/main.css")).toExternalForm()
            );

            stage.setTitle("Система подарунків");
            stage.setScene(scene);
            stage.show();
            log.info("Застосунок успішно запущено");
        } catch (Exception e) {
            log.error("Помилка під час запуску програми", e);
            throw e;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}