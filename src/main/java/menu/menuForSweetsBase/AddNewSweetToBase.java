package menu.menuForSweetsBase;

import gift.sweets.*;
import menu.MenuItem;
import menu.helpers.SweetBaseHelper;
import menu.helpers.UserInputHelper;

import java.util.List;

public class AddNewSweetToBase implements MenuItem {
    @Override
    public String name() {
        return "Додати нові солодощі";
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println("\nВиберіть тип солодощів для додавання (або 0 для виходу): ");
            System.out.println("1. Цукерка\n2. Шоколад\n3. Мармелад\n4. Пряник");
            int choice = UserInputHelper.promptInt("Ваш вибір -> ");

            if (choice == 0) {
                System.out.println("\nВихід з режиму додавання солодощів.");
                break;
            }

            if (choice < 1 || choice > 4) {
                System.out.println("Невірний вибір. Введіть число від 1 до 4 або 0 для виходу.");
                continue;
            }

            Sweets newSweet = createSweet(choice);
            if (newSweet != null) {
                SweetBaseHelper.saveSweet(newSweet);
                System.out.println("Нові солодощі успішно додано до бази даних:\n" + newSweet);
            }
        }
    }

    public Sweets createSweet(int choice) {
        int code;
        while (true) {
            code = UserInputHelper.promptInt("Введіть код товару: ");
            if (isCodeUnique(code)) {
                break;
            } else {
                System.out.println("Код вже використовується. Введіть інший код.");
            }
        }

        String name = UserInputHelper.promptString("Введіть назву: ");
        double weight = UserInputHelper.promptDouble("Введіть вагу(г): ");
        double sugarContent = UserInputHelper.promptDouble("Введіть відсоток цукру(%): ");
        double price = UserInputHelper.promptDouble("Введіть ціну(грн): ");

        return switch (choice) {
            case 1 -> createCandy(code,name, weight, sugarContent, price);
            case 2 -> createChocolate(code,name, weight, sugarContent, price);
            case 3 -> createJelly(code,name, weight, sugarContent, price);
            case 4 -> createGingerbread(code,name, weight, sugarContent, price);
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        };
    }

    // Метод для перевірки унікальності коду
    public boolean isCodeUnique(int code) {
        List<Sweets> allSweets = SweetBaseHelper.loadSweetsFromFile();
        for (Sweets sweet : allSweets) {
            if (sweet.getCode() == code) {
                return false;
            }
        }
        return true;
    }

    //метод для створення цукерки
    public Candy createCandy(int code,String name, double weight, double sugarContent, double price) {
        String filling = UserInputHelper.promptString("Введіть начинку: ");
        String type = UserInputHelper.promptCandyType();
        return new Candy(code,name, weight, sugarContent, price, filling, type);
    }

    //метод для створення шоколаду
    public Chocolate createChocolate(int code,String name, double weight, double sugarContent, double price) {
        double cocoaPercentage = UserInputHelper.promptDouble("Введіть відсоток какао(%): ");
        String filling = UserInputHelper.promptString("Введіть начинку: ");
        String type = UserInputHelper.promptChocolateType();
        return new Chocolate(code,name, weight, sugarContent, price, cocoaPercentage, filling, type);
    }

    //метод для створення мармеладу
    public Jelly createJelly(int code,String name, double weight, double sugarContent, double price) {
        String fruityTaste = UserInputHelper.promptString("Введіть фруктовий смак: ");
        String shape = UserInputHelper.promptString("Введіть форму: ");
        return new Jelly(code,name, weight, sugarContent, price, fruityTaste, shape);
    }

    //метод для створення пряника
    public Gingerbread createGingerbread(int code,String name, double weight, double sugarContent, double price) {
        String shape = UserInputHelper.promptString("Введіть форму: ");
        String message = "Чи глазурований пряник? (так/ні): ";
        boolean isIced = UserInputHelper.promptString(message).equalsIgnoreCase("так");
        return new Gingerbread(code,name, weight, sugarContent, price, shape, isIced);
    }
}
