package menu.helpers;

import gift.sweets.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SweetBaseHelper {
    private static final Logger logger = LogManager.getLogger(SweetBaseHelper.class);
    private static final Logger errorLogger = LogManager.getLogger("ErrorLogger");

    private static String SWEETS_DATABASE_FILE = "sweets_database.txt";

    public static void setDatabaseFile(String filePath) {
        SWEETS_DATABASE_FILE = filePath;
        logger.info("Файл бази даних оновлено: {}", SWEETS_DATABASE_FILE);
    }

    public static List<Sweets> loadSweetsFromFile() {
        List<Sweets> sweetsList = new ArrayList<>();

        logger.info("Завантаження солодощів з файлу: {}", SWEETS_DATABASE_FILE);

        try (BufferedReader reader = new BufferedReader(new FileReader(SWEETS_DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Sweets sweet = parseSweetFromLine(line);
                    if (sweet != null) {
                        sweetsList.add(sweet);
                    }
                } catch (Exception e) {
                    logger.error("Не вдалося обробити рядок: {}. Причина: {}", line, e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            logger.error("Помилка читання з файлу: {}", SWEETS_DATABASE_FILE, e);
        }

        if (sweetsList.isEmpty()) {
            logger.warn("Файл бази даних порожній.");
        }
        return sweetsList;
    }

    public static Sweets parseSweetFromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) {
            logger.warn("Неправильний формат рядка: {}", line);
            return null;
        }

        try {
            Sweets generalSweets = parseCommonProperties(parts);
            if (generalSweets == null) return null;

            return switch (generalSweets.getSweetType().toLowerCase()) {
                case "candy" -> parseCandy(parts, generalSweets);
                case "chocolate" -> parseChocolate(parts, generalSweets);
                case "jelly" -> parseJelly(parts, generalSweets);
                case "gingerbread" -> parseGingerbread(parts, generalSweets);
                default -> {
                    logger.warn("Невідомий тип солодощів: {}", generalSweets.getSweetType());
                    yield null;
                }
            };
        } catch (Exception e) {
            logger.error("Помилка розбору рядка: {}. Причина: {}", line, e.getMessage(), e);
            return null;
        }
    }

    private static Sweets parseCommonProperties(String[] parts) {
        try {
            int code = Integer.parseInt(parts[0].trim());
            String type = parts[1].split(":")[1].trim();
            String name = parts[2].split(":")[1].trim();
            double price = Double.parseDouble(parts[3].split(":")[1].trim());
            double weight = Double.parseDouble(parts[4].split(":")[1].trim());
            double sugarContent = Double.parseDouble(parts[5].split(":")[1].trim());

            return new Sweets(code, name, weight, sugarContent, price, type);
        } catch (Exception e) {
            logger.error("Помилка розбору загальних властивостей: {}", e.getMessage(), e);
            return null;
        }
    }

    public static Candy parseCandy(String[] parts, Sweets generalSweets) {
        try {
            String candyFilling = parts[6].split(":")[1].trim();
            String candyType = parts[7].split(":")[1].trim();
            return new Candy(generalSweets.getCode(), generalSweets.getName(), generalSweets.getWeight(),
                    generalSweets.getSugarContent(), generalSweets.getPrice(), candyFilling, candyType);
        } catch (Exception e) {
            logger.error("Помилка розбору цукерок: {}", e.getMessage(), e);
            return null;
        }
    }
    public static Chocolate parseChocolate(String[] parts, Sweets generalSweets) {
        try {
            double cocoaPercentage = Double.parseDouble(parts[6].split(":")[1].trim());
            String chocolateFilling = parts[7].split(":")[1].trim();
            String chocolateType = parts[8].split(":")[1].trim();
            return new Chocolate(generalSweets.getCode(), generalSweets.getName(), generalSweets.getWeight(),
                    generalSweets.getSugarContent(), generalSweets.getPrice(), cocoaPercentage, chocolateFilling, chocolateType);
        } catch (Exception e) {
            logger.error("Помилка розбору шоколаду: {}", e.getMessage(), e);
            return null;
        }
    }

    public static Jelly parseJelly(String[] parts, Sweets generalSweets) {
        try {
            String fruityTaste = parts[6].split(":")[1].trim();
            String jellyShape = parts[7].split(":")[1].trim();
            return new Jelly(generalSweets.getCode(), generalSweets.getName(), generalSweets.getWeight(),
                    generalSweets.getSugarContent(), generalSweets.getPrice(), fruityTaste, jellyShape);
        } catch (Exception e) {
            logger.error("Помилка розбору мармеладу: {}", e.getMessage(), e);
            return null;
        }
    }

    public static Gingerbread parseGingerbread(String[] parts, Sweets generalSweets) {
        try {
            String gingerbreadShape = parts[6].split(":")[1].trim();
            boolean isIced = parts[7].split(":")[1].trim().equalsIgnoreCase("так");
            return new Gingerbread(generalSweets.getCode(), generalSweets.getName(), generalSweets.getWeight(),
                    generalSweets.getSugarContent(), generalSweets.getPrice(), gingerbreadShape, isIced);
        } catch (Exception e) {
            logger.error("Помилка розбору пряника: {}", e.getMessage(), e);
            return null;
        }
    }

    private static void writeToFile(List<Sweets> sweetsList, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SWEETS_DATABASE_FILE, append))) {
            for (Sweets sweet : sweetsList) {
                writer.write(sweet.toString());
                writer.write("\n");
            }
        } catch (IOException e) {
            logger.error("Помилка запису у файл: {}", SWEETS_DATABASE_FILE, e);
        }
    }

    public static void updateContent(List<Sweets> sweetsList) {
        writeToFile(sweetsList, false);
        logger.info("Вміст бази даних оновлено.");
    }

    public static void saveSweet(Sweets sweet) {
        List<Sweets> singleSweetList = new ArrayList<>();
        singleSweetList.add(sweet);
        writeToFile(singleSweetList, true);
        logger.info("Солодощі збережено до бази даних: {}", sweet);
    }
}