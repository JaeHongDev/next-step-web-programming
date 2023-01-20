package util.http;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.RequiredArgsConstructor;
import util.HttpRequestCollection;

@RequiredArgsConstructor
public class HttpRequestHandler {
    private final HttpRequestCollection httpRequests;

    public byte[] getPage() throws IOException {
        return Files.readAllBytes(new File("./webapp" + httpRequests.requestUrl()).toPath());
    }
}
