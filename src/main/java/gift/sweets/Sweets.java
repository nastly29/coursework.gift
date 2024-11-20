package gift.sweets;

public class Sweets {
    private int code;
    private String name;
    private double weight;
    private double sugarContent;
    private double price;
    private String type;

    public Sweets(int code,String name, double weight,double sugarContent, double price,String type){
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.sugarContent = sugarContent;
        this.price = price;
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public double getPrice() {return price;}

    public double getWeight() {return weight;}

    public int getCode() { return code; }

    public double getSugarContent() { return  sugarContent; }

    public String getSweetType() {return type;}

    public String toString() {
        return code +
                "| тип:" + type +
                "| назва:" + name +
                "| ціна(грн):" + price +
                "| вага(г):" + weight +
                "| рівень цукру(%):" + sugarContent;
    }
}
