package menu.helpers;
import gift.sweets.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SweetBaseHelper {
    private static String SWEETS_DATABASE_FILE = "sweets_database.txt";

    public static void setDatabaseFile(String filePath) {
        SWEETS_DATABASE_FILE = filePath;
    }

    public static List<Sweets> loadSweetsFromFile() {
        List<Sweets> sweetsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(SWEETS_DATABASE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Sweets sweet = parseSweetFromLine(line);
                    if (sweet != null) {
                        sweetsList.add(sweet);
                    }
                } catch (Exception e) {
                    System.out.println("Не вдалося обробити рядок: " + line + ". Причина: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Помилка читання з файлу: " + e.getMessage());
        }

        if (sweetsList.isEmpty()) {
            System.out.println("Файл бази даних порожній.");
        }
        return sweetsList;
    }

    public static Sweets parseSweetFromLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 6) {
            System.out.println("Неправильний формат рядка: " + line);
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
                    System.out.println("Невідомий тип солодощів: " + generalSweets.getSweetType());
                    yield null;
                }
            };
        } catch (Exception e) {
            System.out.println("Помилка розбору рядка: " + line + " - " + e.getMessage());
            return null;
        }
    }

    // Метод для розбору загальних властивостей
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
            System.out.println("Помилка розбору загальних властивостей: " + e.getMessage());
            return null;
        }
    }

    // Метод для розбору Candy
    public static Candy parseCandy(String[] parts, Sweets generalSweets) {
        try {
            String candyFilling = parts[6].split(":")[1].trim();
            String candyType = parts[7].split(":")[1].trim();
            return new Candy(generalSweets.getCode(), generalSweets.getName(), generalSweets.getWeight(),
                    generalSweets.getSugarContent(), generalSweets.getPrice(), candyFilling, candyType);
        } catch (Exception e) {
            System.out.println("Помилка розбору цукерок: " + e.getMessage());
            return null;
        }
    }

    // Метод для розбору Chocolate
    public static Chocolate parseChocolate(String[] parts, Sweets generalSweets) {
        try {
            double cocoaPercentage = Double.parseDouble(parts[6].split(":")[1].trim());
            String chocolateFilling = parts[7].split(":")[1].trim();
            String chocolateType = parts[8].split(":")[1].trim();
            return new Chocolate(generalSweets.getCode(), generalSweets.getName(), generalSweets.getWeight(),
                    generalSweets.getSugarContent(), generalSweets.getPrice(), cocoaPercentage, chocolateFilling, chocolateType);
        } catch (Exception e) {
            System.out.println("Помилка розбору шоколаду: " + e.getMessage());
            return null;
        }
    }

    // Метод для розбору Jelly
    public static Jelly parseJelly(String[] parts, Sweets generalSweets) {
        try {
            String fruityTaste = parts[6].split(":")[1].trim();
            String jellyShape = parts[7].split(":")[1].trim();
            return new Jelly(generalSweets.getCode(), generalSweets.getName(), generalSweets.getWeight(),
                    generalSweets.getSugarContent(), generalSweets.getPrice(), fruityTaste, jellyShape);
        } catch (Exception e) {
            System.out.println("Помилка розбору мармеладу: " + e.getMessage());
            return null;
        }
    }

    // Метод для розбору Gingerbread
    public static Gingerbread parseGingerbread(String[] parts, Sweets generalSweets) {
        try {
            String gingerbreadShape = parts[6].split(":")[1].trim();
            boolean isIced = parts[7].split(":")[1].trim().equalsIgnoreCase("так");
            return new Gingerbread(generalSweets.getCode(), generalSweets.getName(), generalSweets.getWeight(),
                    generalSweets.getSugarContent(), generalSweets.getPrice(), gingerbreadShape, isIced);
        } catch (Exception e) {
            System.out.println("Помилка розбору пряників: " + e.getMessage());
            return null;
        }
    }

    //Метод для запису у файл
    private static void writeToFile(List<Sweets> sweetsList, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SWEETS_DATABASE_FILE, append))) {
            for (Sweets sweet : sweetsList) {
                writer.write(sweet.toString());
                writer.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Помилка запису у файл: " + e.getMessage());
        }
    }

    //Метод для перезапису вмісту файла
    public static void updateContent(List<Sweets> sweetsList) {
        writeToFile(sweetsList, false);
    }

    public static void saveSweet(Sweets sweet) {
        List<Sweets> singleSweetList =  new ArrayList<>();
        singleSweetList.add(sweet);
        writeToFile(singleSweetList, true);
    }
}
