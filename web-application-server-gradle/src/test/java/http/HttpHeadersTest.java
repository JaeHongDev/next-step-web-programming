package http;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HttpHeadersTest {

    @Test
    void 헤더가_정상적으로_파싱됩니다() {
        var header = List.of("GET / HTTp1.1", "key: value", "key1: value1", "");
        var httpHeader = HttpHeaders.of(header);
        Assertions.assertThat(httpHeader.getValue("key")).isEqualTo(" value");
    }

}