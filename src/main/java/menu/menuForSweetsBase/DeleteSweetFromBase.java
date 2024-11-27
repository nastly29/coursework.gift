package menu.menuForSweetsBase;

import gift.sweets.Sweets;
import menu.MenuItem;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DeleteSweetFromBase implements MenuItem {
    private static final Logger logger = LogManager.getLogger(DeleteSweetFromBase.class);

    @Override
    public String name() {
        return "Видалити солодощі з бази";
    }

    @Override
    public void execute() {
        logger.info("Користувач почав видалення солодощів з бази.");

        int codeDelete = UserInputHelper.promptInt("Введіть код солодощів, які потрібно видалити: ");
        logger.info("Користувач ввів код для видалення: {}", codeDelete);

        List<Sweets> sweetsList = SweetBaseHelper.loadSweetsFromFile();

        if (sweetsList.isEmpty()) {
            System.out.println("\nБаза даних солодощів порожня.");
            return;
        }

        boolean removed = sweetsList.removeIf(sweet -> sweet.getCode() == codeDelete);
        if (removed) {
            SweetBaseHelper.updateContent(sweetsList);
            System.out.println("\nСолодощі з кодом " + codeDelete + " успішно видалено.");
            logger.info("Солодощі з кодом {} успішно видалено з файлу.", codeDelete);
        } else {
            System.out.println("\nСолодощі з кодом " + codeDelete + " не знайдено.");
            logger.warn("Солодощі з кодом {} не знайдено в базі.", codeDelete);
        }
    }
}