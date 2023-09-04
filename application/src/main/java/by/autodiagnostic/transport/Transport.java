package by.autodiagnostic.transport;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Transport transport = (Transport) o;
        return category == transport.category && Objects.equals(model, transport.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, model);
    }
}