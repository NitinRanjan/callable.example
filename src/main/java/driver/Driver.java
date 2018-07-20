package driver;

import service.CallableService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Driver {
    public static void main(String... args) throws Exception{
        ExecutorService es = Executors.newFixedThreadPool(5);
        Callable call = new CallableService();
        Future<Integer> firstcall = es.submit(call);
        while(!firstcall.isDone());

        System.out.println(firstcall.get());
        es.shutdown();
    }
}
