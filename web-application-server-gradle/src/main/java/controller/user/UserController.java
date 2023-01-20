package controller.user;

import db.DataBase;
import http.HttpBody;
import http.HttpHeaders;
import http.HttpResponse;
import http.ResponseAccept;
import http.ResponseStatusCode;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.WebServer;

public class UserController {
    private static final Logger log = LoggerFactory.getLogger(WebServer.class);

    public HttpResponse newUser(HttpBody body) {
        var user = User.builder()
                .userId(body.getValue("userId"))
                .name(body.getValue("name"))
                .password(body.getValue("password"))
                .name(body.getValue("name"))
                .build();
        DataBase.addUser(user);
        log.info(DataBase.findAll().toString());

        return HttpResponse.builder()
                .statusCode(ResponseStatusCode.FOUND)
                .filePath("/index.html")
                .fileType(ResponseAccept.HTML)
                .build();
    }

    public HttpResponse login(HttpBody httpBody) {
        var user = DataBase.findUserById(httpBody.getValue("userId"));
        if (user.samePassword(httpBody.getValue("password"))) {
            // login success
            log.info("SUCCESS LOGIN");
            return HttpResponse.builder()
                    .statusCode(ResponseStatusCode.OK)
                    .filePath("/index.html")
                    .fileType(ResponseAccept.HTML)
                    .cookie("logined=true")
                    .build();
        }
        log.info("FAIL LOGIN");
        return HttpResponse.builder()
                .statusCode(ResponseStatusCode.FOUND)
                .filePath("/user/login_failed.html")
                .fileType(ResponseAccept.HTML)
                .build();
    }

    public HttpResponse findList(HttpHeaders httpHeaders) {
        log.info(httpHeaders.toString());
        return HttpResponse.builder()
                .statusCode(ResponseStatusCode.OK)
                .filePath("/user/list")
                .fileType(ResponseAccept.HTML)
                .build();
    }
}
