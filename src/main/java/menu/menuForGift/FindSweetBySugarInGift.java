package menu.menuForGift;

import gift.Gift;
import gift.sweets.Sweets;
import menu.MenuItem;
import menu.helpers.UserInputHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FindSweetBySugarInGift implements MenuItem {
    private static final Logger logger = LogManager.getLogger(FindSweetBySugarInGift.class);

    @Override
    public String name() {
        return "Пошук солодощів за рівнем цукру";
    }

    @Override
    public void execute() {
        logger.info("Користувач почав пошук солодощів за рівнем цукру.");

        Gift gift = Gift.getInstance(null, null);
        if (gift.getSweets().isEmpty()) {
            System.out.println("Подарунок порожній.");
            logger.warn("Спроба пошуку солодощів у порожньому подарунку.");
            return;
        }

        double minSugar = UserInputHelper.promptDouble("Введіть мінімальний відсоток цукру: ");
        double maxSugar = UserInputHelper.promptDouble("Введіть максимальний відсоток цукру: ");
        logger.info("Пошук солодощів з рівнем цукру від {} до {}.", minSugar, maxSugar);
        boolean found = false;

        for (Sweets sweet : gift.getSweets()) {
            double sugarContent = sweet.getSugarContent();
            if (sugarContent >= minSugar && sugarContent <= maxSugar) {
                System.out.println(sweet);
                logger.info("Знайдено солодощі: {} з рівнем цукру {}%.", sweet, sugarContent);
                found = true;
            }
        }
        if (!found) {
            System.out.println("\nНе знайдено солодощів з рівнем цукру у вказаному діапазоні.");
            logger.warn("Не знайдено солодощів з рівнем цукру у діапазоні від {} до {}.", minSugar, maxSugar);
        }
    }
}