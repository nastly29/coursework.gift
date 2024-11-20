package gift;

public class Package {
    private String boxColor;
    private String ribbonColor;
    private String message;

    public Package(String boxColor, String ribbonColor, String message) {
        this.boxColor = boxColor;
        this.ribbonColor = ribbonColor;
        this.message = message;
    }

    public String getBoxColor() {return boxColor;}

    public String getRibbonColor() {return ribbonColor;}

    public String getMessage() {return message;}

    public String toString() {
        return "Колір коробки: " + boxColor + ", Колір стрічки: " + ribbonColor + ", Записка: " + message;
    }
}
