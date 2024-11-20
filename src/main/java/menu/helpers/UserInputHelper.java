package menu.helpers;

import gift.types.CandyType;
import gift.types.ChocolateType;
import gift.types.ColorType;

import java.util.Scanner;

public class UserInputHelper {
    private static Scanner scanner = new Scanner(System.in);

    public static void setScanner(Scanner newScanner) {
        scanner = newScanner;
    }

    public static double promptDouble(String message) {
        double value;

        while (true) {
            System.out.print(message);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (value >= 0) {
                    break;
                } else {
                    System.out.println("Введене значення має бути додатним. Спробуйте ще раз.");
                }
            } else {
                System.out.println("Введене значення не є числом. Спробуйте ще раз.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return value;
    }

    public static int promptInt(String message) {
        int value;

        while (true) {
            System.out.print(message);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= 0) {
                    break;
                } else {
                    System.out.println("Введене значення має бути додатним. Спробуйте ще раз.");
                }
            } else {
                System.out.println("Введене значення не є цілим числом. Спробуйте ще раз.");
                scanner.next();
            }
        }
        scanner.nextLine();
        return value;
    }

    public static String promptString(String message) {
        System.out.print(message);
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.println("Введення не може бути порожнім. Спробуйте ще раз.");
            System.out.print(message);
            input = scanner.nextLine().trim();
        }
        return input;
    }

    public static String promptChocolateType() {
        while (true) {
            String input = promptString("Введіть тип шоколаду (Чорний, Молочний, Білий): ").trim();
            try {
                for (ChocolateType type : ChocolateType.values()) {
                    if (type.getChocolateName().equalsIgnoreCase(input)) {
                        return input;
                    }
                }
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильний тип шоколаду. Спробуйте ще раз.");
            }
        }
    }

    public static String promptCandyType() {
        while (true) {
            String input = promptString("Введіть тип цукерки (Шоколадна, Іриска, Льодяник, Карамель): ").trim();
            try {
                for (CandyType type : CandyType.values()) {
                    if (type.getCandyName().equalsIgnoreCase(input)) {
                        return input;
                    }
                }
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильний тип цукерки. Спробуйте ще раз.");
            }
        }
    }

    public static  String promptColor(String message) {
        while (true) {
            String colorInput = UserInputHelper.promptString(message).trim();

            for (ColorType color : ColorType.values()) {
                if (color.getColorName().equalsIgnoreCase(colorInput)) {
                    return color.getColorName();
                }
            }
            System.out.println("\nНеправильний колір. Будь ласка, введіть один з наявних.");
        }
    }
}
