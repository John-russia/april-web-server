package ru.ivanov.march.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequest {
    private static final Logger requestlogger = LogManager.getLogger(HttpRequest.class.getName());
    private String rawRequest;
    private String uri;
    private HttpMethod method;
    private Map<String, String> parameters;
    private String body;

    public String getRouteKey() {
        return String.format("%s %s", method, uri);
    }

    public String getBody() {
        return body;
    }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parseRequestLine();
        if (method == HttpMethod.POST) {
            requestlogger.trace("Пришёл POST-запрос. Ищем Бодю");
            this.tryToParseBody();
        } else {
            requestlogger.trace("Пришёл не POST-запрос.Не ищем Бодю");
        }
    }

    public void tryToParseBody() {
        if (method == HttpMethod.POST) {
            List<String> lines = rawRequest.lines().collect(Collectors.toList());
            int splitLine = -1;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).isEmpty()) {
                    splitLine = i;
                    break;
                }
            }
            if (splitLine > -1) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = splitLine + 1; i < lines.size(); i++) {
                    stringBuilder.append(lines.get(i));
                }
                this.body = stringBuilder.toString();
            }
        }
    }

    public String getUri() {
        return uri;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public void parseRequestLine() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        this.parameters = new HashMap<>();
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            this.uri = elements[0];
            String[] keyValues = elements[1].split("&");
            for (String o : keyValues) {
                String[] keyValue = o.split("=");
                this.parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public void info(boolean showRawRequest) {
//        if (showRawRequest){
//            System.out.println(rawRequest);
//        }
        for (String s : Arrays.asList("URL: " + uri, "HTTP-method: " + method, "Parameters: " + parameters, "Body: " + body)) {
            requestlogger.trace(s);
        }
    }

}
