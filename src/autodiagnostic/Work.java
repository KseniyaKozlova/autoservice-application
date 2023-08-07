package autodiagnostic;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Work {

    private static final File FILE = Path.of("resources", "transport.txt").toFile();
    private static final File FILE1 = Path.of("resources", "invalid transport.txt").toFile();
    private final List<Transport> transports = new ArrayList<>();

    public List<Transport> checkType() {
        List<Transport> transportList = checkModel();
        for (Transport transport: transportList) {
            if (Category.AUTOMOBILE.getTitle().equals(transport.getType())) {
                transport.setCost(20);
            } else if (Category.MINIBUS.getTitle().equals(transport.getType())) {
                transport.setCost(30);
            } else if (Category.MOTORBIKE.getTitle().equals(transport.getType())) {
                transport.setCost(10);
            }
        }
        System.out.println(transportList);
        return transportList;
    }

    private List<Transport> checkModel() {
        readTransport();
        Pattern pattern = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_ ]+[0-9]|[a-zA-Z]$");
        Iterator<Transport> iterator = transports.iterator();
        BufferedWriter outputStream = null;
        BufferedWriter outputStream1 = null;
            while (iterator.hasNext()) {
                try {
                    Transport next = iterator.next();
                    if (pattern.matcher(next.getModel()).matches()) {
                        outputStream = new BufferedWriter(new FileWriter(FILE, true));
                        outputStream.append(System.lineSeparator());
                        outputStream.append(String.valueOf(next));
                    } else {
                        outputStream1 = new BufferedWriter(new FileWriter(FILE1, true));
                        outputStream1.append(String.valueOf(next)).append(System.lineSeparator());
                        iterator.remove();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (outputStream != null && outputStream1 != null) {
                        try {
                            outputStream.close();
                            outputStream1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        return transports;
    }

    private void readTransport() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] trans = line.split(", ");
                transports.add(new Transport(trans[0], trans[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
