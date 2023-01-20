package http.exception;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum HttpExceptionCode {
    HTTP_START_LINE("HTTP start line", 1000),
    HTTP_START_LINE_FORMAT_EXCEPTION("HTTP 시작점의 포맷이 정상적이지 않습니다. [input=%s]", HTTP_START_LINE.code + 1);
    private final String message;
    private final int code;

    private static String generateHttpErrorMessage(String message, int code) {
        return String.format("[ERROR code:%i] %s", code, message);
    }

    public HttpFormatException newInstance() {
        return new HttpFormatException(generateHttpErrorMessage(message, code));
    }
}
