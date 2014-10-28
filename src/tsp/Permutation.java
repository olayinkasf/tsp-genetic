package tsp;

import java.util.Random;

public class Permutation {

    int[] word;
    Random rand = new Random();
    //private long count;

    public Permutation(int[] _word) {
        word = _word;
        //count = 0;
    }

    @SuppressWarnings("empty-statement")
   public boolean nextPermutation() {
        //print();
        int next = word.length;
        if (next-- <= 1) {
            return false;
        }
        for (;;) {
            // find rightmost element smaller than successor
            int next1 = next;
            if (word[--next] < word[next1]) {
                int mid = word.length;
                for (; !(word[next] < word[--mid]);)
					;
                swap(next, mid); // swap with rightmost element that's smaller
                reverse(next1, word.length);
                return true;
            }
            if (next == 0) {
                return false;
            }
        }
    }

    boolean swap(int left, int right) {
        int c = word[right];
        word[right] = word[left];
        word[left] = c;
        return true;
    }

    boolean reverse(int first, int last) {
        // reverse elements in [first, last)
        for (; first != last && first != --last; ++first) {
            swap(first, last);
        }
        return true;
    }

    int[] getWord() {
        return word;
    }

    public Random getRand() {
        return rand;
    }
    
    

}
