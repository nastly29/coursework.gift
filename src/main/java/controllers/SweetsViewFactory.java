package controllers;

import controllers.menuForSweetsBase.SweetsCardController;
import domain.sweets.Sweets;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.GiftService;
import utils.TableAction;
import java.io.IOException;

public class SweetsViewFactory {
    private static final Logger log = LoggerFactory.getLogger(SweetsViewFactory.class);

    public static VBox createCard(Sweets s, GiftService giftService, TableAction... actions) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    SweetsViewFactory.class.getResource("/fxml/menuForSweetsBase/SweetsCard.fxml")
            );
            loader.setControllerFactory(_ ->
                    new SweetsCardController(s, giftService, actions)
            );
            return loader.load();
        } catch (IOException e) {
            log.warn("Не вдалося завантажити шаблон картки для солодощів з кодом {}: {}", s.getCode(), e.getMessage(), e);
            return new VBox(new Label("Помилка завантаження картки"));
        }
    }
}