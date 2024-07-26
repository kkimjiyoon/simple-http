package com.study.http.service;

import com.study.http.request.HttpRequest;
import com.study.http.response.HttpResponse;

//TODO#1 Http Request, Http Response를 parameter를 인자로 받고 http 요청을 처리 하고 응답하는 역할을 합니다.
public interface HttpService {

    default void service(HttpRequest httpRequest, HttpResponse httpResponse){
        if(httpRequest.getMethod().equals("GET")){
            doGet(httpRequest, httpResponse);
        }else if(httpRequest.getMethod().equals("POST")){
            doPost(httpRequest, httpResponse);
        }
    }

    default void doGet(HttpRequest httpRequest, HttpResponse httpResponse){
        throw new RuntimeException("405 - Method Not Allowd");
    }

    default void doPost(HttpRequest httpRequest, HttpResponse httpResponse){
        throw new RuntimeException("405 - Method Not Allowd");
    }
}
