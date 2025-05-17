package domain.sweets;

public class Candy extends Sweets{
    private String filling;
    private String type;

    public Candy(int code, String name, double weight, double sugarContent,
                 double price, String filling, String type, byte[] img) {
        super(code,name, weight, sugarContent, price, "candy",img);
        this.filling=filling;
        this.type=type;
    }

    public String getFilling() {return filling;}

    public String getType() {return type;}

    public void setFilling(String text) {this.filling=text;}

    public void setType(String value) {this.type=value;}
}
