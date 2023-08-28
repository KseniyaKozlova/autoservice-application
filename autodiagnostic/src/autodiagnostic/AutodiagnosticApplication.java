package autodiagnostic;

import autodiagnostic.transport.TransportService;

public class AutodiagnosticApplication {

    public static void main(String[] args) {
        final TransportService transportService = new TransportService();
        transportService.startApplication();
    }
}
