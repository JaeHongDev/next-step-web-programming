package http;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseAccept {
    HTML("text/html"),
    CSS("text/css"),
    ALL("*/*");

    private final String type;

    public static ResponseAccept parse(final String text) {
        var splitStr = text.split(",");
        if (splitStr.length == 0) {
            return ALL;
        }
        return Arrays.stream(values())
                .filter(responseAccept -> responseAccept.type.equals(splitStr[0])).findAny()
                .orElse(ResponseAccept.ALL);
    }

    public boolean isNotHtml() {
        return this != HTML;
    }
}
