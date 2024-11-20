package gift.types;

public enum CandyType {
    CHOCOLATE("Шоколадна"),
    TOFFEE("Іриска"),
    LOLLIPOP("Льодяник"),
    CARAMEL("Карамель");

    private final String candyName;

    CandyType(String candyName) {
        this.candyName = candyName;
    }

    public String getCandyName() {
        return candyName;
    }

    @Override
    public String toString() {
        return candyName;
    }
}
