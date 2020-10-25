package ResourceHandling;

import java.util.Random;

public class RandomIntegerArrayGenerator {

    public static int[] generateUniformIntegers(int size, int low, int high){
        int[] data = new int[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(high+low)+low;
        }
        return data;
    }
}
