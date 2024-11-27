package menu.menuForGift;

import gift.Gift;
import gift.sweets.Sweets;
import menu.MenuItem;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisplayGiftInfo implements MenuItem {
    private static final Logger logger = LogManager.getLogger(DisplayGiftInfo.class);

    @Override
    public String name() {
        return "Відобразити інформацію про подарунок";
    }

    @Override
    public void execute() {
        logger.info("Користувач почав перегляд інформації про подарунок.");

        Gift gift = Gift.getInstance(null, null);
        List<Sweets> sweets = new ArrayList<>(gift.getSweets());
        System.out.println("\n---Інформація про подарунок---");
        System.out.println(gift);
        logger.info("Інформація про подарунок: {}", gift);

        getTotalPrice(sweets);
        getTotalWeight(sweets);
    }

    public void getTotalPrice(List<Sweets> sweets) {
        double total = 0.0;
        for(Sweets sweet: sweets){
            total+=sweet.getPrice();
        }
        System.out.println("Загальна ціна:" + total +" г");
        logger.info("Загальна ціна подарунка: {} грн", total);
    }

    public void getTotalWeight(List<Sweets> sweets) {
        double total = 0.0;
        for(Sweets sweet: sweets){
            total+=sweet.getWeight();
        }
        System.out.println("Загальна вага:" + total + " грн");
        logger.info("Загальна вага подарунка: {} г", total);
    }
}