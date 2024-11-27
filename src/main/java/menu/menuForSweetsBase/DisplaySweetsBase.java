package menu.menuForSweetsBase;

import gift.sweets.Sweets;
import menu.MenuItem;
import menu.helpers.SweetBaseHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DisplaySweetsBase implements MenuItem {
    private static final Logger logger = LogManager.getLogger(DisplaySweetsBase.class);

    @Override
    public String name() {
        return "Переглянути наявні солодощі";
    }

    @Override
    public void execute() {
        logger.info("Користувач почав перегляд бази даних солодощів.");

        List<Sweets> sweets = SweetBaseHelper.loadSweetsFromFile();
        if (sweets.isEmpty()) {
            System.out.println("База даних порожня.");
        } else {
            for (Sweets sweet : sweets) {
                System.out.println(sweet);
            }
            logger.info("Відображено {} солодощів з бази даних.", sweets.size());
        }
    }
}
