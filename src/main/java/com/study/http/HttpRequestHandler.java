package com.study.http;

import com.study.http.channel.Executable;
import com.study.http.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

@Slf4j
public class HttpRequestHandler implements Runnable {

    private final RequestChannel requestChannel;

    public HttpRequestHandler(RequestChannel requestChannel) {
        this.requestChannel = requestChannel;
    }

    @Override
    public void run() {
        Executable httpJob = requestChannel.getHttpJob();
        try {
            httpJob.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        run();
    }
}
