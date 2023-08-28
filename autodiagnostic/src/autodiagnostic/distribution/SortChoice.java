package autodiagnostic.distribution;

public class SortChoice {

    private final SortType sortType;
    private final Direction direction;

    public SortChoice(final SortType sortType, final Direction direction) {
        this.sortType = sortType;
        this.direction = direction;
    }

    public SortType getSortType() {
        return sortType;
    }

    public Direction getDirection() {
        return direction;
    }
}
