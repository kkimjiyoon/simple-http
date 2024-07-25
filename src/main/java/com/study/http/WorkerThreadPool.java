package com.study.http;

import com.study.http.channel.RequestChannel;

public class WorkerThreadPool {
    private final int poolSize;

    private final static int DEFAULT_POOL_SIZE=5;

    private final Thread[] workerThreads;
    private final RequestChannel requestChannel;

    public WorkerThreadPool(RequestChannel requestChannel){
        this(DEFAULT_POOL_SIZE, requestChannel);
    }
    public WorkerThreadPool(int poolSize, RequestChannel requestChannel) {
        //TODO#1 poolSize <1 다면 IllegalArgumentException이 발생합니다.
        if(poolSize < 1) {
            throw new IllegalArgumentException();
        }

        this.poolSize = poolSize;
        this.requestChannel = requestChannel;
        //TODO#2 requestChannel을 이용하여 httpRequestHandler 객체를 생성 합니다.
        HttpRequestHandler httpRequestHandler = new HttpRequestHandler(requestChannel);

        //TODO#3 workerThreads를 초기화 합니다. poolSize 만큼 Thread를 생성 합니다.
        workerThreads = new Thread[poolSize];

        for (int i = 0; i < poolSize; i++) {
            workerThreads[i] = new Thread(httpRequestHandler);
        }

    }
    public void start(){
        //TODO#4 workerThreads에 초가화된 모든 Thread를 start 합니다.
        for (Thread thread : workerThreads) {
            thread.start();
        }
    }
}
