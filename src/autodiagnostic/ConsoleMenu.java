package autodiagnostic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final Work work = new Work();
    private final List<Transport> transportList = work.checkType();
    private final LinkedHashMap<String, Object> choice = new LinkedHashMap<>();
    private static final File FILE = Path.of("resources", "processed-transport.txt").toFile();

    public void startMenu() {
        String step;
        do {
            System.out.println("""
                    Please choose 2 position without separation symbols (or exit):
                    1 - sort by type;
                    2 - sort by model;
                    3 - sort by cost;
                    -------------------
                    1 - sort ascending;
                    2 - sort descending;
                    -------------------
                    0 - write file;
                    """);
            step = scanner.nextLine();
            haveResult(step);
        } while (!step.equals("0"));
    }

    private void haveResult(String step) {
        transportList.sort((h1, h2) -> {
            int result = 0;
            switch (step) {
                case "11", "12" -> choice.put(step, h2.getType());
                case "21", "22" -> choice.put(step, h2.getModel());
                case "31", "32" -> choice.put(step, h2.getCost());
                case "0" -> writeFile();
            }
            return h1.compareTo(choice);
        });
        transportList.forEach(System.out::println);
    }

    private void writeFile() {
        try (FileWriter sortedTransport = new FileWriter(FILE)) {
            for (Transport transports : transportList) {
                String type = transports.getType();
                String model = transports.getModel();
                Integer cost = transports.getCost();
                sortedTransport.write(type + "|" + model + "|" + cost + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}