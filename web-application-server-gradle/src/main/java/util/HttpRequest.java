package util;

import http.HttpMethod;
import http.RequestParam;
import http.RequestUrl;
import http.exception.HttpFormatException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpRequest {
    private final HttpMethod httpMethod;
    private final RequestUrl url;
    private final RequestParam param;
    private final String httpType;

    public static HttpRequest of(final String plainText) {
        System.out.println(plainText);
        var splitStr = plainText.split(" ");
        if (splitStr.length != 3) {
            throw new HttpFormatException(plainText);
        }
        var requestParameterDelimiterIndex = splitStr[1].indexOf('?');

        if (requestParameterDelimiterIndex == -1) {
            return new HttpRequest(HttpMethod.of(splitStr[0]), RequestUrl.of(splitStr[1]), null, splitStr[2]);
        }

        var requestUrl = RequestUrl.of(splitStr[1].substring(0, requestParameterDelimiterIndex));
        var requestParam = RequestParam.parsePlainText(
                splitStr[1].substring(requestParameterDelimiterIndex + 1));
        return new HttpRequest(
                HttpMethod.of(splitStr[0]),
                requestUrl,
                requestParam,
                splitStr[2]);
    }

    public String getUrl() {
        return url.getUrl();
    }

    public RequestParam getParam() {
        return param;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "httpMethod=" + httpMethod +
                ", url=" + url +
                ", param=" + param +
                ", httpType='" + httpType + '\'' +
                '}';
    }
}
