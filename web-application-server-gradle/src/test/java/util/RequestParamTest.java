package util;

import http.RequestParam;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RequestParamTest {


    @Test
    void 정상적으로_문자열이_파싱됩니다() {
        var str = "abc=1&bbb=2&ccc=3";
        var requestParam = RequestParam.parsePlainText(str);
        Assertions.assertThat(requestParam.getValue("abc")).isEqualTo("1");
        Assertions.assertThat(requestParam.getValue("bbb")).isEqualTo("2");
        Assertions.assertThat(requestParam.getValue("ccc")).isEqualTo("3");
    }
}