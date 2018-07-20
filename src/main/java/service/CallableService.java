package service;

import java.util.concurrent.Callable;

public class CallableService implements Callable{
    @Override
    public Integer call() throws Exception {
        return 1;
    }
}
