package menu.menuForSweetsBase;

import gift.sweets.Sweets;
import menu.MenuItem;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;

import java.util.List;

public class DeleteSweetFromBase implements MenuItem {
    @Override
    public String name() {
        return "Видалити солодощі з бази";
    }

    @Override
    public void execute() {
        int codeDelete = UserInputHelper.promptInt("Введіть код солодощів, які потрібно видалити: ");
        List<Sweets> sweetsList = SweetBaseHelper.loadSweetsFromFile();

        if (sweetsList.isEmpty()) {
            System.out.println("\nБаза даних солодощів порожня.");
            return;
        }

        boolean removed = sweetsList.removeIf(sweet -> sweet.getCode() == codeDelete);
        if (removed) {
            SweetBaseHelper.updateContent(sweetsList);
            System.out.println("\nСолодощі з кодом " + codeDelete + " успішно видалено.");
        } else {
            System.out.println("\nСолодощі з кодом " + codeDelete + " не знайдено.");
        }
    }
}