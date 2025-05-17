package domain.sweets;

public class Chocolate extends Sweets{
    private double cocoaPercentage;
    private String filling;
    private String type;

    public Chocolate(int code, String name, double weight, double sugarContent,
                     double price, double cocoaPercentage, String filling, String type, byte[] img) {
        super(code,name, weight, sugarContent, price, "chocolate",img);
        this.cocoaPercentage=cocoaPercentage;
        this.filling=filling;
        this.type=type;
    }

    public double getCocoaPercentage() {return  cocoaPercentage;}

    public String getFilling() {return filling;}

    public String getType() {return type;}

    public void setCocoaPercentage(double v) {this.cocoaPercentage=v;}

    public void setFilling(String text) {this.filling=text;}

    public void setType(String value) {this.type=value;}
}
