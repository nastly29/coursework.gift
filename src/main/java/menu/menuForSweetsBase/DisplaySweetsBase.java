package menu.menuForSweetsBase;

import gift.sweets.Sweets;
import menu.MenuItem;
import menu.helpers.SweetBaseHelper;

import java.util.List;

public class DisplaySweetsBase implements MenuItem {
    @Override
    public String name() {
        return "Переглянути наявні солодощі";
    }

    @Override
    public void execute() {
        List<Sweets> sweets = SweetBaseHelper.loadSweetsFromFile();
        if (sweets.isEmpty()) {
            System.out.println("База даних порожня.");
        } else {
            for (Sweets sweet : sweets) {
                System.out.println(sweet);
            }
        }
    }
}
