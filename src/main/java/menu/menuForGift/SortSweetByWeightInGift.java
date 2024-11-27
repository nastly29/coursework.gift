package menu.menuForGift;

import gift.sweets.Sweets;
import gift.Gift;
import menu.MenuItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SortSweetByWeightInGift implements MenuItem {
    private static final Logger logger = LogManager.getLogger(SortSweetByWeightInGift.class);

    @Override
    public String name() {
        return "Сортувати солодощі в подарунку за вагою";
    }

    @Override
    public void execute() {
        logger.info("Користувач почав сортування солодощів у подарунку за вагою.");

        Gift gift = Gift.getInstance(null, null);
        if (gift.getSweets().isEmpty()) {
            System.out.println("Подарунок порожній.");
            logger.warn("Спроба сортування солодощів у порожньому подарунку.");
            return;
        }

        try {
            List<Sweets> sweetsCopy = new ArrayList<>(gift.getSweets());
            sweetsCopy.sort(Comparator.comparing(Sweets::getWeight));

            logger.info("Солодощі в подарунку відсортовано за вагою.");
            System.out.println("\nСолодощі в подарунку відсортовано за вагою:");
            for (Sweets sweet : sweetsCopy) {
                System.out.println(sweet);
            }
        } catch (Exception e) {
            logger.error("Помилка під час сортування солодощів за вагою: {}", e.getMessage(), e);
            System.out.println("Сталася помилка. Спробуйте ще раз.");
        }
    }
}
