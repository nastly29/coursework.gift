package menu.menuForSweetsBase;

import gift.sweets.Sweets;
import menu.MenuItem;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;

import java.util.List;

public class FindSweetByPrice implements MenuItem {
    @Override
    public String name() {
        return "Пошук солодощів за ціною";
    }

    @Override
    public void execute() {
        double minPrice = UserInputHelper.promptDouble("Введіть мінімальну ціну: ");
        double maxPrice = UserInputHelper.promptDouble("Введіть максимальну ціну: ");

        List<Sweets> sweetsList = SweetBaseHelper.loadSweetsFromFile();
        if (sweetsList.isEmpty()) {
            System.out.println("\nБаза даних солодощів порожня.");
            return;
        }

        boolean found = false;
        for (Sweets sweet : sweetsList) {
            double price = sweet.getPrice();
            if (price >= minPrice && price <= maxPrice) {
                System.out.println(sweet);
                found = true;
            }
        }

        if (!found) {
            System.out.println("\nНе знайдено солодощів у вказаному діапазоні цін.");
        }
    }
}
