package http;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpHeaders {
    private final Map<String, String> headers;

    @Deprecated
    private static HttpHeaders of(List<String> text, int i) {
        int n = 1;
        Map<String, String> parsedData = new LinkedHashMap<>();
        while (n < text.size() && !text.get(n).isBlank()) {
            var splitStr = text.get(n++).split(":");
            parsedData.put(splitStr[0], splitStr[1]);
        }
        return new HttpHeaders(parsedData);
    }

    public static HttpHeaders of(List<String> lines) {
        return new HttpHeaders(lines.stream()
                .map(line -> line.split(": "))
                .collect(Collectors.toMap((arr) -> arr[0], (arr) -> arr[1], (x, y) -> y, LinkedHashMap::new)));
    }

    public String getValue(String key) {
        return headers.get(key);
    }

    @Override
    public String toString() {
        return "HttpHeaders{" +
                "headers=" + headers.entrySet().stream()
                .map(stringStringEntry -> String.format("[key:%s]=[value:%s]", stringStringEntry.getKey(),
                        stringStringEntry.getValue())).collect(
                        Collectors.joining("\n")) +
                '}';
    }

    public boolean hasKey(String s) {
        return this.headers.containsKey(s);
    }

    public int getValueToInt(String s) {
        return Integer.parseInt(this.headers.get(s));
    }
}
