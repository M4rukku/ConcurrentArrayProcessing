package ConcurrentTaskPipeline;

import ResourceHandling.ResourceBatch;

public interface PipelineTask<T> {
    void process(ResourceBatch<T> data);
}
