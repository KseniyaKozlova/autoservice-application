package by.autodiagnostic.menu;

import by.autodiagnostic.distribution.SortChoice;

import java.util.List;

public interface MenuReader {

    List<SortChoice> readChoice() throws MenuReaderException;
}