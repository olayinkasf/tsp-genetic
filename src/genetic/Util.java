/*
 * Do whatever you want with it.
 */
package genetic;

import java.util.List;
import java.util.Random;

/**
 *
 * @author Olayinka S. Folorunso <olayinka.sf@gmail.com> olayinka.sf@gmail.com
 */
public class Util {

    private static Random random;

    /**
     * @param array
     */
    public static void shuffle(int[] array) {
        if (random == null) {
            random = new Random();
        }
        int count = array.length;
        for (int i = count; i > 1; i--) {
            swap(array, i - 1, random.nextInt(i));
        }
    }

    public static void shuffle(List array) {
        if (random == null) {
            random = new Random();
        }
        int count = array.size();
        for (int i = count; i > 1; i--) {
            swap(array, i - 1, random.nextInt(i));
        }
    }

    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void swap(List array, int i, int nextInt) {
        Object temp = array.get(i);
        array.set(i, array.get(nextInt));
        array.set(nextInt, array.get(i));
    }
}
