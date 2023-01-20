package http;

import http.exception.HttpExceptionCode;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HttpMethod {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    CONNECT("CONNECT"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE"),
    PATCH("PATCH");
    private final String code;

    public static HttpMethod of(final String code) {
        return Arrays.stream(values())
                .filter(httpMethod -> httpMethod.code.equals(code.trim().toUpperCase()))
                .findAny()
                .orElseThrow(HttpExceptionCode.HTTP_START_LINE_FORMAT_EXCEPTION::newInstance);
    }

    @Override
    public String toString() {
        return code;
    }
}
