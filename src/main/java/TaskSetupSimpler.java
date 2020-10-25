import ConcurrentTaskPipeline.AggregateTaskFromFunction;
import ConcurrentTaskPipeline.BatchPipelineAggregator;
import ConcurrentTaskPipeline.BatchProcessorPipeline;
import ConcurrentTaskPipeline.PipelineTaskFromFunction;
import ResourceHandling.RandomIntegerArrayGenerator;
import ResourceHandling.ResourceHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TaskSetupSimpler {

    public static void setupSequentialTask(Integer[] data) {
        System.out.println("Starting Sequential task with data size - " + data.length);
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i] * data[i];
            sum += data[i];
        }
        System.out.println("Finished Sequential Task with sum " + sum);
        System.out.println();
    }

    public static void setupSimpleConcurrentTask(Integer[] data) {
        System.out.println("Starting concurrent processing of Data -- Data Size is: " + data.length);

        ResourceHandler<Integer> handler =
                new ResourceHandler<>(data, Runtime.getRuntime().availableProcessors(), 1024);
        List<Thread> threads = new ArrayList<>();
        List<SimpleRunnable> runnables = new ArrayList<>();

        while(handler.getRemainingBatches()>0) {
            SimpleRunnable tmp = new SimpleRunnable(handler.getBatch());
            runnables.add(tmp);

            Thread current = new Thread(tmp);
            current.start();
            threads.add(current);
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long sum = 0;
        for (SimpleRunnable runnable : runnables) {
            sum+= runnable.sum;
        }
        System.out.println("Sum is: " + sum);
    }

    public static void main(String[] args) {
        Integer[] data = RandomIntegerArrayGenerator.generateUniformIntegers(1024 * 1024 * 100, 1, 5);
        Integer[] dataCpy = Arrays.copyOf(data, data.length);

        long elapsedSequentialTime;
        long elapsedConcurrentTime;

        long currentTime = System.nanoTime();
        setupSequentialTask(data);
        elapsedSequentialTime = System.nanoTime() - currentTime;

        currentTime = System.nanoTime();
        setupSimpleConcurrentTask(dataCpy);
        elapsedConcurrentTime = System.nanoTime() - currentTime;

        System.out.println();
        System.out.println("Results are as follows: ");
        System.out.println("Sequential Method took - " +
                (double) elapsedSequentialTime / 1000000000.0 + " seconds");

        System.out.println("Concurrent Method took - " +
                (double) elapsedConcurrentTime / 1000000000.0 + " seconds");
    }
}
