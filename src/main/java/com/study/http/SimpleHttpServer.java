package com.study.http;

import com.study.http.channel.HttpJob;
import com.study.http.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SimpleHttpServer {

    private final int port;
    private static final int DEFAULT_PORT=8080;

    private final RequestChannel requestChannel;

    public SimpleHttpServer(){
        this(DEFAULT_PORT);
    }
    private WorkerThreadPool workerThreadPool;

    public SimpleHttpServer(int port) {
        if(port<=0){
            throw new IllegalArgumentException(String.format("Invalid Port:%d",port));
        }
        this.port = port;
        //TODO#5 RequestChannel() 초기화 합니다.
        requestChannel = new RequestChannel();

        //TODO#6 workerThreadPool 초기화 합니다.
        workerThreadPool = new WorkerThreadPool(requestChannel);
    }

    public void start(){
        //TODO#7 workerThreadPool을 시작 합니다.
        workerThreadPool.start();

        try(ServerSocket serverSocket = new ServerSocket(8080);){
            while(true){
                Socket client = serverSocket.accept();
                //TODO#8 Queue(requestChannel)에 HttpJob 객체를 배치 합니다.
                HttpJob httpJob = new HttpJob(client);
                requestChannel.addHttpJob(httpJob);
            }
        }catch (IOException e){
            log.error("server error:{}",e);
        }
    }
}

