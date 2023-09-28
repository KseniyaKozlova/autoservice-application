package by.autodiagnostic.web.decoder.impl;

import by.autodiagnostic.web.decoder.Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URLDecoder;

import static by.autodiagnostic.util.StandardConstants.CHARSET;

public class InputStreamDecoder implements Decoder {

    private final HttpServletRequest request;

    public InputStreamDecoder(final HttpServletRequest request) {
        this.request = request;
    }

    public InputStream getDecodedStream() throws IOException {
        try (final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), CHARSET))) {

            final var cont = bufferedReader.lines().reduce("", String::concat);
            final var jsonCont = cont.split("=")[1];

            final var json = URLDecoder.decode(jsonCont, CHARSET);
            return new ByteArrayInputStream(json.getBytes(CHARSET));
        }
    }
}
