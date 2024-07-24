package com.study.http;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Slf4j
public class SimpleHttpServer {

    private final int port;
    private static final int DEFAULT_PORT=8080;

    public SimpleHttpServer(){
        //TODO#2 기본 port는 8080을 사용합니다.
        port = DEFAULT_PORT;
    }

    public SimpleHttpServer(int port) {
        //TODO#1 port range <=0 IllegalArgumentException 예외가 발생 합니다.
        if (port <= 0) {
            throw new IllegalArgumentException();
        }
        this.port = port;
    }

    public void start() throws IOException {

        StringBuilder requestBuilder = new StringBuilder();

        try(ServerSocket serverSocket = new ServerSocket(8080);){

            while(true){
                //TODO#3 client가 연결될 때 까지 대기 합니다.
                Socket client = serverSocket.accept();

                //TODO#4 입출력을 위해서  Reader, Writer를 선언 합니다.
                try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))
                ) {
                    log.debug("------HTTP-REQUEST_start()");
                    while (true) {
                        String line = bufferedReader.readLine();
                        //TODO#5  requestBuilder에 append 합니다.
                        requestBuilder.append(line);

                        log.debug("{}", line);

                        //TODO#6 break(종료) 조건을 구현 line is null or line length == 0
                        if (line == null || line.length() == 0) {
                            break;
                        }
//                            break;
                    }
                    log.debug("------HTTP-REQUEST_end()");

                    //TODO#7 clinet에 응답할 html을 작성합니다.
                    StringBuilder responseBody = new StringBuilder();
                    responseBody.append("<html>\n" +
                            "<body>\n" +
                            "<h1>hello java</h1>\n" +
                            "</body>\n" +
                            "</html>");
                        /*
                            <html>
                                <body>
                                    <h1>hello hava</h1>
                                </body>
                            </html>
                        */


                    StringBuilder responseHeader = new StringBuilder();

                    //TODO#8 HTTP/1.0 200 OK
                    responseHeader.append(String.format("HTTP/1.0 200 OK%s", System.lineSeparator()));

                    responseHeader.append(String.format("Server: HTTP server/0.1%s",System.lineSeparator()));

                    //TODO#9 Content-type: text/html; charset=UTF-8"
                    responseHeader.append(String.format("Content-type: text/html; charset=UTF-8%s", System.lineSeparator()));

                    responseHeader.append(String.format("Connection: Closed%s",System.lineSeparator()));

                    //TODO#10 responseBody의  Content-Length를 설정 합니다.
                    responseHeader.append(String.format("Content-Length: " + responseBody.length() + "%s", System.lineSeparator()));

                    //TODO#11 write Response Header
                    bufferedWriter.write(String.valueOf(responseHeader));

                    //TODO#12 write Response Body
                    bufferedWriter.write(String.valueOf(responseBody));

                    //TODO#13 buffer에 등록된 Response (header, body) flush 합니다.(socket을 통해서 clent에 응답 합니다.)
                    bufferedWriter.flush();


                    log.debug("header:{}",responseHeader);
                    log.debug("body:{}",responseBody);

                }catch (IOException e){
                    log.error("sock error : {}",e);
                }finally {
                    //TODO#14 Client Socket Close
                    client.close();

                }
            }//end while

        }
    }
}
