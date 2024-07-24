package com.study.http;

import lombok.extern.slf4j.Slf4j;

import java.net.Socket;

@Slf4j
/* TODO#3 Java에서 Thread는 implements Runnable or extends Thread를 이용해서 Thread를 만들 수 있습니다.
 *  implements Runnable을 사용하여 구현 합니다.
 */
public class HttpRequestHandler implements Runnable {
    private final Socket client;

    public HttpRequestHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {

        //TODO#4 simple-http-server-step1을 참고 하여 구현 합니다.
        /*
            <html>
                <body>
                    <h1>hello java</h1>
                </body>
            </html>
        */

    }
}
