package http;


import http.policy.ValidateHttpStartLinePolicy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpStartLine {
    private final HttpMethod httpMethod;
    private final RequestUrl requestUrl;
    private final RequestParam requestParam;
    private final String version;

    public static HttpStartLine of(final String input) {
        var splitStr = input.trim().split(" ");
        ValidateHttpStartLinePolicy.validateSplitMessageSize(splitStr.length);

        var httpMethod = HttpMethod.of(splitStr[0]);
        var requestParamDelimiterIndex = findIndexByCharacter(splitStr[1], '?');

        return HttpStartLine.builder()
                .httpMethod(httpMethod)
                .requestUrl(generateRequestUrl(splitStr[1], requestParamDelimiterIndex))
                .requestParam(generateRequestParam(splitStr[1], requestParamDelimiterIndex))
                .build();
    }

    private static RequestUrl generateRequestUrl(final String str, final int index) {
        if (index == -1) {
            return RequestUrl.of(str);
        }
        return RequestUrl.of(str.substring(0, index));
    }

    private static RequestParam generateRequestParam(final String str, final int index) {
        if (index == -1) {
            return RequestParam.parsePlainText("");
        }
        return RequestParam.parsePlainText(str.substring(index + 1));
    }

    private static int findIndexByCharacter(final String str, final char character) {
        return str.indexOf(character);
    }

    public String getPath() {
        return this.requestUrl.getUrl();
    }
}
