package domain;

public class Package {
    private final String boxColor;
    private final String ribbonColor;
    private final String message;

    public Package(String boxColor, String ribbonColor, String message) {
        this.boxColor = boxColor;
        this.ribbonColor = ribbonColor;
        this.message = message;
    }

    public String getBoxColor() { return boxColor;}

    public String getRibbonColor(){return ribbonColor;}

    public String getMessage() {return message;}
}