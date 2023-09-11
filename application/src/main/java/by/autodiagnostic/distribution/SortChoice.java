package by.autodiagnostic.distribution;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SortChoice that = (SortChoice) o;
        return sortType == that.sortType && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sortType, direction);
    }
}
