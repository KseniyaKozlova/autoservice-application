package autodiagnostic;

import java.nio.file.Path;

public class Main {

    public static void main(String[] args) {
        ConsoleMenu consoleMenu = new ConsoleMenu();
        consoleMenu.startMenu();
        System.out.println(Path.of("resources", "processed-transport.txt").toAbsolutePath());

    }
}
