package domain.user.ui;


import common.BasicController;
import domain.user.application.FindUserUseCase;
import domain.user.payload.response.UserInformationResponse;
import http.request.HttpRequest;
import http.response.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class FindUserController implements BasicController {
    private final FindUserUseCase findUserUseCase = new FindUserUseCase();

    @Override
    public void process(HttpRequest httpRequest, HttpResponse response) {
        var logined = httpRequest.getCookie("logined");
        if (logined.isEmpty()) {
            response.writeRedirect("/index.html");
            return;
        }
        if (Boolean.parseBoolean(logined.get())) {
            response.writeHtml(this.generateView(findUserUseCase.getAllUser()));
            return;
        }
        response.writeRedirect("/index.html");
    }

    private String generateView(List<UserInformationResponse> allUser) {
        return "<!DOCTYPE HTML>"
                + "<head>"
                + "</head>"
                + "<body>"
                + "<ul>"
                + allUser.stream().map(UserInformationResponse::toHTML).collect(Collectors.joining())
                + "</ul>"
                + "</body>"
                + "</html>";


    }
}
