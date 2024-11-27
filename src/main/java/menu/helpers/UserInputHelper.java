package menu.helpers;

import gift.types.CandyType;
import gift.types.ChocolateType;
import gift.types.ColorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class UserInputHelper {
    private static final Logger logger = LogManager.getLogger(UserInputHelper.class);
    //private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");

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
                    logger.info("Користувач ввід дійсне додатне число: {}", value);
                    break;
                } else {
                    System.out.println("Введене значення має бути додатним. Спробуйте ще раз.");
                    logger.warn("Користувач ввів від'ємне число: {}", value);
                }
            } else {
                System.out.println("Введене значення не є числом. Спробуйте ще раз.");
                logger.error("Неправильне введення для числа: {}", scanner.next());
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
                    logger.info("Користувач ввів дійсне додатне ціле число: {}", value);
                    break;
                } else {
                    System.out.println("Введене значення має бути додатним. Спробуйте ще раз.");
                    logger.warn("Користувач ввів від'ємне ціле число: {}", value);
                }
            } else {
                System.out.println("Введене значення не є цілим числом. Спробуйте ще раз.");
                logger.error("Неправильне введення для цілого числа: {}", scanner.next());
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
            logger.warn("Користувач ввів порожній рядок.");
            System.out.print(message);
            input = scanner.nextLine().trim();
        }
        logger.info("Користувач ввів коректний рядок: {}", input);
        return input;
    }

    public static String promptChocolateType() {
        while (true) {
            String input = promptString("Введіть тип шоколаду (Чорний, Молочний, Білий): ").trim();
            try {
                for (ChocolateType type : ChocolateType.values()) {
                    if (type.getChocolateName().equalsIgnoreCase(input)) {
                        logger.info("Користувач ввів правильний тип шоколаду: {}", input);
                        return input;
                    }
                }
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильний тип шоколаду. Спробуйте ще раз.");
                logger.error("Неправильний тип шоколаду: {}", input);
            }
        }
    }

    public static String promptCandyType() {
        while (true) {
            String input = promptString("Введіть тип цукерки (Шоколадна, Іриска, Льодяник, Карамель): ").trim();
            try {
                for (CandyType type : CandyType.values()) {
                    if (type.getCandyName().equalsIgnoreCase(input)) {
                        logger.info("Користувач ввів правильний тип цукерки: {}", input);
                        return input;
                    }
                }
                throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильний тип цукерки. Спробуйте ще раз.");
                logger.error("Неправильний тип цукерки: {}", input);
            }
        }
    }

    public static  String promptColor(String message) {
        while (true) {
            String colorInput = UserInputHelper.promptString(message).trim();

            for (ColorType color : ColorType.values()) {
                if (color.getColorName().equalsIgnoreCase(colorInput)) {
                    logger.info("Користувач ввів правильний колір: {}", colorInput);
                    return color.getColorName();
                }
            }
            System.out.println("\nНеправильний колір. Будь ласка, введіть один з наявних.");
            logger.warn("Неправильне введення кольору: {}", colorInput);
        }
    }
}
