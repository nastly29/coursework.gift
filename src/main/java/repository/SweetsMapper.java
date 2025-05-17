package repository;

import domain.sweets.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SweetsMapper {
    private static final Logger log = LoggerFactory.getLogger(SweetsMapper.class);

    public Sweets map(ResultSet rs) throws SQLException {
        int code = rs.getInt("Code");
        String type = rs.getString("TypeName");
        String name = rs.getString("Name");
        double price = rs.getDouble("Price");
        double weight = rs.getDouble("Weight");
        double sugar = rs.getDouble("SugarContent");
        byte[] img = rs.getBytes("ImageData");

        try {
            Sweets sweet = switch (type.toLowerCase()) {
                case "candy" -> new Candy(code, name, weight, sugar, price,
                        rs.getString("Filling"),
                        rs.getString("CandyType"),
                        img);
                case "chocolate" -> new Chocolate(code, name, weight, sugar, price,
                        rs.getDouble("CocoaPercent"),
                        rs.getString("ChocFill"),
                        rs.getString("ChocolateType"),
                        img);
                case "jelly" -> new Jelly(code, name, weight, sugar, price,
                        rs.getString("FruityTaste"),
                        rs.getString("JellyShape"),
                        img);
                case "gingerbread" -> new Gingerbread(code, name, weight, sugar, price,
                        rs.getString("GingerbreadShape"),
                        rs.getBoolean("IsIced"),
                        img);
                default -> throw new IllegalArgumentException("Невідомий вид солодощів: " + type);
            };

            log.debug("Зчитано об'єкт {} з кодом {}", sweet.getClass().getSimpleName(), sweet.getCode());
            return sweet;

        } catch (Exception e) {
            log.warn("Помилка під час мапінгу солодощів з кодом {}: {}", code, e.getMessage());
            throw e;
        }
    }
}