package menu.menuForGift;

import gift.Package;
import gift.types.ColorType;
import gift.Gift;
import menu.*;
import menu.helpers.UserInputHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class CreateGift implements MenuItem {
    private static final Logger logger = LogManager.getLogger(CreateGift.class);

    @Override
    public String name() {
        return "Створити подарунок";
    }

    @Override
    public void execute() {
        logger.info("Користувач почав створення подарунка.");

        if (Gift.exists()) {
            System.out.println("Подарунок вже створено!");
            logger.warn("Спроба створити подарунок, коли він вже існує.");
            return;
        }

        try {
            String giftName = UserInputHelper.promptString("Введіть назву подарунка: ");
            logger.info("Назва подарунка: {}", giftName);

            System.out.println("Кольори для стрічки і коробки:");
            System.out.println(Arrays.toString(ColorType.values()));

            String boxColor = UserInputHelper.promptColor("Введіть колір коробки: ");
            logger.info("Колір коробки: {}", boxColor);

            String ribbonColor = UserInputHelper.promptColor("Введіть колір стрічки: ");
            logger.info("Колір стрічки: {}", ribbonColor);

            String message = UserInputHelper.promptString("Введіть повідомлення на упаковці: ");
            logger.info("Повідомлення на упаковці: {}", message);

            Package pack = new Package(boxColor, ribbonColor, message);
            Gift.getInstance(giftName, pack);

            System.out.println("\nПодарунок успішно створено!");
            logger.info("Подарунок успішно створено з назвою: {}", giftName);
        } catch (Exception e) {
            logger.error("Помилка при створенні подарунка: {}", e.getMessage(), e);
            System.out.println("Сталася помилка під час створення подарунка. Спробуйте ще раз.");
        }
    }
}
