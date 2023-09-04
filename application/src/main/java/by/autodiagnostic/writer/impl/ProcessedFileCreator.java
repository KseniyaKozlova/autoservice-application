package by.autodiagnostic.writer.impl;

import by.autodiagnostic.writer.FileCreator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

public class ProcessedFileCreator implements FileCreator {

    @Override
    public FileWriter createFile(final File processedTransportPath, final Charset encoding) throws IOException {
        return new FileWriter(processedTransportPath, encoding);
    }
}
