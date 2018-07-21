package driver;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import service.CallableService;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class Driver {
    private static  Logger DriverLogger = Logger.getLogger("GenericClass");
    public static void main(String... args) {
        PropertyConfigurator.configure("log4j.properties");
        DriverLogger.info("\n\n\n\t\t###### Using Callable Interface ######");
        callableUsingCallableInterface();
        DriverLogger.info("\n\n\n\t\t###### Using Lambda Expression ######");
        callableUsingLambda();
    }

    private static void callableUsingLambda() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Queue<Future<String>> taskQueue =  new LinkedList<Future<String>>();
        while (taskQueue.size()<10)
        {
            Callable<String> call = () -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return Thread.currentThread().getName();
            };
            taskQueue.add(executorService.submit(call));
        }
        waitForAllTask2Complete(taskQueue);
        printAllTaskResult(taskQueue);
        executorService.shutdown();
    }

    private static void callableUsingCallableInterface() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Queue<Future<String>> taskQueue =  new LinkedList<Future<String>>();
        while (taskQueue.size()<10)
            taskQueue.add(executorService.submit(new CallableService()));
        waitForAllTask2Complete(taskQueue);
        printAllTaskResult(taskQueue);
        executorService.shutdown();
    }

    private static void printAllTaskResult(Queue<Future<String>> taskQueue) {
        while (taskQueue.size()>0) {
            try {
                DriverLogger.info(taskQueue.poll().get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private static void waitForAllTask2Complete(Queue<Future<String>> taskQueue) {
        boolean isPending = true;
        while (isPending) {
            isPending = true;
            for(var task : taskQueue) {
                isPending &= !task.isDone();
            }
        }
    }

}

