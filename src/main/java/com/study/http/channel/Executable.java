package com.study.http.channel;

import java.io.IOException;

//TODO#9 Executable interface를 구현한 객체는  WorkerThread(작업자)가 execute method를 호출 합니다.
public interface Executable {
    void execute() throws IOException;
}
