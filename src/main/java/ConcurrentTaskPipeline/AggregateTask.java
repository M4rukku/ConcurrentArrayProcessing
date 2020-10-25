package ConcurrentTaskPipeline;

import ResourceHandling.ResourceBatch;

//Aggregate Task is a Task that will combine a number of T elements with a combine function
//Must have the property of Associativity e.g. gcd...
public interface AggregateTask<T> {
    void combine(ResourceBatch<T> data);
}
