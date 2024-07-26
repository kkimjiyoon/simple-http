package com.study.http.service;

import com.study.http.request.HttpRequest;
import com.study.http.response.HttpResponse;
import com.study.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class IndexHttpService implements HttpService{
    /* TODO#2 IndexHttpService 구현
       http://localhost:8080/index.html
       요청을 처리하는 HttpService 입니다.
    */

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        //Body-설정
        String responseBody = null;

        try {
            responseBody = ResponseUtils.tryGetBodyFormFile(httpRequest.getRequestURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Header-설정
        String responseHeader = ResponseUtils.createResponseHeader(200,"UTF-8",responseBody.length());

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
}
