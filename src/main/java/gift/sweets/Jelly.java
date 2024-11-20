package gift.sweets;

public class Jelly extends Sweets{
    private String fruityTaste;
    private String shape;

    public Jelly(int code,String name, double weight, double sugarContent,
                 double price, String fruityTaste, String shape) {
        super(code,name, weight, sugarContent, price,"jelly");
        this.fruityTaste=fruityTaste;
        this.shape=shape;
    }

    public String getShape() {return shape;}

    public String getFruityTaste() {return fruityTaste;}

    public String toString() {
        return super.toString() + "| смак:" + fruityTaste + "| форма:" + shape;
    }
}
