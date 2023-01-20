package util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpRequestCollection {
    private final List<HttpRequest> httpRequests;

    public HttpRequestCollection(final List<String> plainRequestTexts){

         this.httpRequests = plainRequestTexts.stream()
                 .map(HttpRequest::of)
                 .collect(Collectors.toList());
    }

    public HttpRequest getFirstRequest(){
        return httpRequests.get(0);
    }

    public String requestUrl() {
        return httpRequests.get(0).getUrl();
    }
}
