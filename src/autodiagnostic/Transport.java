package autodiagnostic;

import java.util.LinkedHashMap;

public class Transport implements Comparable<LinkedHashMap<String, Object>> {
    String type;
    String model;
    Integer cost;

    public Transport(String type, String model) {
        this.type = type;
        this.model = model;
    }

    public Transport(String type, String model, Integer cost) {
        this.type = type;
        this.model = model;
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return type + ", " + model;
    }

    @Override
    public int compareTo(LinkedHashMap<String, Object> o) {
        if (o == null) {
            return 0;
        }
        int val = 0;
        for (var entry : o.entrySet()) {
            Object value = entry.getValue();
            try {
                switch (entry.getKey()) {
                    case ("11") -> {
                        val = this.type.compareTo((String) value);
                        if (val != 0) {
                            return val;
                        }
                    }
                    case ("12") -> {
                        val = this.type.compareTo((String) value);
                        if (val != 0) {
                            return val * -1;
                        }
                    }
                    case ("21") -> {
                        val = this.model.compareTo((String) value);
                        if (val != 0) {
                            return val;
                        }
                    }
                    case ("22") -> {
                        val = this.model.compareTo((String) value);
                        if (val != 0) {
                            return val * -1;
                        }
                    }
                    case ("31") -> {
                        val = this.cost.compareTo((Integer) value);
                        if (val != 0) {
                            return val;
                        }
                    }
                    case ("32") -> {
                        val = this.cost.compareTo((Integer) value);
                        if (val != 0) {
                            return val * -1;
                        }
                    }
                }
            } catch (ClassCastException e) {
                System.out.println("unright meaning");
            }
        }
        return val;
    }
}

