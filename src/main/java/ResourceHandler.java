import java.util.ArrayDeque;
import java.util.Queue;

public class ResourceHandler<T> {
    private final T[] data;
    private int numBatches;
    private int minBatchSize;

    private final Queue<ResourceBatch<T>> batches = new ArrayDeque<ResourceBatch<T>>();

    //Will make exactly numBatches ResourceBatches that have at least minBatchSize elements
    public ResourceHandler(T[] dataArray, int numBatches, int minBatchSize) {
        data = dataArray;
        this.numBatches = numBatches;
        this.minBatchSize = minBatchSize;

        makeBatches();
    }

    private void makeBatches() {
        int startIndex = 0;
        int minNumberOfBatchesToCreate = data.length / minBatchSize;

        //TODO
        //We have less data than the minBatchSize
        if(minNumberOfBatchesToCreate < numBatches){

        }
    }


}
