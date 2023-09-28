package by.itacademy.model;

public class Transport {

    private final Model model;
    private final Type type;
    private final Client client;

    public Transport(final Model model, final Type type, final Client client) {
        this.model = model;
        this.type = type;
        this.client = client;
    }

    public Model getModel() {
        return model;
    }

    public Type getType() {
        return type;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "model=" + model +
                ", type=" + type +
                ", client=" + client +
                '}';
    }
}
