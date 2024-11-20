package menu.menuForGift;

import gift.Package;
import gift.types.ColorType;
import gift.Gift;
import menu.*;
import menu.helpers.UserInputHelper;

import java.util.Arrays;

public class CreateGift implements MenuItem {

    @Override
    public String name() {
        return "Створити подарунок";
    }

    @Override
    public void execute() {
        if (Gift.exists()) {
            System.out.println("Подарунок вже створено!");
            return;
        }

        String giftName = UserInputHelper.promptString("Введіть назву подарунка: ");
        System.out.println("Кольори для стрічки і коробки:");
        System.out.println(Arrays.toString(ColorType.values()));

        String boxColor = UserInputHelper.promptColor("Введіть колір коробки: ");
        String ribbonColor = UserInputHelper.promptColor("Введіть колір стрічки: ");
        String message = UserInputHelper.promptString("Введіть повідомлення на упаковці: ");

        Package pack = new Package(boxColor, ribbonColor, message);
        Gift.getInstance(giftName, pack);
        System.out.println("\nПодарунок успішно створено!");
    }
}
