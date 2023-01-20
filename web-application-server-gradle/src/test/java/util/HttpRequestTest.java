package util;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import http.exception.HttpFormatException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HttpRequestTest {

    @ParameterizedTest
    @ValueSource(strings = {"GET /", " /"})
    void http_request_를_생성할때_3글자가_아닌경우_에러가_발생합니다(final String value) {
        assertThatThrownBy(() -> HttpRequest.of(value))
                .isInstanceOf(HttpFormatException.class);
    }
}