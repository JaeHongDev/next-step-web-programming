package http.policy;

import http.exception.HttpExceptionCode;

public class ValidateHttpStartLinePolicy {


    public static void validate() {

    }

    public static void validateSplitMessageSize(final int size) {
        if (size != 3) {
            throw HttpExceptionCode.HTTP_START_LINE_FORMAT_EXCEPTION.newInstance();
        }
    }
}
