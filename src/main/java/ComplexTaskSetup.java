import ConcurrentTaskPipeline.*;
import ResourceHandling.RandomIntegerArrayGenerator;
import ResourceHandling.ResourceHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ComplexTaskSetup {

    public static void setupSequentialTask(Integer[] data){
        System.out.println("Starting Sequential task with data size - " + data.length);
        int sum = 0;
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i]*data[i];
            sum += data[i];
        }
        System.out.println("Finished Sequential Task with sum " + sum);
        System.out.println();
    }

    public static void setupConcurrentTask(Integer[] data){
        System.out.println("Starting concurrent processing of Data -- Data Size is: " + data.length);

        ResourceHandler<Integer> handler =
                new ResourceHandler<>(data, Runtime.getRuntime().availableProcessors(), 1024);
        List<Thread> threads = new ArrayList<>();
        List<BatchProcessorPipeline<Integer>> pipes = new ArrayList<>();

        for (int i = 0; i < handler.getNumberOfCreatedBatches(); i++) {
            BatchProcessorPipeline<Integer> pipeline = new BatchProcessorPipeline<>(handler.getBatch());
            pipes.add(pipeline);
            pipeline.addTask(PipelineTaskFromFunction.power2);
//            pipeline.addTask(new PipelineTaskFromFunction<>(integer -> integer*integer));
//            pipeline.setAggregateTask(AggregateTaskFromFunction.summer);
            Thread current = new Thread(pipeline);

            current.start();
            threads.add(current);
        }

        for (Thread thread: threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        BatchPipelineAggregator<Integer> summer =
//                new BatchPipelineAggregator<Integer>(pipes, new AggregateTaskFromFunction<>(Integer::sum));
//        System.out.println("Sum is: " + summer.aggregate());
    }

    public static void main(String[] args) {
        Integer[] data = RandomIntegerArrayGenerator.generateUniformIntegers(1024*1024*100, 1, 5);
        Integer[] dataCpy = data.clone();

        long elapsedSequentialTime;
        long elapsedConcurrentTime;

        long currentTime = System.nanoTime();
        setupSequentialTask(data);
        elapsedSequentialTime = System.nanoTime() - currentTime;

        currentTime = System.nanoTime();
        setupConcurrentTask(dataCpy);
        elapsedConcurrentTime = System.nanoTime()-currentTime;

        System.out.println();
        System.out.println("Results are as follows: ");
        System.out.println("Sequential Method took - " +
                (double) elapsedSequentialTime/1000000000.0 + " seconds");

        System.out.println("Concurrent Method took - " +
                (double) elapsedConcurrentTime/1000000000.0 + " seconds");
    }
}
