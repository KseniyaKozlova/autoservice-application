package by.autodiagnostic.transport;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Category {

    AUTOMOBILE("автомобиль", 20),
    MINIBUS("микроавтобус", 30),
    MOTORBIKE("мотоцикл", 10);

    private static final Map<String, Category> CATEGORY_MAP = Arrays.stream(Category.values())
            .collect(Collectors.toMap(translation -> translation.title, Function.identity()));
    private final String title;
    private final Integer cost;


    Category(final String title, final Integer cost) {
        this.title = title;
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCost() {
        return cost;
    }

    public static Category getCategoryByTitle(final String name) throws CategoryException {
        final Category category = CATEGORY_MAP.get(name);

        if (category != null) {
            return category;
        } else {
            throw new CategoryException("File contains nonexistent category: " + name);
        }
    }
}
