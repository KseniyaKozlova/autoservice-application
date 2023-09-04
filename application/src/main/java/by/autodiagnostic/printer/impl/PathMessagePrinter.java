package by.autodiagnostic.printer.impl;

import by.autodiagnostic.printer.MessagePrinter;

import java.io.File;

public class PathMessagePrinter implements MessagePrinter {

    @Override
    public void print(File fileToPrint) {
        System.out.print(fileToPrint.toPath().toAbsolutePath());
    }
}
