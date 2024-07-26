package com.study.http.service;

import com.study.http.request.HttpRequest;
import com.study.http.response.HttpResponse;
import com.study.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class RegisterService implements HttpService{

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        //Body-설정
        String responseBody = null;

        try {
            responseBody = ResponseUtils.tryGetBodyFormFile(httpRequest.getRequestURI());//${count} <-- counter 값을 치환 합니다.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Header-설정
        String responseHeader = ResponseUtils.createResponseHeader(200,"UTF-8",responseBody.getBytes().length);

        //PrintWriter 응답
        try(PrintWriter bufferedWriter = httpResponse.getWriter();){
            bufferedWriter.write(responseHeader);
            bufferedWriter.write(responseBody);
            bufferedWriter.flush();
            log.debug("body:{}",responseBody.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        //Body-설정
        StringBuilder responseHeader = new StringBuilder();
        responseHeader.append("HTTP/1.1 301 Moved Permanently\n");
        responseHeader.append(String.format("Location: http://localhost:8080/index.html?userId=%s \n",httpRequest.getParameter("userId")));

        //PrintWriter 응답
        try(PrintWriter bufferedWriter = httpResponse.getWriter();){
            bufferedWriter.write(responseHeader.toString());
            bufferedWriter.flush();
            log.debug("header:{}",responseHeader.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
