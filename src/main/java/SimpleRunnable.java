import ResourceHandling.ResourceBatch;

public class SimpleRunnable implements Runnable{
    ResourceBatch<Integer> batch;
    int sum = 0;

    SimpleRunnable(ResourceBatch<Integer> batch){
        this.batch = batch;
    }

    @Override
    public void run() {
        sum=0;
        for (int i = batch.startBatchIndex; i < batch.endBatchIndex; i++) {
            batch.data[i] = batch.data[i]*batch.data[i];
            sum += batch.data[i];
        }
    }
}
