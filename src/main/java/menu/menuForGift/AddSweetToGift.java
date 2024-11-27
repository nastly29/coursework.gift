package menu.menuForGift;

import gift.Gift;
import gift.sweets.Sweets;
import menu.*;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AddSweetToGift implements MenuItem {
    private static final Logger logger = LogManager.getLogger(AddSweetToGift.class);

    @Override
    public String name() {
        return "Додати солодощі до подарунка";
    }

    @Override
    public void execute() {
        logger.info("Користувач розпочав додавання солодощів до подарунка.");

        Gift gift = Gift.getInstance(null, null);
        List<Sweets> allSweets = SweetBaseHelper.loadSweetsFromFile();

        for(Sweets sweet: allSweets){
            System.out.println(sweet);
        }

        while (true) {
            int sweetCode = UserInputHelper.promptInt("\nВведіть код товару (або 0 для виходу): ");
            if (sweetCode == 0) {
                logger.info("Користувач завершив додавання солодощів до подарунка.");
                break;
            }
            boolean found = false;
            for (Sweets sweet : allSweets) {
                if (sweet.getCode() == sweetCode) {
                    gift.addSweet(sweet);
                    found = true;
                    System.out.println("\nСолодощі з кодом " + sweetCode + " додано до подарунка.");
                    logger.info("Солодощі з кодом {} успішно додано до подарунка.", sweetCode);
                    break;
                }
            }
            if (!found) {
                System.out.println("\nСолодощі з вказаним кодом не знайдено.");
                logger.warn("Солодощі з кодом {} не знайдено.", sweetCode);
            }
        }
    }
}
