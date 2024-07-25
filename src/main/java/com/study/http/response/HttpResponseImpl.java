package com.study.http.response;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class HttpResponseImpl implements HttpResponse {
    //TODO#4 HttpResponse를 구현 합니다.

    private final Socket socket;
    private String charsetEncoding;
    public HttpResponseImpl(Socket socket){
        this.socket = socket;
    }

    @Override
    public PrintWriter getWriter() throws IOException {

        return new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void setCharacterEncoding(String charset) {
        this.charsetEncoding = charset;
    }

    @Override
    public String getCharacterEncoding() {
        return charsetEncoding;
    }
}

