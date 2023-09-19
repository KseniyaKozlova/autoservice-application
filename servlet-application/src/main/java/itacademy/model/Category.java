package itacademy.model;

public enum Category {

    AUTOMOBILE( 20),
    MINIBUS( 30),
    MOTORBIKE( 10);

    private final Integer cost;

    Category(final Integer cost) {
        this.cost = cost;
    }

    public Integer getCost() {
        return cost;
    }
}