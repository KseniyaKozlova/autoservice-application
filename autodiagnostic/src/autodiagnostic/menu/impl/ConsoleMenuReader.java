package autodiagnostic.menu.impl;

import autodiagnostic.distribution.Direction;
import autodiagnostic.distribution.SortChoice;
import autodiagnostic.distribution.SortType;
import autodiagnostic.menu.MenuChoiceException;
import autodiagnostic.menu.MenuReader;

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

    public List<SortChoice> readChoice() throws MenuChoiceException {
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
                if (requirements.stream()
                        .anyMatch(requirement -> requirement.getSortType().equals(sortChoice.getSortType()))) {
                    System.out.println("You have already chose this option, try again");
                } else {
                    requirements.add(sortChoice);
                }
            }
            return requirements;
        } catch (final IllegalArgumentException e) {
            throw new MenuChoiceException("You entered incorrect sort meaning", e);
        }
    }

    private static SortChoice readSortingChoice(final String step) {
        final String[] sort = step.split(",\\s");
        return new SortChoice(SortType.valueOf(sort[0].toUpperCase()), Direction.valueOf(sort[1].toUpperCase()));
    }
}