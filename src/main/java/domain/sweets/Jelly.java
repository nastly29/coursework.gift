package domain.sweets;

public class Jelly extends Sweets{
    private String fruityTaste;
    private String shape;

    public Jelly(int code, String name, double weight, double sugarContent,
                 double price, String fruityTaste, String shape, byte[] img) {
        super(code,name, weight, sugarContent, price,"jelly",img);
        this.fruityTaste=fruityTaste;
        this.shape=shape;
    }

    public String getShape() {return shape;}

    public String getFruityTaste() {return fruityTaste;}

    public void setFruityTaste(String text) {this.fruityTaste=text;}

    public void setShape(String text) {this.shape=text;}
}
