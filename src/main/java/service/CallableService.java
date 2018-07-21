package service;

import java.util.concurrent.Callable;

public class CallableService implements Callable{
    @Override
    public String call()  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName()
    }
}
