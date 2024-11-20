package menu.menuForGift;

import gift.Gift;
import gift.sweets.Sweets;
import menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class DisplayGiftInfo implements MenuItem {

    @Override
    public String name() {
        return "Відобразити інформацію про подарунок";
    }

    @Override
    public void execute() {
        Gift gift = Gift.getInstance(null, null);
        List<Sweets> sweets = new ArrayList<>(gift.getSweets());
        System.out.println("\n---Інформація про подарунок---");
        System.out.println(gift);
        getTotalPrice(sweets);
        getTotalWeight(sweets);
    }

    public void getTotalPrice(List<Sweets> sweets) {
        double total = 0.0;
        for(Sweets sweet: sweets){
            total+=sweet.getPrice();
        }
        System.out.println("Загальна ціна:" + total +" г");
    }

    public void getTotalWeight(List<Sweets> sweets) {
        double total = 0.0;
        for(Sweets sweet: sweets){
            total+=sweet.getWeight();
        }
        System.out.println("Загальна вага:" + total + " грн");
    }
}