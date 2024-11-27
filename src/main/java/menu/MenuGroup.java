package menu;

import gift.Gift;
import menu.helpers.UserInputHelper;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MenuGroup implements MenuItem {
    private static final Logger logger = LogManager.getLogger(MenuGroup.class);

    private String name;
    private boolean isMainMenu;
    private List<MenuItem> items = new ArrayList<>();

    public MenuGroup(String name) {
        this(name, false);
    }

    public MenuGroup(String name, boolean isMainMenu) {
        this.name = name;
        this.isMainMenu = isMainMenu;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void execute() {
        if (name.equals("Меню дій з подарунком") && !Gift.exists()) {
            System.out.println("Подарунок ще не створено. Створіть подарунок, щоб отримати доступ до цього меню.");
            logger.warn("Спроба доступу до меню без створеного подарунка.");
            return;
        }

        while (true) {
            printMenu();
            int choice = UserInputHelper.promptInt("Ваш вибір (0 для виходу) -> ");
            logger.info("Користувач зробив вибір: {} в меню {}.", choice, name);
            if (processChoice(choice)) {
                logger.info("Користувач вийшов з меню: {}.", name);
                break;
            }
        }
    }

    public MenuGroup addItem(MenuItem item) {
        items.add(item);
        return this;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void printMenu() {
        System.out.println("=====================================");
        System.out.println(name + ":");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, items.get(i).name());
        }
        System.out.println("=====================================");
    }

    public boolean processChoice(int choice) {
        if (choice == 0) {
            if (isMainMenu) {
                System.out.println("\nПрограму завершено.");
                logger.info("Програму завершено користувачем через головне меню.");
            }
            return true;
        } else if (choice > 0 && choice <= items.size()) {
            items.get(choice - 1).execute();
        } else {
            System.out.println("Невірний вибір!");
        }
        return false;
    }
}
