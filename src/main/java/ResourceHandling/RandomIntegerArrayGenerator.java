package ResourceHandling;

import java.util.Random;

public class RandomIntegerArrayGenerator {

    public static Integer[] generateUniformIntegers(int size, int low, int high){
        Integer[] data = new Integer[size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            data[i] = random.nextInt(high+low)-low+1;
        }
        return data;
    }
}
