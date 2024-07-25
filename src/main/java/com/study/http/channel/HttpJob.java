package com.study.http.channel;

import com.study.http.request.HttpRequest;
import com.study.http.request.HttpRequestImpl;
import com.study.http.response.HttpResponse;
import com.study.http.response.HttpResponseImpl;
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
    public void execute() throws IOException {

        log.debug("method:{}", httpRequest.getMethod());
        log.debug("uri:{}", httpRequest.getRequestURI());
        log.debug("client-closed:{}",client.isClosed());

        /*TODO#5 Browser(client)는 특정 page를 요청시 먼저 /favicon.ico 호출 합니다.
          아래 코드에서는 /favicon.ico 요청을 처리하지 않고 return 합니다.
        */
        if(httpRequest.getRequestURI().equals("/favicon.ico")){
            return;
        }

        /*TODO#6 /index.html을 요청시  httpRequest.getRequestURI()에 해당되는 html 파일이 존재 하지 않는다면 client 연결을 종료 합니다.
            ex) /index.html 요청이 온다면 ->  /resources/index.html이 존재하지 않는다면 client 연결을 종료 합니다.
            ResponseUtils.isExist(httpRequest.getRequestURI()) 이용하여 구현합니다.
        */

        if (!ResponseUtils.isExist(httpRequest.getRequestURI())) {
            client.close();
        }


        /*TODO#8 responseBody에 응답할 html 파일을 읽습니다.
           ResponseUtils.tryGetBodyFromFile(httpRequest.getRequestURI()) 이용하여 구현 합니다.
        */
        String responseBody = ResponseUtils.tryGetBodyFromFile(httpRequest.getRequestURI());


        /*TODO#10 ResponseHeader를 생성합니다.
          ResponseUtils.createResponseHeader() 이용해서 생성합니다. responseHeader를 생성합니다.
        */
        String responseHeader = ResponseUtils.createResponseHeader(200, "UTF-8", responseBody.getBytes().length);

        //TODO#12 PrintWriter을 사용 하여 responseHeader, responseBody를 응답합니다.
        try (PrintWriter writer = httpResponse.getWriter()) {
            writer.write(responseHeader);
            writer.write(responseBody);

            writer.flush();
        }
    }
}

