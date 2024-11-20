package gift;

import gift.sweets.Sweets;

import java.util.ArrayList;
import java.util.List;

public class Gift {
    public static Gift instance;

    private String name;
    private Package pack;
    private List<Sweets> sweets;

    public Gift(String name, Package pack) {
        this.name = name;
        this.pack = pack;
        this.sweets = new ArrayList<>();
    }

    public static Gift getInstance(String name, Package pack) {
        if(instance == null){
            instance = new Gift(name,pack);
        }
        return instance;
    }

    public static boolean exists() {
        return instance != null;
    }

    public void addSweet(Sweets sweet) {
        sweets.add(sweet);
    }

    public List<Sweets> getSweets() {return sweets;}

    public String getName() { return name;}

    public Package getPackage() { return pack;}

    public String toString() {
        StringBuilder result = new StringBuilder(name + "\n");
        result.append("Пакування: ").append(pack).append("\n");
        result.append("Вміст:\n");
        for (Sweets sweet : sweets) {
            result.append(sweet).append("\n");
        }
        return result.toString();
    }
}
