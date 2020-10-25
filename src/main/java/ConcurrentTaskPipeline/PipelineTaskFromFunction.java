package ConcurrentTaskPipeline;

import ResourceHandling.ResourceBatch;

import java.util.function.Consumer;
import java.util.function.Function;

public class PipelineTaskFromFunction<T> implements PipelineTask<T>{
    public Function<T, T> function;

    public PipelineTaskFromFunction(Function<T, T> function) {
        this.function = function;
    }

    @Override
    public void process(ResourceBatch<T> data) {
        for (int i = data.startBatchIndex; i < data.endBatchIndex; i++) {
            data.data[i] = function.apply(data.data[i]);
        }
    }


}
