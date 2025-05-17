package service;

import domain.Gift;
import domain.Package;
import domain.sweets.Sweets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GiftService{
    private static final Logger log = LoggerFactory.getLogger(GiftService.class);

    private final Gift gift;

    public GiftService(Gift gift) {
        this.gift = gift;
    }
    public void createOrUpdateGift(String name, Package pack) {
        gift.setName(name);
        gift.setPack(pack);
        log.info("Подарунок створено/оновлено: {}, упаковка: {}", name, pack);
    }

    //Повернути поточний подарунок
    public Gift getGift() {
        if (!exists()) {
            log.warn("Спроба звернення до неіснуючого подарунка");
            throw new IllegalStateException("Подарунок ще не створено!");
        }
        return gift;
    }

    public boolean exists() {
        return gift.getName() != null && gift.getPack() != null;
    }

    public void reset() {
        log.info("Подарунок скинуто");
        gift.setName(null);
        gift.setPack(null);
        gift.getSweets().clear();
    }

    //Додає солодощі до подарунка
    public void addSweetToGift(Sweets sweet) {
        gift.getSweets().add(sweet);
        log.info("Додано до подарунка: {}", sweet);
    }

    //Видаляє солодощі з подарунка
    public void removeSweet(Sweets s) {
        boolean removed = gift.getSweets().removeIf(x -> x.getCode() == s.getCode());
        if (removed) {
            log.info("Солодощі видалено з подарунка: {}", s);
        } else {
            log.warn("Спроба видалити солодощі, які не знайдено: {}", s);
        }
    }

    //Повертає список солодощів у подарунку
    public List<Sweets> listSweets() {
        return gift.getSweets().stream().distinct().collect(Collectors.toList());
    }

    //Повертає кількість однакових цукерок в подарунку
    public int getQuantity(Sweets s) {
        int qty = (int) gift.getSweets().stream().filter(x -> x.getCode() == s.getCode()).count();
        log.debug("Кількість солодощів [{}]: {}", s.getName(), qty);
        return qty;
    }

    //Зменшити кількість на 1 (або видалити, якщо лишилась 1)
    public void decreaseSweet(Sweets s) {
        boolean result = gift.getSweets().remove(s);
        if (result) {
            log.info("Кількість солодощів зменшено на 1: {}", s);
        } else {
            log.warn("Спроба зменшити кількість неіснуючого елемента: {}", s);
        }
    }

    //Загальна ціна
    public double totalPrice() {
        double price = gift.getSweets().stream().mapToDouble(Sweets::getPrice).sum();
        log.debug("Загальна ціна подарунка: {}", price);
        return price;
    }

    //Загальну вага
    public double totalWeight() {
        double weight = gift.getSweets().stream().mapToDouble(Sweets::getWeight).sum();
        log.debug("Загальна вага подарунка: {}", weight);
        return weight;
    }

    //Пошук за вмістом цукру
    public List<Sweets> findBySugar(double minSugar, double maxSugar) {
        List<Sweets> result = listSweets().stream().filter(s -> {
            double sc = s.getSugarContent();
            return sc >= minSugar && sc <= maxSugar;
        }).collect(Collectors.toList());
        log.info("Знайдено {} солодощів за вмістом цукру в межах {}–{}", result.size(), minSugar, maxSugar);
        return result;
    }

    //Сортування
    public List<Sweets> getSweetsSortedByWeight(boolean ascending) {
        List<Sweets> copy = new ArrayList<>(listSweets());
        copy.sort(Comparator.comparing(Sweets::getWeight));
        if (!ascending) Collections.reverse(copy);
        log.info("Солодощі відсортовано за вагою у порядку {}", ascending ? "зростання" : "спадання");
        return copy;
    }
}