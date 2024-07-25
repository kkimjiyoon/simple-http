package com.study.http.channel;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class HttpJob implements Executable {
    private final Socket client;

    public HttpJob(Socket client) {
        this.client = client;
    }

    public Socket getClient() {
        return client;
    }

    @Override
    public void execute(){
        //TODO#10 HttpJob는 execute() method를 구현 합니다.
        StringBuilder requestBuilder = new StringBuilder();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        ) {

            while (true) {
                String line = bufferedReader.readLine();
                requestBuilder.append(line);
                log.debug("line:{}", line);
                if (Objects.isNull(line) || line.length() == 0) {
                    break;
                }
            }

            StringBuilder responseBody = new StringBuilder();
            responseBody.append("<html>");
            responseBody.append("<body>");
            responseBody.append("<h1>hello java</h1>");
            responseBody.append("</body>");
            responseBody.append("</html>");

            StringBuilder responseHeader = new StringBuilder();

            responseHeader.append(String.format("HTTP/1.0 200 OK%s",System.lineSeparator()));
            responseHeader.append(String.format("Server: HTTP server/0.1%s",System.lineSeparator()));
            responseHeader.append(String.format("Content-type: text/html; charset=%s%s","UTF-8",System.lineSeparator()));
            responseHeader.append(String.format("Connection: Closed%s",System.lineSeparator()));
            responseHeader.append(String.format("Content-Length:%d %s%s",responseBody.length(),System.lineSeparator(),System.lineSeparator()));

            bufferedWriter.write(responseHeader.toString());
            bufferedWriter.write(responseBody.toString());
            bufferedWriter.flush();
            client.close();
        }catch (IOException e){
            log.error("server error:{}",e);
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
