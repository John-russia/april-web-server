package ru.ivanov.march.chat;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String rawRequest;
    private String uri;
    private HttpMethod method;
    private Map<String, String> parameters;

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parseRequestLine();

    }

    public String getUri() {
        return uri;
    }

    public String getParameter(String key){
        return parameters.get(key);
    }

    public void parseRequestLine() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        this.parameters = new HashMap<>();
        if (uri.contains("?")){
            String[] elements = uri.split("[?]");
            this.uri = elements[0];
            String[] keyValues = elements[1].split("&");
            for (String o : keyValues){
                String[] keyValue = o.split("=");
                this.parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public void info(boolean showRawRequest){
        if (showRawRequest){
            System.out.println(rawRequest);
        }
        System.out.println("URL: " + uri);
        System.out.println("HTTP-method: " + method);
        System.out.println("Parameters: " + parameters);
    }

}
