package http;


import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.Builder;

@Builder
public class HttpResponse {
    private ResponseStatusCode statusCode;
    private String filePath;
    private ResponseAccept fileType;
    private String cookie;

    private HttpResponse(ResponseStatusCode code, String filePath, ResponseAccept fileType, String cookie) {
        this.statusCode = code;
        this.filePath = filePath;
        this.fileType = fileType;
        this.cookie = cookie;
    }

    /**
     * dos.writeBytes("HTTP/1.1 200 OK \r\n"); dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
     * dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
     *
     * @return
     */
    public void writeResponse(DataOutputStream outputStream) throws IOException {
        byte[] body = Files.readAllBytes(new File("./webapp" + filePath).toPath());

        var result = String.format("HTTP/1.1 %s %s \r\n", statusCode.getCode(), statusCode.name())
                + String.format("Content-Type: %s;charset=utf-8", fileType.getType())
                + String.format("Content-Length: %s \r\n", body.length)
                + this.getCookieString()
                + "\r\n";
        outputStream.writeBytes(result);
        outputStream.write(body, 0, body.length);
        outputStream.flush();
    }

    private String getCookieString() {
        if (cookie == null) {
            return "";
        }
        return "Set-Cookie: logined=true";
    }
}
