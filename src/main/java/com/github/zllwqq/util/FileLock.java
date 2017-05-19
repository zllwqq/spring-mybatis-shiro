package com.github.zllwqq.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FileLock {
	
    private static Map<String, Lock> LOCKS = new HashMap<String, Lock>();

    public static synchronized Lock getLock(String key){
        if(LOCKS.containsKey(key)){
            return LOCKS.get(key);
        }else{
            Lock one = new ReentrantLock();
            LOCKS.put(key, one);
            return one;
        }
    }

    public static synchronized void removeLock(String key){
        LOCKS.remove(key);
    }
}
