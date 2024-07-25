package com.study.http.request;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class HttpRequestImpl implements HttpRequest {
    /* TODO#2 HttpRequest를 구현 합니다.
     *  test/java/com/nhnacademy/http/request/HttpRequestImplTest TestCode를 실행하고 검증 합니다.
     */

    private final Socket client;

    private String method;
    private String requestURI;
    private final Map<String, String> parameterMap = new HashMap<>();
    private final Map<String, Object> attributeMap = new HashMap<>();
    private final Map<String, String> headerMap = new HashMap<>();

    public HttpRequestImpl(Socket socket) {
        this.client = socket;
        parse();
    }

    private void parse() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String line = bufferedReader.readLine();

            if (line != null && !line.isEmpty()) {
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    method = parts[0];
                    String[] uriParts = parts[1].split("\\?");
                    requestURI = uriParts[0];
                    if (uriParts.length > 1) {
                        parseParameters(uriParts[1]);
                    }
                }
            }

            String header;
            while ((header = bufferedReader.readLine()) != null && !header.isEmpty()) {
                String[] headerParts = header.split(": ");
                if (headerParts.length == 2) {
                    headerMap.put(headerParts[0], headerParts[1]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseParameters(String query) {
        String[] queryParts = query.split("&");
        for (String part : queryParts) {
            String[] keyValue = part.split("=");
            if (keyValue.length > 1) {
                parameterMap.put(keyValue[0], keyValue[1]);
            }
        }
    }


    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getParameter(String name) {
        return parameterMap.get(name);
    }

    @Override
    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    @Override
    public String getHeader(String name) {
        return headerMap.get(name);
    }

    @Override
    public void setAttribute(String name, Object o) {
        attributeMap.put(name, o);
    }

    @Override
    public Object getAttribute(String name) {
        return attributeMap.get(name);
    }

    @Override
    public String getRequestURI() {
        return requestURI;
    }
}
