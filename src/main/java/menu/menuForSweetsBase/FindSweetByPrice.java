package menu.menuForSweetsBase;

import gift.sweets.Sweets;
import menu.MenuItem;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class FindSweetByPrice implements MenuItem {
    private static final Logger logger = LogManager.getLogger(FindSweetByPrice.class);

    @Override
    public String name() {
        return "Пошук солодощів за ціною";
    }

    @Override
    public void execute() {
        logger.info("Користувач почав пошук солодощів за ціною.");

        double minPrice = UserInputHelper.promptDouble("Введіть мінімальну ціну: ");
        double maxPrice = UserInputHelper.promptDouble("Введіть максимальну ціну: ");
        logger.info("Користувач ввів діапазон цін: від {} до {} грн.", minPrice, maxPrice);

        List<Sweets> sweetsList = SweetBaseHelper.loadSweetsFromFile();
        if (sweetsList.isEmpty()) {
            System.out.println("\nБаза даних солодощів порожня.");
            logger.warn("Спроба пошуку у порожній базі даних солодощів.");
            return;
        }

        boolean found = false;
        for (Sweets sweet : sweetsList) {
            double price = sweet.getPrice();
            if (price >= minPrice && price <= maxPrice) {
                System.out.println(sweet);
                logger.debug("Знайдено солодощі: {} з ціною {} грн.", sweet, price);
                found = true;
            }
        }

        if (!found) {
            System.out.println("\nНе знайдено солодощів у вказаному діапазоні цін.");
            logger.warn("Не знайдено солодощів у діапазоні цін: від {} до {} грн.", minPrice, maxPrice);
        }
    }
}
