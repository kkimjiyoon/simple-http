package com.study.http.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Objects;

public class ResponseUtils {
    private ResponseUtils(){}

    public static boolean isExist(String filePath){
        URL url = ResponseUtils.class.getResource(filePath);
        return Objects.nonNull(url);
    }
    public static String tryGetBodyFormFile(String filePath) throws IOException {
        StringBuilder responseBody = new StringBuilder();
        try(InputStream inputStream = ResponseUtils.class.getResourceAsStream(filePath);
            BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream,"UTF-8"))){
            while(true) {
                String line = reader.readLine();
                if(Objects.isNull(line)){
                    break;
                }
                responseBody.append(line);
            }
        }
        return responseBody.toString();
    }

    public static String createResponseHeader(int httpStatusCode, String charset, int contentLength ){
        StringBuilder responseHeader = new StringBuilder();
        responseHeader.append(String.format("HTTP/1.0 %d OK%s",httpStatusCode,System.lineSeparator()));
        responseHeader.append(String.format("Server: HTTP server/0.1%s",System.lineSeparator()));
        responseHeader.append(String.format("Content-type: text/html; charset=%s%s",charset,System.lineSeparator()));
        responseHeader.append(String.format("Connection: Closed%s",System.lineSeparator()));
        responseHeader.append(String.format("Content-Length:%d %s%s",contentLength,System.lineSeparator(),System.lineSeparator()));
        return responseHeader.toString();
    }

}
