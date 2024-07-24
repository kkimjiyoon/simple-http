package com.study.http;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SimpleHttpServer {

    private final int port;
    private static final int DEFAULT_PORT=8080;

    public SimpleHttpServer(){
        this(DEFAULT_PORT);
    }
    public SimpleHttpServer(int port) {
        if(port<=0){
            throw new IllegalArgumentException(String.format("Invalid Port:%d",port));
        }
        this.port = port;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(8080);){

            HttpRequestHandler httpRequestHandlerA = new HttpRequestHandler();
            HttpRequestHandler httpRequestHandlerB = new HttpRequestHandler();

            //TODO#9threadA를 생성하고 시작 합니다.
            Thread threadA = new Thread(httpRequestHandlerA);
            threadA.start();


            //TODO#10threadB를 생성하고 시작 합니다.
            Thread threadB = new Thread(httpRequestHandlerB);
            threadB.start();

            long count = 0;

            while(true){
                Socket client = serverSocket.accept();
                /*TODO#O11 count값이 짝수이면 httpRequestHandlerA에 client를 추가 합니다.
                           count값이 홀수라면 httpRequestHandlerB에 clinet를 추가 합니다.
                */

                if (count % 2 == 0) {
                    httpRequestHandlerA.addRequest(client);
                } else {
                    httpRequestHandlerB.addRequest(client);
                }

                count++;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

