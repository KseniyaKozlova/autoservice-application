package autodiagnostic;

public enum Category {
    AUTOMOBILE("автомобиль"),
    MINIBUS("микроавтобус"),
    MOTORBIKE("мотоцикл")
    ;

    private final String title;

    Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
