package gift.sweets;

public class Candy extends Sweets{
    private String filling;
    private String type;

    public Candy(int code,String name, double weight, double sugarContent,
                 double price, String filling, String type) {
        super(code,name, weight, sugarContent, price, "candy");
        this.filling=filling;
        this.type=type;
    }

    public String getFilling() {return filling;}

    public String getType() {return type;}

    public String toString() {
        return super.toString() + "| начинка:" + filling + "| вид:" + type;
    }
}
