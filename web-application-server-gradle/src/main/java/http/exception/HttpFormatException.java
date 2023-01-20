package http.exception;

public class HttpFormatException extends RuntimeException {

    public HttpFormatException(String message) {
        super(message);
    }

    public HttpFormatException(String message, int code) {
        super(message);
    }

    public HttpFormatException(String message, String... args) {
        super(message);
    }
}
