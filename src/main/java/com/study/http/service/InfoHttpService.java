package com.study.http.service;

import com.study.http.request.HttpRequest;
import com.study.http.response.HttpResponse;
import com.study.http.util.CounterUtils;
import com.study.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class InfoHttpService implements HttpService {
    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        //Body-설정
        String responseBody = null;

        try {
            responseBody = ResponseUtils.tryGetBodyFormFile(httpRequest.getRequestURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String id =  httpRequest.getParameter("id");
        String name= httpRequest.getParameter("name");
        name = URLDecoder.decode(name, StandardCharsets.UTF_8);
        String age = httpRequest.getParameter("age");

        log.debug("id:{}",id);
        log.debug("name:{}",name);
        log.debug("age:{}",age);

        responseBody = responseBody.replace("${id}",id);
        responseBody = responseBody.replace("${name}",name);
        responseBody = responseBody.replace("${age}",age);

        //TODO#9 CounterUtils.increaseAndGet()를 이용해서 context에 있는 counter 값을 증가시키고, 반환되는 값을 info.html에 반영 합니다.
        // ${count} <-- counter 값을 치환 합니다.

        responseBody = responseBody.replace("${count}", String.valueOf(CounterUtils.increaseAndGet()));

        //Header-설정
        String responseHeader = ResponseUtils.createResponseHeader(200,"UTF-8",responseBody.getBytes().length);

        //PrintWriter 응답
        try(PrintWriter bufferedWriter = httpResponse.getWriter();){
            bufferedWriter.write(responseHeader);
            bufferedWriter.write(responseBody);
            bufferedWriter.write("\n");
            bufferedWriter.flush();
            log.debug("body:{}",responseBody.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

