package by.autodiagnostic.transport;

import by.autodiagnostic.annotation.JSONField;
import by.autodiagnostic.annotation.PatternValidator;

import java.util.Objects;

public class Transport {

    @JSONField(name = "type")
    private Category category;

    @PatternValidator(pattern = "^[a-zA-Z][a-zA-Z0-9-\\s]+[0-9]|[a-zA-Z]$")
    @JSONField(name = "model")
    private String model;

    public Transport(final Category category, final String model) {
        this.category = category;
        this.model = model;
    }

    public Transport() {
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
        return category + ", " + model;
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