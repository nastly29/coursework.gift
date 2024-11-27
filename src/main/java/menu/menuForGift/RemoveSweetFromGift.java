package menu.menuForGift;

import menu.MenuItem;
import gift.Gift;
import menu.helpers.UserInputHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveSweetFromGift implements MenuItem {
    private static final Logger logger = LogManager.getLogger(RemoveSweetFromGift.class);

    @Override
    public String name() {
        return "Видалити солодощі з подарунка";
    }

    @Override
    public void execute() {
        logger.info("Користувач почав видалення солодощів з подарунка.");

        Gift gift = Gift.getInstance(null, null);
        if (gift.getSweets().isEmpty()) {
            System.out.println("Подарунок порожній.");
            logger.warn("Спроба видалення солодощів з порожнього подарунка.");
            return;
        }

        try {
            int sweetCode = UserInputHelper.promptInt("Введіть код солодощів для видалення: ");
            logger.info("Користувач ввів код для видалення: {}", sweetCode);

            boolean removed = gift.getSweets().removeIf(sweet -> sweet.getCode() == sweetCode);
            if (removed) {
                System.out.println("\nСолодощі з кодом " + sweetCode + " успішно видалено з подарунка.");
                logger.info("Солодощі з кодом {} успішно видалено з подарунка.", sweetCode);
            } else {
                System.out.println("\nСолодощі з кодом " + sweetCode + " не знайдено в подарунку.");
                logger.warn("Солодощі з кодом {} не знайдено в подарунку.", sweetCode);
            }
        } catch (Exception e) {
            logger.error("Помилка під час видалення солодощів з подарунка: {}", e.getMessage(), e);
            System.out.println("Сталася помилка. Спробуйте ще раз.");
        }
    }
}
