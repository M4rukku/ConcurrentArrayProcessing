package ConcurrentTaskPipeline;


import javax.naming.OperationNotSupportedException;
import java.util.List;
import java.util.function.BiFunction;

public class BatchPipelineAggregator<T> {
    private List<BatchProcessorPipeline<T>> processedBatches;
    private AggregateTaskFromFunction<T> aggregateTask;

    public BatchPipelineAggregator(List<BatchProcessorPipeline<T>> processedBatches, AggregateTaskFromFunction<T> task) {
        this.processedBatches = processedBatches;
        aggregateTask = task;
    }

    public T aggregate(){
        try {
            T aggregated = processedBatches.get(0).getAggregateValue();
            for (int i = 1, processedBatchesSize = processedBatches.size(); i < processedBatchesSize; i++) {
                BatchProcessorPipeline<T> batch = processedBatches.get(i);
                aggregated = aggregateTask.aggregator.apply(aggregated, batch.getAggregateValue());
            }
            return aggregated;
        }catch (OperationNotSupportedException e){
            e.printStackTrace();
            return null;
        }
    }
}
