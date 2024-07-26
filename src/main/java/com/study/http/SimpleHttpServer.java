package com.study.http;

import com.study.http.channel.HttpJob;
import com.study.http.channel.RequestChannel;
import com.study.http.context.Context;
import com.study.http.context.ContextHolder;
import com.study.http.service.IndexHttpService;
import com.study.http.service.InfoHttpService;
import com.study.http.util.CounterUtils;
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
        //RequestChannel() 초기화 합니다.
        requestChannel = new RequestChannel();

        //workerThreadPool 초기화 합니다.
        workerThreadPool = new WorkerThreadPool(requestChannel);

        /*TODO#4 Context에 HttpService Object 등록
         * * ex)  context.setAttribute("/index.html",new IndexHttpService());
         * index.html 과 info.html을 등록 합니다.
         * */

        Context context = ContextHolder.getApplicationContext();
        context.setAttribute("/index.html", new IndexHttpService());
        context.setAttribute("/info.html", new InfoHttpService());



        /*TODO#5 Counter 구현을 위해서 CounterUtils.CONTEXT_COUNTER_NAME 으로, 0l 을 context에 등록 합니다.
         *  */
        context.setAttribute(CounterUtils.CONTEXT_COUNTER_NAME, 0L);


    }

    public void start(){
        //workerThreadPool을 시작 합니다.
        workerThreadPool.start();

        try(ServerSocket serverSocket = new ServerSocket(8080);){
            while(true){
                Socket client = serverSocket.accept();
                requestChannel.addHttpJob(new HttpJob(client));
            }
        }catch (IOException e){
            log.error("server error:{}",e);
        }
    }
}

