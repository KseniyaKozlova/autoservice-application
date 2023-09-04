package by.autodiagnostic.menu.impl;

import by.autodiagnostic.menu.ConsoleInput;

import java.util.Scanner;

public class ConsoleInputReader implements ConsoleInput {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Override
    public String readLine() {
        return SCANNER.nextLine();
    }
}
