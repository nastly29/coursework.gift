package gift.sweets;

public class Gingerbread extends Sweets{
    private String shape;
    private boolean isIced;

    public Gingerbread(int code,String name, double weight, double sugarContent,
                       double price, String shape, boolean isIced) {
        super(code,name, weight, sugarContent, price,"gingerbread");
        this.isIced=isIced;
        this.shape=shape;
    }

    public String getShape() {return shape;}

    public boolean isIced() {return isIced;}

    public String toString() {
        return super.toString() + "| форма:" + shape + "| глазурований:" + (isIced ? "так" : "ні");
    }
}
