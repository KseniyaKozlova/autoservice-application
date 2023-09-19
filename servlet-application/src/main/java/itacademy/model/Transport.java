package itacademy.model;

import java.util.Objects;

public class Transport {

    private final Category category;
    private final String model;

    public Transport(final Category category, final String model) {
        this.category = category;
        this.model = model;
    }

    @Override
    public String toString() {
        return category.name().toLowerCase() + ", " + model + ", " + category.getCost();
    }

    @Override
    public boolean equals(final Object o) {
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
