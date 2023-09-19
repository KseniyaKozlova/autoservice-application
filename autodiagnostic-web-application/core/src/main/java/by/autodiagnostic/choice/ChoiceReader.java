package by.autodiagnostic.choice;

import by.autodiagnostic.distribution.SortChoice;

import java.util.List;

public interface ChoiceReader {

    List<SortChoice> readChoice(String step) throws ChoiceReaderException;
}