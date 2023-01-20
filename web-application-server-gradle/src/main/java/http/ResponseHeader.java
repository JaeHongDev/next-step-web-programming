package http;

import lombok.Builder;
import lombok.RequiredArgsConstructor;


@Builder
@RequiredArgsConstructor
public class ResponseHeader {

    private final ResponseStatusCode statusCode;
}
