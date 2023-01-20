package webserver;

import http.HttpBody;
import http.HttpHeaders;
import http.HttpMessage;
import http.HttpStartLine;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private final Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    private static HttpBody parseHttpBody(BufferedReader bufferedReader, HttpHeaders httpHeader) throws IOException {
        if (httpHeader.hasKey("Content-Length")) {
            var contentLength = httpHeader.getValueToInt("Content-Length");
            return HttpBody.of(IOUtils.readData(bufferedReader, contentLength));
        }
        return null;
    }

    private static HttpHeaders parseHttpHeaders(BufferedReader bufferedReader) throws IOException {
        var temp = new ArrayList<String>();
        while (true) {
            var line = bufferedReader.readLine();
            if (line.isBlank() || line.isEmpty()) {
                break;
            }
            temp.add(line);
        }
        return HttpHeaders.of(temp);
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());
        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            var bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            //var parsedRequestText = HttpRequestParser.parseStream(bufferedReader);

            if (!bufferedReader.ready()) { // 비어 있는 요청이 들어온경우 그대로 통과 시킴
                return;
            }
            var httpStandLine = HttpStartLine.of(bufferedReader.readLine());
            var httpHeader = parseHttpHeaders(bufferedReader);
            var httpBody = parseHttpBody(bufferedReader, httpHeader);

            var httpMessage = HttpMessage.builder()
                    .startLine(httpStandLine)
                    .httpHeaders(httpHeader)
                    .httpBody(httpBody)
                    .build();

            DataOutputStream dos = new DataOutputStream(out);
            WebApplicationContext.process(httpMessage, dos);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

}
