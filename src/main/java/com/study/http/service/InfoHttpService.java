package com.study.http.service;

import com.study.http.request.HttpRequest;
import com.study.http.response.HttpResponse;
import com.study.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class InfoHttpService implements HttpService {
    /*TODO#3 InfoHttpService 구현
       - Request : http://localhost:8080/info.html?id=marco&age=40&name=마르코
       - 요청을 처리하고 응답하는 InfoHttpService 입니다.
       - IndexHttpService를 참고하여 doGet을 구현하세요.
       - info.html 파일은 /resources/info.html 위치 합니다.
       - info.html을 읽어 parameters{id,name,age}를 replace 후 응답 합니다.
       - ex)
            ${id} <- marco
            ${name} <- 마르코
            ${age} <- 40
    */

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        //doGet 구현

        String responseBody = null;

        try {
            responseBody = ResponseUtils.tryGetBodyFormFile(httpRequest.getRequestURI());

            String id = httpRequest.getParameter("id");
            String age = httpRequest.getParameter("age");
            String name = httpRequest.getParameter("name");

            responseBody = responseBody.replace("${id}", id);
            responseBody = responseBody.replace("${age}", age);
            responseBody = responseBody.replace("${name}", URLDecoder.decode(name, StandardCharsets.UTF_8));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String responseHeader = ResponseUtils.createResponseHeader(200, "UTF-8", responseBody.length());

        try(PrintWriter printWriter = httpResponse.getWriter()) {
            printWriter.write(responseHeader);
            printWriter.write(responseBody);
            printWriter.flush();
            log.debug("body:{}", responseBody.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
