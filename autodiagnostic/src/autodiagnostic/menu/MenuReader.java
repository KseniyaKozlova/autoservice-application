package autodiagnostic.menu;

import autodiagnostic.distribution.SortChoice;

import java.util.List;

public interface MenuReader {

    List<SortChoice> readChoice() throws MenuChoiceException;
}
