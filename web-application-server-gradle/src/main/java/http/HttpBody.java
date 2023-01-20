package http;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpBody {
    private final Map<String, String> httpBody;

    private HttpBody(final String input) {
        this.httpBody = Arrays.stream(input.split("&"))
                .map(str -> str.split("="))
                .collect(Collectors.toMap((arr) -> arr[0], (arr) -> arr[1], (x, y) -> y, LinkedHashMap::new));
    }

    public static HttpBody of(final String input) {
        return new HttpBody(input);
    }

    public String getValue(final String key) {
        return httpBody.get(key);
    }

    @Override
    public String toString() {
        return "HttpBody{" +
                "body=" + httpBody.entrySet().stream()
                .map(stringStringEntry -> String.format("[key:%s]=[value:%s]", stringStringEntry.getKey(),
                        stringStringEntry.getValue())).collect(
                        Collectors.joining("\n")) +
                '}';
    }
}
