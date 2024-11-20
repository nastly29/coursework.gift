package menu.menuForGift;

import menu.MenuItem;
import gift.Gift;
import menu.helpers.UserInputHelper;

public class RemoveSweetFromGift implements MenuItem {
    @Override
    public String name() {
        return "Видалити солодощі з подарунка";
    }

    @Override
    public void execute() {
        Gift gift = Gift.getInstance(null, null);

        int sweetCode = UserInputHelper.promptInt("Введіть код солодощів для видалення: ");
        boolean removed = gift.getSweets().removeIf(sweet -> sweet.getCode() == sweetCode);
        if (removed) {
            System.out.println("\nСолодощі з кодом " + sweetCode + " успішно видалено з подарунка.");
        } else {
            System.out.println("\nСолодощі з кодом " + sweetCode + " не знайдено в подарунку.");
        }
    }
}
