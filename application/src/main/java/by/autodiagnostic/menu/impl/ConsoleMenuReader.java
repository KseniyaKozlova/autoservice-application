package by.autodiagnostic.menu.impl;

import by.autodiagnostic.distribution.Direction;
import by.autodiagnostic.distribution.SortChoice;
import by.autodiagnostic.distribution.SortType;
import by.autodiagnostic.menu.MenuReader;
import by.autodiagnostic.menu.MenuReaderException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenuReader implements MenuReader {

    private static final String MENU = """
            Please choose sorting condition separated by comma and space (', ') or 'stop':
            type - sort by type;
            model - sort by model;
            cost - sort by cost;
            -------------------
            asc - sort ascending;
            dsc - sort descending;
            -------------------
            stop - write file;
            """;

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public List<SortChoice> readChoice() throws MenuReaderException {
        try {
            final List<SortChoice> requirements = new ArrayList<>();

            while (true) {
                System.out.println(MENU);
                String step = scanner.nextLine();

                if (step.equals("stop")) {
                    System.out.println("The program is ending...");
                    break;
                }

                final SortChoice sortChoice = readSortingChoice(step);
                final var hasSorting = requirements.stream()
                        .anyMatch(requirement -> requirement.getSortType().equals(sortChoice.getSortType()));

                if (hasSorting) {
                    System.out.println("You have already chose this option, try again");
                } else {
                    requirements.add(sortChoice);
                }
            }
            return requirements;
        } catch (final IllegalArgumentException e) {
            throw new MenuReaderException("You entered incorrect sort meaning", e);
        }
    }

    private static SortChoice readSortingChoice(final String step) {
        final String[] sort = step.split(",\\s");
        return new SortChoice(SortType.valueOf(sort[0].toUpperCase()), Direction.valueOf(sort[1].toUpperCase()));
    }
}