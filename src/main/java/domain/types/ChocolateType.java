package domain.types;

public enum ChocolateType {
    DARK("Чорний"),
    MILK("Молочний"),
    WHITE("Білий");

    private final String chocolateName;
    ChocolateType(String chocolateName) {
        this.chocolateName = chocolateName;
    }

    @Override
    public String toString() {
        return chocolateName;
    }
}
