package repository;

import domain.sweets.*;
import domain.sweets.Sweets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class SweetsRepository {
    private static final SweetsMapper mapper = new SweetsMapper();
    private static final Logger log = LoggerFactory.getLogger(SweetsRepository.class);

    // Завантажуємо всі SQL-запити з properties
    private static final Properties sql = new Properties();
    static {
        try (InputStream in = SweetsRepository.class
                .getResourceAsStream("/sql/queries.properties")) {
            sql.load(in);
        } catch (IOException e) {
            log.error("Не вдалося завантажити SQL-запити з properties", e);
            throw new ExceptionInInitializerError("Помилка: " + e.getMessage());
        }
    }

    // Завантаження всіх солодощів з бази даних
    public static List<Sweets> loadSweetsFromDb() {
        List<Sweets> list = new ArrayList<>();
        String q = sql.getProperty("load.all");

        try (Connection c = DbUtil.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(q)) {
            while (rs.next()) {
                list.add(mapper.map(rs));
            }
            log.info("Завантажено {} солодощів з БД", list.size());
        } catch (SQLException e) {
            log.warn("Помилка завантаження солодощів", e);
        }
        return list;
    }

    // Додавання солодощів до бази даних
    public static boolean saveSweetToDb(Sweets sweet) {
        String insertBase = sql.getProperty("insert.sweet");

        try (Connection conn = DbUtil.getConnection()) {
            conn.setAutoCommit(false);
            int generatedCode;
            try (PreparedStatement ps = conn.prepareStatement(insertBase, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, sweet.getSweetType());
                ps.setString(2, sweet.getName());
                ps.setDouble(3, sweet.getPrice());
                ps.setDouble(4, sweet.getWeight());
                ps.setDouble(5, sweet.getSugarContent());
                byte[] img = sweet.getImageData();
                if (img != null) ps.setBytes(6, img);
                else ps.setNull(6, Types.VARBINARY);
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        generatedCode = keys.getInt(1);
                        sweet.setCode(generatedCode);
                    } else {
                        throw new SQLException("Не вдалося отримати згенерований ключ (Code)");
                    }
                }
            }
            // Вставляємо деталі залежно від типу
            switch (sweet.getSweetType().toLowerCase()) {
                case "candy" -> insertCandyDetails(conn, sweet.getCode(), (Candy) sweet);
                case "chocolate"   -> insertChocolateDetails(conn, sweet.getCode(), (Chocolate) sweet);
                case "jelly"       -> insertJellyDetails(conn, sweet.getCode(), (Jelly) sweet);
                case "gingerbread" -> insertGingerbreadDetails(conn, sweet.getCode(), (Gingerbread) sweet);
                default -> throw new IllegalArgumentException("Невідомий вид солодощів: " + sweet.getSweetType());
            }
            conn.commit();
            log.info("Солодощі успішно збережено в БД: {}", sweet);
            return true;
        } catch (SQLException e) {
            log.warn("Помилка збереження солодощів в БД: {}", e.getMessage(), e);
            return false;
        }
    }

    private static void insertCandyDetails(Connection conn, int code, Candy candy) throws SQLException {
        String insertCandy =  sql.getProperty("insert.candyDetails");
        try (PreparedStatement ps = conn.prepareStatement(insertCandy)) {
            ps.setInt(1, code);
            ps.setString(2, candy.getFilling());
            ps.setString(3, candy.getType());
            ps.executeUpdate();
        }
    }

    private static void insertChocolateDetails(Connection conn, int code, Chocolate chocolate) throws SQLException {
        String insertChocolate = sql.getProperty("insert.chocolateDetails");
        try (PreparedStatement ps = conn.prepareStatement(insertChocolate)) {
            ps.setInt(1, code);
            ps.setDouble(2, chocolate.getCocoaPercentage());
            ps.setString(3, chocolate.getFilling());
            ps.setString(4, chocolate.getType());
            ps.executeUpdate();
        }
    }

    private static void insertGingerbreadDetails(Connection conn, int code, Gingerbread gingerbread) throws SQLException {
        String insertGingerbread = sql.getProperty("insert.gingerbreadDetails");
        try (PreparedStatement ps = conn.prepareStatement(insertGingerbread)) {
            ps.setInt(1, code);
            ps.setString(2, gingerbread.getShape());
            ps.setBoolean(3, gingerbread.isIced());
            ps.executeUpdate();
        }
    }

    private static void insertJellyDetails(Connection conn, int code, Jelly jelly) throws SQLException {
        String insertJelly = sql.getProperty("insert.jellyDetails");
        try (PreparedStatement ps = conn.prepareStatement(insertJelly)) {
            ps.setInt(1, code);
            ps.setString(2, jelly.getFruityTaste());
            ps.setString(3, jelly.getShape());
            ps.executeUpdate();
        }
    }

    // Видалення солодощів з бази даних
    public static boolean deleteSweetFromDb(int code) throws SQLException {
        String[] keys = {
                "delete.candyDetails", "delete.chocolateDetails",
                "delete.jellyDetails", "delete.gingerbreadDetails"
        };
        try (Connection conn = DbUtil.getConnection()) {
            conn.setAutoCommit(false);
            for (String k : keys) {
                try (PreparedStatement ps = conn.prepareStatement(sql.getProperty(k))) {
                    ps.setInt(1, code);
                    ps.executeUpdate();
                }
            }
            int rowsDeleted;
            try (PreparedStatement ps = conn.prepareStatement(sql.getProperty("delete.base"))) {
                ps.setInt(1, code);
                rowsDeleted = ps.executeUpdate();
            }
            conn.commit();
            log.info("Солодощі з кодом {} видалено: {}", code, rowsDeleted > 0);
            return rowsDeleted > 0;
        } catch (SQLException ex) {
            log.warn("Помилка видалення солодощів (code={}): {}", code, ex.getMessage(), ex);
            throw ex;
        }
    }

    // Пошук солодощів за ціною в базі
    public static List<Sweets> findSweetsByPrice(double minPrice, double maxPrice) {
        String q = sql.getProperty("find.by.price");
        List<Sweets> result = new ArrayList<>();
        try (Connection c = DbUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(q)) {
            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()){
                    result.add(mapper.map(rs));
                }
                log.info("Знайдено {} солодощів у діапазоні {}–{}", result.size(), minPrice, maxPrice);
            }
        } catch (SQLException e) {
            log.warn("Помилка пошуку солодощів за ціною: {}", e.getMessage(), e);
        }
        return result;
    }

    //Редагування
    public static boolean updateSweetInDb(Sweets sweet) {
        String updateBase = sql.getProperty("update.sweet");
        try (Connection conn = DbUtil.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(updateBase)) {
                ps.setString(1, sweet.getSweetType());
                ps.setString(2, sweet.getName());
                ps.setDouble(3, sweet.getPrice());
                ps.setDouble(4, sweet.getWeight());
                ps.setDouble(5, sweet.getSugarContent());
                byte[] img = sweet.getImageData();
                if (img != null) ps.setBytes(6, img);
                else ps.setNull(6, Types.VARBINARY);
                ps.setInt(7, sweet.getCode());
                ps.executeUpdate();
            }catch (SQLException ex) {
                conn.rollback();
                log.warn("Помилка оновлення базових даних солодощів: {}", ex.getMessage(), ex);
                return false;
            }
            switch (sweet.getSweetType().toLowerCase()) {
                case "candy" -> updateCandyDetails(conn, sweet.getCode(), (Candy) sweet);
                case "chocolate" -> updateChocolateDetails(conn, sweet.getCode(), (Chocolate) sweet);
                case "jelly" -> updateJellyDetails(conn, sweet.getCode(), (Jelly) sweet);
                case "gingerbread" -> updateGingerbreadDetails(conn, sweet.getCode(), (Gingerbread) sweet);
            }
            conn.commit();
            log.info("Солодощі оновлено: {}", sweet);
            return true;
        } catch (SQLException ex) {
            log.warn("Помилка оновлення солодощів: {}", ex.getMessage(), ex);
            return false;
        }
    }

    private static void updateCandyDetails(Connection conn, int code, Candy c) throws SQLException {
        String q = sql.getProperty("update.candyDetails");
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, c.getFilling());
            ps.setString(2, c.getType());
            ps.setInt(3, code);
            ps.executeUpdate();
        }
    }

    private static void updateChocolateDetails(Connection conn, int code, Chocolate ch) throws SQLException {
        String q = sql.getProperty("update.chocolateDetails");
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setDouble(1, ch.getCocoaPercentage());
            ps.setString(2, ch.getFilling());
            ps.setString(3, ch.getType());
            ps.setInt(4, code);
            ps.executeUpdate();
        }
    }

    private static void updateJellyDetails(Connection conn, int code, Jelly j) throws SQLException {
        String q = sql.getProperty("update.jellyDetails");
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, j.getFruityTaste());
            ps.setString(2, j.getShape());
            ps.setInt(3, code);
            ps.executeUpdate();
        }
    }

    private static void updateGingerbreadDetails(Connection conn, int code, Gingerbread g) throws SQLException {
        String q = sql.getProperty("update.gingerbreadDetails");
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, g.getShape());
            ps.setBoolean(2, g.isIced());
            ps.setInt(3, code);
            ps.executeUpdate();
        }
    }
}