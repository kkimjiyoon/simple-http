package com.study.http;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class SimpleHttpServer {

    private final int port;
    private static final int DEFAULT_PORT=8080;

    public SimpleHttpServer(){
        this(DEFAULT_PORT);
    }

    public SimpleHttpServer(int port) {
        if(port<=0){
            throw new IllegalArgumentException(String.format("invalid port :%d",port));
        }
        this.port = port;
    }

    public void start() throws IOException {
        //TODO#1 - SocketServer를 생성 합니다. PORT = 8080
        try(ServerSocket serverSocket = new ServerSocket(8080)){
            while(true){
                //TODO#2 - Client와 서버가 연결 되면 HttpRequestHandler를 이용해서 Thread을 생성 합니다.
                Socket client = serverSocket.accept();

                HttpRequestHandler httpRequestHandler = new HttpRequestHandler(client);
                httpRequestHandler.run();

                break;
            }
        }
    }

}
