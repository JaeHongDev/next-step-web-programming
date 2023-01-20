package webserver;

import controller.user.UserController;
import http.HttpMessage;
import http.HttpResponse;
import http.ResponseAccept;
import http.ResponseStatusCode;
import java.io.DataOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApplicationContext {
    private static final UserController userController = new UserController();
    private static final Logger log = LoggerFactory.getLogger(WebApplicationContext.class);

    public static void process(HttpMessage httpMessage, DataOutputStream dos) throws IOException {
        var responseAccept = ResponseAccept.parse(httpMessage.getHttpHeaders().getValue("Accept"));
        var response = process(responseAccept, httpMessage);

        response.writeResponse(dos);
    }

    private static HttpResponse process(ResponseAccept responseAccept, HttpMessage httpMessage) {
        var httpStartLine = httpMessage.getStartLine();
        var url = httpStartLine.getPath();
        if (responseAccept.isNotHtml()) {
            return HttpResponse.builder()
                    .statusCode(ResponseStatusCode.OK)
                    .filePath(url)
                    .fileType(responseAccept)
                    .build();
        }
        if ("/user/create".equals(url)) {
            return userController.newUser(httpMessage.getHttpBody());
        }
        if ("/user/login".equals(url)) {
            return userController.login(httpMessage.getHttpBody());
        }
        if ("/user/list".equals(url)) {
            return userController.findList(httpMessage.getHttpHeaders());
        }
        // not found
        {
            return HttpResponse.builder()
                    .statusCode(ResponseStatusCode.OK)
                    .filePath(url)
                    .fileType(ResponseAccept.ALL).build();
        }
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
