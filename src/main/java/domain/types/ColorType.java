package domain.types;

public enum ColorType {
    RED("Червоний"),
    GREEN("Зелений"),
    BLUE("Синій"),
    YELLOW("Жовтий"),
    WHITE("Білий"),
    BLACK("Чорний"),
    ORANGE("Помаранчевий"),
    PURPLE("Фіолетовий"),
    PINK("Рожевий");

    private final String colorName;
    ColorType(String colorName) {
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }

    @Override
    public String toString() {
        return colorName;
    }
}
