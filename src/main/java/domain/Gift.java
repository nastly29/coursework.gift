package domain;

import domain.sweets.Sweets;
import java.util.ArrayList;
import java.util.List;

public class Gift {
    private String name;
    private Package pack;
    private final  List<Sweets> sweets = new ArrayList<>();

    public Gift(){}

    public List<Sweets> getSweets() {return sweets;}

    public String getName() { return name;}

    public Package getPack() {
        return pack;
    }

    public void setName(String name) {this.name = name;}

    public void setPack(Package pack) {this.pack = pack;}
}