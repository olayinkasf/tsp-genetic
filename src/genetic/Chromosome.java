/*
 * Do whatever you want with it.
 */
package genetic;

import java.util.ArrayList;

/**
 *
 * @author Olayinka S. Folorunso <olayinka.sf@gmail.com> olayinka.sf@gmail.com
 * @param <T>
 */
public final class Chromosome<T> {

    ArrayList<Gene> genes;
    T fitness;
    long index;
    private static long INDEX = 0;

    public long getIndex() {
        return index;
    }

    public Chromosome(ArrayList<Gene> genes, T value) {
        this.genes = genes;
        this.fitness = value;
        index = INDEX;
        INDEX++;
    }

    public T getFitness() {
        return fitness;
    }

    public ArrayList<Gene> getGenes() {
        return genes;
    }

    public void setGenes(ArrayList<Gene> genes) {
        this.genes = genes;
    }

    @Override
    public String toString() {
        return "Chromosome{\n"
                + "\t" + "genes=" + genes.toString() + ",\n"
                + "\tvalue=" + fitness + ", index=" + index + "\n"
                + "}";
    }

}
