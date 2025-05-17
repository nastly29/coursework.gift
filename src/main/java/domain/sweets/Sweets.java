package domain.sweets;

public class Sweets {
    private int code;
    private String name;
    private double weight;
    private double sugarContent;
    private double price;
    private String type;
    private byte[] imageData;

    public Sweets(int code,String name, double weight,double sugarContent, double price,String type,byte[]imageData){
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.sugarContent = sugarContent;
        this.price = price;
        this.type = type;
        this.imageData=imageData;
    }

    public byte[] getImageData() { return imageData; }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public double getPrice() {return price;}

    public double getWeight() {return weight;}

    public int getCode() { return code; }

    public double getSugarContent() { return  sugarContent; }

    public String getSweetType() {return type;}

    public void setCode(int generatedCode) {this.code=generatedCode;}

    public void setWeight(double v) {this.weight=v;}

    public void setSugarContent(double v) {this.sugarContent=v;}

    public void setPrice(double v) {this.price=v;}

    public void setImageData(byte[] imageData) {this.imageData=imageData;}

    public void setType(String newType) {this.type=newType;}
}
