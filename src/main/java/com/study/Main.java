package com.study;

import com.study.http.SimpleHttpServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //TODO#15 SimpleHttpServer를 시작합니다.
        SimpleHttpServer simpleHttpServer = new SimpleHttpServer(8080);
        simpleHttpServer.start();
    }
}
