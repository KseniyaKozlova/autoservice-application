package by.autodiagnostic.choice.impl;

import by.autodiagnostic.choice.ChoiceReader;
import by.autodiagnostic.choice.ChoiceReaderException;
import by.autodiagnostic.distribution.Direction;
import by.autodiagnostic.distribution.SortChoice;
import by.autodiagnostic.distribution.SortType;

import java.util.ArrayList;
import java.util.List;

public class InputChoiceReader implements ChoiceReader {

    @Override
    public List<SortChoice> readChoice(final String step) throws ChoiceReaderException {
        try {
            final List<SortChoice> requirements = new ArrayList<>();

            if (step != null) {
                final String[] sort = step.split(",\\s");
                final SortChoice sortChoice = new SortChoice(SortType.valueOf(sort[0].toUpperCase()),
                        Direction.valueOf(sort[1].toUpperCase()));
                requirements.add(sortChoice);
            }
            return requirements;
        } catch (final IllegalArgumentException e) {
            throw new ChoiceReaderException("You entered incorrect sort meaning", e);
        }
    }
}