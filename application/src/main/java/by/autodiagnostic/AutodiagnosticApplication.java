package by.autodiagnostic;

import by.autodiagnostic.transport.TransportService;

public class AutodiagnosticApplication {

    public static void main(final String[] args) {
        final TransportService transportService = new TransportService();
        transportService.startApplication();
    }
}
