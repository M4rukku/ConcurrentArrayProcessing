public interface PipelineTask<T> {
    void process(ResourceBatch<T> data);
}
