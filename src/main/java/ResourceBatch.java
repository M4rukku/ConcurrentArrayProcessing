public class ResourceBatch<T> {
    private final T[] data;
    int startBatchIndex;
    //End Index is exclusive
    int endBatchIndex;

    ResourceBatch(T[] data, int startBatchIndex, int endBatchIndex){
        this.data = data;
        this.startBatchIndex = startBatchIndex;
        this.endBatchIndex = endBatchIndex;
    }
}
