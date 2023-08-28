package autodiagnostic.transport;

public class Transport {

    private final Category category;
    private final String model;

    public Transport(final Category category, final String model) {
        this.category = category;
        this.model = model;
    }

    public String getType() {
        return category.getTitle();
    }

    public Integer getPrice() {
        return category.getCost();
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return category.getTitle() + ", " + model;
    }
}