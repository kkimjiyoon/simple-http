package com.study.http.context;

import com.study.http.context.exception.ObjectNotFoundException;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//TODO#2 - Context를 구현합니다.
public class ApplicationContext  implements Context{
    ConcurrentMap<String, Object> objectMap;

    public ApplicationContext() {
        this.objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public void setAttribute(String name, Object object) {
        objectNameCheck(name);
        objectMap.put(name,object);
    }

    @Override
    public void removeAttribute(String name) {
        objectNameCheck(name);
        objectMap.remove(name);
    }

    @Override
    public Object getAttribute(String name) {
        //object가 존재하지 않는다면 ObjectNotFoundException 예외가 발생합니다.

        objectNameCheck(name);
        Object object =  objectMap.get(name);
        if(Objects.isNull(object)){
            throw new ObjectNotFoundException(name);
        }
        return object;
    }

    private void objectNameCheck(String name){
        if(Objects.isNull(name) || name.length()==0){
            throw new IllegalArgumentException(name);
        }
    }
}

