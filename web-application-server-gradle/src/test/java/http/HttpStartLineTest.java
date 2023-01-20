package http;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HttpStartLineTest {
    @Test
    void httpMessage의_시작점이_정상적으로_파싱됩니다() {
        var httpStartLine = HttpStartLine.of("GET /index.html HTTP1.1");
        Assertions.assertThat(httpStartLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        Assertions.assertThat(httpStartLine.getRequestUrl().getUrl()).isEqualTo("/index.html");
    }

    @Test
    void httpMessage의_시작점에서_param_이_정상적으로_파싱됩니다() {
        var httpStartLine = HttpStartLine.of("GET /index.html?a=a&b=b&c=c HTTP1.1");
        var params = httpStartLine.getRequestParam();

        Assertions.assertThat(httpStartLine.getHttpMethod()).isEqualTo(HttpMethod.GET);
        Assertions.assertThat(httpStartLine.getRequestUrl().getUrl()).isEqualTo("/index.html");
        Assertions.assertThat(params.getValue("a")).isEqualTo("a");
        Assertions.assertThat(params.getValue("b")).isEqualTo("b");
        Assertions.assertThat(params.getValue("c")).isEqualTo("c");
    }

}