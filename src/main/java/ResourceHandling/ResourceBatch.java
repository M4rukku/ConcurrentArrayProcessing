package ResourceHandling;

import java.util.Arrays;
import java.util.List;

public class ResourceBatch<T> {
    public final T[] data;
    public int startBatchIndex;
    //End Index is exclusive
    public int endBatchIndex;

    ResourceBatch(T[] data, int startBatchIndex, int endBatchIndex){
        this.data = data;
        this.startBatchIndex = startBatchIndex;
        this.endBatchIndex = endBatchIndex;
    }
}
