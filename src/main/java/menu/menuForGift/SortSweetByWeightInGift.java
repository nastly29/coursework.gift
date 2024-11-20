package menu.menuForGift;

import gift.sweets.Sweets;
import gift.Gift;
import menu.MenuItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortSweetByWeightInGift implements MenuItem {

    @Override
    public String name() {
        return "Сортувати солодощі в подарунку за вагою";
    }

    @Override
    public void execute() {
        Gift gift = Gift.getInstance(null, null);
        List<Sweets> sweetsCopy = new ArrayList<>(gift.getSweets());
        sweetsCopy.sort(Comparator.comparing(Sweets::getWeight));

        System.out.println("\nСолодощі в подарунку відсортовано за вагою:");
        for(Sweets sweet: sweetsCopy){
            System.out.println(sweet);
        }
    }
}
