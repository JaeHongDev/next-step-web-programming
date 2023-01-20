package http;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestUrl {
    private final String url;

    public static RequestUrl of(final String url) {
        return new RequestUrl(url);
    }

    @Override
    public String toString() {
        return url;
    }
}
