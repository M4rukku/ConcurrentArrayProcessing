package ConcurrentTaskPipeline;

import ConcurrentTaskPipeline.AggregateTask;
import ResourceHandling.ResourceBatch;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
/*
The Batch Processor Pipeline will take a reference to a data array and a span,
then it will apply the tasks set to the data passed
 */
public class BatchProcessorPipeline<T> implements Runnable{

    private final List<PipelineTask<T>> tasks = new ArrayList<>();
    private final ResourceBatch<T> data;
    private AggregateTask<T> aggregateTask = null;

    private boolean processingComplete = false;
    private boolean doesAggregation = false;
    private T aggregateValue;

    public void addTask(PipelineTask<T> task){
        tasks.add(task);
    }

    public void setAggregateTask(AggregateTask<T> task){
        aggregateTask = task;
        doesAggregation = true;
    }

    public T getAggregateValue() throws OperationNotSupportedException {
        if (doesAggregation && processingComplete)
            return aggregateValue;
        else
            throw new OperationNotSupportedException("This ConcurrentTaskPipeline.BatchProcessorPipeline does not have a set aggregate task");
    }

    public BatchProcessorPipeline(ResourceBatch<T> data){
        this.data = data;
    }

    public void run() {
        for (PipelineTask<T> task : tasks){
            task.process(data);
        }
        if(doesAggregation)
            aggregateValue = aggregateTask.combine(data);
        processingComplete = true;
    }
}
