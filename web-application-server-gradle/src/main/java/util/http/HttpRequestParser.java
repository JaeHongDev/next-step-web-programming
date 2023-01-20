package util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequestParser {

    public static List<String> parseStream(BufferedReader bufferedReader) throws IOException {
        return bufferedReader.lines().collect(Collectors.toList());
    }

}
