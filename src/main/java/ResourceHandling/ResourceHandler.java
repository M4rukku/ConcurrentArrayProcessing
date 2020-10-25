package ResourceHandling;

import java.util.*;

public class ResourceHandler<T> {
    private final T[] data;
    private int wantedNumberOfBatches;
    private int minBatchSize;

    private final Queue<ResourceBatch<T>> batches = new ArrayDeque<ResourceBatch<T>>();

    //Will make exactly numBatches ResourceBatches that have at least minBatchSize elements
    public ResourceHandler(T[] dataArray, int numBatches, int minBatchSize) {
        data = dataArray;
        this.wantedNumberOfBatches = numBatches;
        this.minBatchSize = minBatchSize;

        makeBatches();
    }

    private void makeBatches() {
        int startIndex = 0;
        int minNumberOfBatchesToCreate = data.length / minBatchSize;

        //We have less data than the minBatchSize, create as many minBatchSizes as possible
        //We will not be able to created wantedNumberOfBatches
        if (minNumberOfBatchesToCreate < wantedNumberOfBatches) {
            while (startIndex < data.length) {
                int endIndex = Math.min(startIndex + minBatchSize, data.length);
                batches.add(new ResourceBatch<T>(data, startIndex, endIndex));
                startIndex = endIndex;
            }
        } else {
            int batchSize = data.length / wantedNumberOfBatches;
            while (startIndex < data.length) {
                int endIndex = Math.min(startIndex + batchSize, data.length);
                if (startIndex + 2 * batchSize > data.length)
                    endIndex = data.length;
                batches.add(new ResourceBatch<T>(data, startIndex, endIndex));
                startIndex = endIndex;
            }
        }
    }

    public int getNumberOfCreatedBatches() {
        return batches.size();
    }

    public ResourceBatch<T> getBatch() {
        if (batches.isEmpty()) throw new NoSuchElementException("You have already obtained all batches created.");
        return batches.poll();
    }
}
