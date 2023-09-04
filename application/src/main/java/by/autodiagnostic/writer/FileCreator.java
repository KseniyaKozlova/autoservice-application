package by.autodiagnostic.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

public interface FileCreator {

    FileWriter createFile(File processedTransportPath, Charset encoding) throws IOException;
}
