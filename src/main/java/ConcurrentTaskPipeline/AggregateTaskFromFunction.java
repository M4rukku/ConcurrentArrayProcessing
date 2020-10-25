package ConcurrentTaskPipeline;

import ResourceHandling.ResourceBatch;

import java.util.function.BiFunction;

public class AggregateTaskFromFunction<T> implements AggregateTask<T>{
    public  BiFunction<T, T, T> aggregator;

    @Override
    public T combine(ResourceBatch<T> data) {
        T aggregate = data.data[data.startBatchIndex];
        for (int i = data.startBatchIndex+1; i < data.endBatchIndex; i++) {
            aggregate = aggregator.apply(aggregate, data.data[i]);
        }
        return aggregate;
    }

    public AggregateTaskFromFunction(BiFunction<T, T, T> aggregator){
        this.aggregator = aggregator;
    }
}
