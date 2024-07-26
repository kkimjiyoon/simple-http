package com.study.http.channel;

import com.study.http.context.Context;
import com.study.http.context.ContextHolder;
import com.study.http.request.HttpRequest;
import com.study.http.request.HttpRequestImpl;
import com.study.http.response.HttpResponse;
import com.study.http.response.HttpResponseImpl;
import com.study.http.service.HttpService;
import com.study.http.service.IndexHttpService;
import com.study.http.service.InfoHttpService;
import com.study.http.util.ResponseUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class HttpJob implements Executable {

    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    private final Socket client;

    public HttpJob(Socket client) {
        this.httpRequest = new HttpRequestImpl(client);
        this.httpResponse = new HttpResponseImpl(client);
        this.client = client;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    @Override
    public void execute(){

        log.debug("method:{}", httpRequest.getMethod());
        log.debug("uri:{}", httpRequest.getRequestURI());
        log.debug("clinet-closed:{}",client.isClosed());

        if(httpRequest.getRequestURI().equals("/favicon.ico")){
            return;
        }

        if(!ResponseUtils.isExist(httpRequest.getRequestURI())){
            try {
                //404 - not -found
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        //TODO#6 requestURI()을 이용해서 Context에 등록된 HttpService를 실행 합니다.
        Context context = ContextHolder.getApplicationContext();

        HttpService httpService = (HttpService) context.getAttribute(httpRequest.getRequestURI());

        httpService.service(httpRequest, httpResponse);

        try {
            if(Objects.nonNull(client) && client.isConnected()) {
                client.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


