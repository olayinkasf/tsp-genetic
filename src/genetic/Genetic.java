/*
 * Do whatever you want with it.
 */
package genetic;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

/**
 *
 * @author Olayinka S. Folorunso <olayinka.sf@gmail.com> olayinka.sf@gmail.com
 */
public abstract class Genetic {

    ArrayList<Gene> genes;
    private static final int POPULATION_SIZE = 1000;
    private static final int ITERATIONS = 1000;
    TreeSet<Chromosome> population;
    Comparator<Chromosome> chromosomeComparator;
    Chromosome fittest;

    public Genetic(ArrayList<Gene> genes) {
        this.genes = genes;
    }

    public void solve() {

        if (chromosomeComparator == null) {
            population = new TreeSet<>();
        } else {
            population = new TreeSet<>(chromosomeComparator);
        }

        //Initial population
        for (int i = 0; i < POPULATION_SIZE; i++) {
            population.add(generateChromosome());
        }

        for (int i = 0; i < ITERATIONS; i++) {

            System.err.println("Sample " + i + ":");

            Chromosome cache = population.first();
            TreeSet<Chromosome> newGens = new TreeSet<>(chromosomeComparator);

            //cross over to make new babies
            for (Chromosome mum : population) {
                for (Chromosome dad : population) {
                    if (mum != dad) {
                        newGens.add(crossOverChromosomes(mum, dad));
                    }
                }
            }

            //mutate current population for evolution's sake
            for (Chromosome chromosome : population) {
                newGens.add(chromosome);
                newGens.add(mutateChromosome(chromosome));
            }

            //select the fittest species
            population.clear();
            for (Chromosome chromosome : newGens) {
                population.add(chromosome);
                if (population.size() == POPULATION_SIZE) {
                    break;
                }
            }

            //when the fittest becomes redundant, burn 'em all
            if (population.first() == cache) {
                population.clear();
                population.add(cache);
                for (int j = 0; j < POPULATION_SIZE - 1; j++) {
                    population.add(generateChromosome());
                }
            }

            fittest = population.first();
            System.err.println("FITTEST: \n" + fittest.toString());

            assert (population.size() == POPULATION_SIZE);
        }

    }

    public ArrayList<Gene> getGenes() {
        return genes;
    }

    public void setChromosomeComparator(Comparator<Chromosome> chromosomeComparator) {
        this.chromosomeComparator = chromosomeComparator;
    }

    public abstract Chromosome generateChromosome();

    public abstract Chromosome mutateChromosome(Chromosome chromosome);

    public abstract Chromosome crossOverChromosomes(Chromosome mum, Chromosome dad);

}
