package domain.sweets;

public class Gingerbread extends Sweets{
    private String shape;
    private boolean isIced;

    public Gingerbread(int code, String name, double weight, double sugarContent,
                       double price, String shape, boolean isIced, byte[] img) {
        super(code,name, weight, sugarContent, price,"gingerbread",img);
        this.isIced=isIced;
        this.shape=shape;
    }

    public String getShape() {return shape;}

    public boolean isIced() {return isIced;}

    public void setShape(String text) {this.shape=text;}

    public void setIced(boolean selected) {this.isIced=selected;}
}
