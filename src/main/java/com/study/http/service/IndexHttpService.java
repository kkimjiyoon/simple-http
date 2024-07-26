package com.study.http.service;

import com.study.http.request.HttpRequest;
import com.study.http.response.HttpResponse;
import com.study.http.util.CounterUtils;
import com.study.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class IndexHttpService implements HttpService{

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {

        //Body-설정
        String responseBody = null;

        try {
            responseBody = ResponseUtils.tryGetBodyFormFile(httpRequest.getRequestURI());
            //TODO#8 CounterUtils.increaseAndGet()를 이용해서 context에 있는 counter 값을 증가시키고, 반환되는 값을 index.html에 반영 합니다.
            //${count} <-- counter 값을 치환 합니다.
            responseBody = responseBody.replace("${count}", String.valueOf(CounterUtils.increaseAndGet()));
            responseBody = responseBody.replace("${userId}", httpRequest.getParameter("userId"));
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


