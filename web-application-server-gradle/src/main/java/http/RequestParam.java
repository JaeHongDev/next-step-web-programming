package http;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestParam {
    private static final String PARAM_DELIMITER = "&";
    private final Map<String, String> parameters;

    public static RequestParam parsePlainText(String s) {
        if (s == null || s.isBlank() || s.length() < 2) {
            return new RequestParam(new LinkedHashMap<>());
        }
        var result = Arrays.stream(s.split("&"))
                .map(str -> str.split("="))
                .collect(Collectors.toMap((arr) -> arr[0], (arr) -> arr[1], (x, y) -> y, LinkedHashMap::new));
        return new RequestParam(result);
    }

    public String getValue(final String key) {
        return parameters.get(key);
    }

    @Override
    public String toString() {
        return "RequestParam{" + "parameters=" + parameters + '}';
    }
}