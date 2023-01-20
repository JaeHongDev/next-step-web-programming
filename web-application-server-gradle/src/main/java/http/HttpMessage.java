package http;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * http message 는 startline headers 빈줄 body 로 구성됨
 */
@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpMessage {
    private final HttpStartLine startLine;
    private final HttpHeaders httpHeaders;
    private final HttpBody httpBody;

    public static HttpMessage of(List<String> httpMessageText) {
        return HttpMessage.builder()
                .startLine(HttpStartLine.of(httpMessageText.get(0)))
                .httpHeaders(HttpHeaders.of(httpMessageText))
                .httpBody(HttpBody.of(httpMessageText.get(httpMessageText.size() - 1))).build();
    }
}
