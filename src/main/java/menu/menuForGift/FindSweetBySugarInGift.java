package menu.menuForGift;

import gift.Gift;
import gift.sweets.Sweets;
import menu.MenuItem;
import menu.helpers.UserInputHelper;

public class FindSweetBySugarInGift implements MenuItem {
    @Override
    public String name() {
        return "Пошук солодощів за рівнем цукру";
    }

    @Override
    public void execute() {
        Gift gift = Gift.getInstance(null, null);

        double minSugar = UserInputHelper.promptDouble("Введіть мінімальний відсоток цукру: ");
        double maxSugar = UserInputHelper.promptDouble("Введіть максимальний відсоток цукру: ");
        boolean found = false;

        for (Sweets sweet : gift.getSweets()) {
            double sugarContent = sweet.getSugarContent();
            if (sugarContent >= minSugar && sugarContent <= maxSugar) {
                System.out.println(sweet);
                found = true;
            }
        }
        if (!found) {
            System.out.println("\nНе знайдено солодощів з рівнем цукру у вказаному діапазоні.");
        }
    }
}