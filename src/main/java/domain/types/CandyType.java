package domain.types;

public enum CandyType {
    CHOCOLATE("Шоколадна"),
    TOFFEE("Іриска"),
    LOLLIPOP("Льодяник"),
    CARAMEL("Карамель");

    private final String candyName;
    CandyType(String candyName) {
        this.candyName = candyName;
    }

    @Override
    public String toString() {
        return candyName;
    }
}
